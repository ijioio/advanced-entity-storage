package com.ijioio.aes.sandbox.test.persistence.property;

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
import com.ijioio.test.model.PropertyStringCreatePersistence;
import com.ijioio.test.model.PropertyStringCreatePersistenceIndex;

public class PropertyStringCreatePersistenceTest extends BasePersistenceTest {

	@Entity( //
			name = PropertyStringCreatePersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = @Type(name = Type.STRING)) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueString", type = @Type(name = Type.STRING)) //
							} //
					) //
			} //
	)
	public static interface PropertyStringCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringCreatePersistenceIndex";
	}

	private Path path;

	private PropertyStringCreatePersistenceIndex index;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(
				getClass().getClassLoader().getResource("persistence/property-string-create-persistence.sql").toURI());

		executeSql(connection, path);

		index = new PropertyStringCreatePersistenceIndex();

		index.setId("property-string-create-persistence-index");
		index.setSource(
				EntityReference.of("property-string-create-persistence", PropertyStringCreatePersistence.class));
		index.setValueString("value");
	}

	@Test
	public void testCreate() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		handler.create(JdbcPersistenceContext.of(connection), index);

		try (PreparedStatement statement = connection.prepareStatement(
				String.format("select * from %s", PropertyStringCreatePersistenceIndex.class.getSimpleName()))) {

			try (ResultSet resultSet = statement.executeQuery()) {

				Assertions.assertTrue(resultSet.next());

				Assertions.assertEquals(index.getId(), resultSet.getString("id"));
				Assertions.assertEquals(index.getSource().getId(), resultSet.getString("sourceId"));
				Assertions.assertEquals(index.getSource().getType().getName(), resultSet.getString("sourceType"));
				Assertions.assertEquals(index.getValueString(), resultSet.getString("valueString"));

				Assertions.assertTrue(resultSet.isLast());
			}
		}
	}
}
