package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;
import com.ijioio.test.model.PropertyStringListCreatePersistence;
import com.ijioio.test.model.PropertyStringListCreatePersistenceIndex;

public class PropertyStringListCreatePersistenceTest extends BasePersistenceTest {

	@Entity( //
			name = PropertyStringCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = Type.LIST, parameters = Type.STRING) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = "List<String>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueStringList", type = "List<String>") //
							} //
					) //
			} //
	)
	public static interface PropertyStringCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringListCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringListCreatePersistenceIndex";
	}

	private JdbcPersistenceHandler handler;

	private Path path;

	private PropertyStringListCreatePersistenceIndex index;

	@BeforeEach
	public void before() throws Exception {

		handler = new JdbcPersistenceHandler();

		path = Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-string-list-create-persistence.sql").toURI());

		executeSql(connection, path);

		index = new PropertyStringListCreatePersistenceIndex();

		index.setId("property-string-list-create-persistence-index");
		index.setSource(EntityReference.of("property-string-list-create-persistence",
				PropertyStringListCreatePersistence.class));
		index.setValueStringList(Arrays.asList("value1", "value2", "value3"));
	}

	@Test
	public void testCreate() throws Exception {

		handler.create(JdbcPersistenceContext.of(connection), index);

		try (PreparedStatement statement = connection.prepareStatement(
				String.format("select * from %s", PropertyStringListCreatePersistenceIndex.class.getSimpleName()))) {

			try (ResultSet resultSet = statement.executeQuery()) {

				Assertions.assertTrue(resultSet.next());

				Assertions.assertEquals(index.getId(), resultSet.getString("id"));
				Assertions.assertEquals(getEntityReferenceSearchId(index.getSource()),
						resultSet.getString("sourceSearchId"));
				Assertions.assertEquals(index.getSource().getId(), resultSet.getString("sourceId"));
				Assertions.assertEquals(index.getSource().getType().getName(), resultSet.getString("sourceType"));
				Assertions.assertEquals(index.getValueStringList(),
						Arrays.asList((Object[]) resultSet.getArray("valueStringList").getArray()));

				Assertions.assertTrue(resultSet.isLast());
			}
		}
	}
}
