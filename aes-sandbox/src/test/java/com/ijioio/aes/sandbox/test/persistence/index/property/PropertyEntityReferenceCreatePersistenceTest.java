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
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;
import com.ijioio.test.model.PropertyEntityReferenceCreatePersistence;
import com.ijioio.test.model.PropertyEntityReferenceCreatePersistenceIndex;

public class PropertyEntityReferenceCreatePersistenceTest extends BasePersistenceTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.persistence.index.property.PropertyEntityReferenceCreatePersistenceTest.Some";
	}

	@Entity( //
			name = PropertyEntityReferenceCreatePersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueEntityReference", type = @Type(name = Type.ENTITY_REFERENCE), parameters = @Type(name = Some.NAME)) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyEntityReferenceCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueEntityReference", type = @Type(name = Type.ENTITY_REFERENCE), parameters = @Type(name = Some.NAME)) //
							} //
					) //
			} //
	)
	public static interface PropertyEntityReferenceCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyEntityReferenceCreatePersistenceIndex";
	}

	private Path path;

	private PropertyEntityReferenceCreatePersistenceIndex index;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-entity-reference-create-persistence.sql").toURI());

		executeSql(connection, path);

		index = new PropertyEntityReferenceCreatePersistenceIndex();

		index.setId("property-entity-reference-create-persistence-index");
		index.setSource(EntityReference.of("property-entity-reference-create-persistence",
				PropertyEntityReferenceCreatePersistence.class));
		index.setValueEntityReference(EntityReference.of("some", Some.class));
	}

	@Test
	public void testCreate() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		handler.create(JdbcPersistenceContext.of(connection), index);

		try (PreparedStatement statement = connection.prepareStatement(String.format("select * from %s",
				PropertyEntityReferenceCreatePersistenceIndex.class.getSimpleName()))) {

			try (ResultSet resultSet = statement.executeQuery()) {

				Assertions.assertTrue(resultSet.next());

				Assertions.assertEquals(index.getId(), resultSet.getString("id"));
				Assertions.assertEquals(index.getSource().getId(), resultSet.getString("sourceId"));
				Assertions.assertEquals(index.getSource().getType().getName(), resultSet.getString("sourceType"));
				Assertions.assertEquals(index.getValueEntityReference().getId(),
						resultSet.getString("valueEntityReferenceId"));
				Assertions.assertEquals(index.getValueEntityReference().getType().getName(),
						resultSet.getString("valueEntityReferenceType"));

				Assertions.assertTrue(resultSet.isLast());
			}
		}
	}
}
