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
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;
import com.ijioio.test.model.PropertyEntityReferenceListCreatePersistence;
import com.ijioio.test.model.PropertyEntityReferenceListCreatePersistenceIndex;

public class PropertyEntityReferenceListCreatePersistenceTest extends BasePersistenceTest {

	public static class Some extends BaseEntity {
		// Empty
	}

	@Entity( //
			name = PropertyEntityReferenceListCreatePersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueEntityReferenceList", type = @Type(name = Type.LIST), parameters = @Type(name = "com.ijioio.aes.sandbox.test.persistence.index.property.PropertyEntityReferenceListCreatePersistenceTest.Some", reference = true)) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyEntityReferenceListCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueEntityReferenceList", type = @Type(name = Type.LIST), parameters = @Type(name = "com.ijioio.aes.sandbox.test.persistence.index.property.PropertyEntityReferenceListCreatePersistenceTest.Some", reference = true)) //
							} //
					) //
			} //
	)
	public static interface PropertyEntityReferenceListCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceListCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyEntityReferenceListCreatePersistenceIndex";
	}

	private Path path;

	private PropertyEntityReferenceListCreatePersistenceIndex index;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-entity-reference-list-create-persistence.sql")
				.toURI());

		executeSql(connection, path);

		index = new PropertyEntityReferenceListCreatePersistenceIndex();

		index.setId("property-entity-reference-list-create-persistence-index");
		index.setSource(EntityReference.of("property-entity-reference-list-create-persistence",
				PropertyEntityReferenceListCreatePersistence.class));
		index.setValueEntityReferenceList(Arrays.asList(EntityReference.of("some-1", Some.class),
				EntityReference.of("some-2", Some.class), EntityReference.of("some-3", Some.class)));
	}

	@Test
	public void testCreate() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		handler.create(JdbcPersistenceContext.of(connection), index);

		try (PreparedStatement statement = connection.prepareStatement(String.format("select * from %s",
				PropertyEntityReferenceListCreatePersistenceIndex.class.getSimpleName()))) {

			try (ResultSet resultSet = statement.executeQuery()) {

				Assertions.assertTrue(resultSet.next());

				Assertions.assertEquals(index.getId(), resultSet.getString("id"));
				Assertions.assertEquals(index.getSource().getId(), resultSet.getString("sourceId"));
				Assertions.assertEquals(index.getSource().getType().getName(), resultSet.getString("sourceType"));
//				Assertions.assertEquals(index.getValueEntityReferenceList().getId(),
//						resultSet.getString("valueEntityReferenceListId"));
//				Assertions.assertEquals(index.getValueEntityReferenceList().getType().getName(),
//						resultSet.getString("valueEntityReferenceListType"));

				Assertions.assertTrue(resultSet.isLast());
			}
		}
	}
}