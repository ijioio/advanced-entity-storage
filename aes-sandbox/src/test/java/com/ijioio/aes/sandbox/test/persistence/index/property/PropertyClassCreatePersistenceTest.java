package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
import com.ijioio.test.model.PropertyClassCreatePersistence;
import com.ijioio.test.model.PropertyClassCreatePersistenceIndex;

public class PropertyClassCreatePersistenceTest extends BasePersistenceTest {

	@Entity( //
			name = PropertyClassCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "Class<String>", type = Type.CLASS, parameters = Type.STRING) //
			}, //
			properties = { //
					@EntityProperty(name = "valueClass", type = "Class<String>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyClassCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueClass", type = "Class<String>") //
							} //
					) //
			} //
	)
	public static interface PropertyClassCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyClassCreatePersistenceIndex";
	}

	private JdbcPersistenceHandler handler;

	private Path path;

	private PropertyClassCreatePersistenceIndex index;

	@BeforeEach
	public void before() throws Exception {

		handler = new JdbcPersistenceHandler();

		path = Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-class-create-persistence.sql").toURI());

		executeSql(connection, path);

		index = new PropertyClassCreatePersistenceIndex();

		index.setId("property-class-create-persistence-index");
		index.setSource(EntityReference.of("property-class-create-persistence", PropertyClassCreatePersistence.class));
		index.setValueClass(String.class);
	}

	@Test
	public void testCreate() throws Exception {

		handler.create(JdbcPersistenceContext.of(connection), index);

		try (PreparedStatement statement = connection.prepareStatement(
				String.format("select * from %s", PropertyClassCreatePersistenceIndex.class.getSimpleName()))) {

			try (ResultSet resultSet = statement.executeQuery()) {

				Assertions.assertTrue(resultSet.next());

				Assertions.assertEquals(index.getId(), resultSet.getString("id"));
				Assertions.assertEquals(index.getSource().getId(), resultSet.getString("sourceId"));
				Assertions.assertEquals(index.getSource().getType().getName(), resultSet.getString("sourceType"));
				Assertions.assertEquals(index.getValueClass().getName(), resultSet.getString("valueClass"));

				Assertions.assertTrue(resultSet.isLast());
			}
		}
	}
}
