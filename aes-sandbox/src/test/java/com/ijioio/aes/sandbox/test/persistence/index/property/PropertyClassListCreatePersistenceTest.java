package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.stream.Collectors;

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
import com.ijioio.test.model.PropertyClassListCreatePersistence;
import com.ijioio.test.model.PropertyClassListCreatePersistenceIndex;

public class PropertyClassListCreatePersistenceTest extends BasePersistenceTest {

	@Entity( //
			name = PropertyClassListCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "Class<String>", type = Type.CLASS, parameters = Type.STRING), //
					@Type(name = "List<Class<String>>", type = Type.LIST, parameters = "Class<String>") //
			}, //
			properties = { //
					@EntityProperty(name = "valueClassList", type = "List<Class<String>>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyClassListCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueClassList", type = "List<Class<String>>") //
							} //
					) //
			} //
	)
	public static interface PropertyClassListCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassListCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyClassListCreatePersistenceIndex";
	}

	private Path path;

	private PropertyClassListCreatePersistenceIndex index;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-class-list-create-persistence.sql").toURI());

		executeSql(connection, path);

		index = new PropertyClassListCreatePersistenceIndex();

		index.setId("property-class-list-create-persistence-index");
		index.setSource(
				EntityReference.of("property-class-list-create-persistence", PropertyClassListCreatePersistence.class));
		index.setValueClassList(Arrays.asList(String.class, String.class, String.class));
	}

	@Test
	public void testCreate() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		handler.create(JdbcPersistenceContext.of(connection), index);

		try (PreparedStatement statement = connection.prepareStatement(
				String.format("select * from %s", PropertyClassListCreatePersistenceIndex.class.getSimpleName()))) {

			try (ResultSet resultSet = statement.executeQuery()) {

				Assertions.assertTrue(resultSet.next());

				Assertions.assertEquals(index.getId(), resultSet.getString("id"));
				Assertions.assertEquals(index.getSource().getId(), resultSet.getString("sourceId"));
				Assertions.assertEquals(index.getSource().getType().getName(), resultSet.getString("sourceType"));
				Assertions.assertEquals(
						index.getValueClassList().stream().map(item -> item.getName()).collect(Collectors.toList()),
						Arrays.asList((Object[]) resultSet.getArray("valueClassList").getArray()));

				Assertions.assertTrue(resultSet.isLast());
			}
		}
	}
}
