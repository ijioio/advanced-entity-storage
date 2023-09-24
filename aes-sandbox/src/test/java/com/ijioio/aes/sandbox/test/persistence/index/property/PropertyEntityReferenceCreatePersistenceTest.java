package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.sql.ResultSet;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.sandbox.test.persistence.index.property.PropertyEntityReferenceCreatePersistenceTest.Some;
import com.ijioio.test.model.PropertyEntityReferenceCreatePersistence;
import com.ijioio.test.model.PropertyEntityReferenceCreatePersistenceIndex;

public class PropertyEntityReferenceCreatePersistenceTest extends
		BasePropertyCreatePersistenceTest<PropertyEntityReferenceCreatePersistenceIndex, EntityReference<Some>> {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.persistence.index.property.PropertyEntityReferenceCreatePersistenceTest.Some";
	}

	@Entity( //
			name = PropertyEntityReferenceCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "EntityReference<Some>", type = Type.ENTITY_REFERENCE, parameters = Some.NAME) //
			}, //
			properties = { //
					@EntityProperty(name = "valueEntityReference", type = "EntityReference<Some>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyEntityReferenceCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueEntityReference", type = "EntityReference<Some>") //
							} //
					) //
			} //
	)
	public static interface PropertyEntityReferenceCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyEntityReferenceCreatePersistenceIndex";
	}

	@Override
	protected String getSqlScriptPath() throws Exception {
		return "persistence/index/property/property-entity-reference-create-persistence.sql";
	}

	@Override
	protected String getTableName() {
		return PropertyEntityReferenceCreatePersistenceIndex.class.getSimpleName();
	}

	@Override
	protected PropertyEntityReferenceCreatePersistenceIndex createIndex() {

		PropertyEntityReferenceCreatePersistenceIndex index = new PropertyEntityReferenceCreatePersistenceIndex();

		index.setId("property-entity-reference-create-persistence-index");
		index.setSource(EntityReference.of("property-entity-reference-create-persistence",
				PropertyEntityReferenceCreatePersistence.class));
		index.setValueEntityReference(EntityReference.of("some", Some.class));

		return index;
	}

	@Override
	protected EntityReference<Some> getPropertyValue(PropertyEntityReferenceCreatePersistenceIndex index) {
		return index.getValueEntityReference();
	}

	@Override
	protected void setPropertyValue(PropertyEntityReferenceCreatePersistenceIndex index, EntityReference<Some> value) {
		index.setValueEntityReference(value);
	}

	@Override
	protected void checkPropertyValue(PropertyEntityReferenceCreatePersistenceIndex index, ResultSet resultSet)
			throws Exception {

		Assertions.assertEquals(
				Optional.ofNullable(index.getValueEntityReference()).map(item -> item.getId()).orElse(null),
				resultSet.getString("valueEntityReferenceId"));
		Assertions.assertEquals(
				Optional.ofNullable(index.getValueEntityReference()).map(item -> item.getType().getName()).orElse(null),
				resultSet.getString("valueEntityReferenceType"));
	}
}
