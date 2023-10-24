package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.sql.ResultSet;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertyCreatePersistenceTest.Some;
import com.ijioio.test.model.PropertyEntityReferenceCreatePersistence;
import com.ijioio.test.model.PropertyEntityReferenceCreatePersistenceIndex;

public class PropertyEntityReferenceCreatePersistenceTest extends
		BasePropertyCreatePersistenceTest<PropertyEntityReferenceCreatePersistenceIndex, EntityReference<Some>> {

	@Entity( //
			name = PropertyEntityReferenceCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "EntityReference<Some>", type = Type.ENTITY_REFERENCE, parameters = @Parameter(name = Some.NAME)) //
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
	protected String getSqlScriptFileName() throws Exception {
		return "property-entity-reference-create-persistence.sql";
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
	protected boolean isNullPropertyValueAllowed() {
		return true;
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
	protected void checkPropertyValue(EntityReference<Some> value, ResultSet resultSet) throws Exception {

		Assertions.assertEquals(getEntityReferenceId(value), resultSet.getString("valueEntityReferenceId"));
		Assertions.assertEquals(getEntityReferenceTypeName(value), resultSet.getString("valueEntityReferenceType"));
	}

	private String getEntityReferenceId(EntityReference<? extends Some> value) {
		return Optional.ofNullable(value).map(item -> item.getId()).orElse(null);
	}

	private String getEntityReferenceTypeName(EntityReference<? extends Some> value) {
		return Optional.ofNullable(value).map(item -> item.getType()).map(item -> item.getName()).orElse(null);
	}
}
