package com.ijioio.aes.sandbox.test.serialization.property;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.sandbox.test.serialization.property.BasePropertySerializationTest.TestEntity;
import com.ijioio.test.model.PropertyEntityReferenceSerialization;

public class PropertyEntityReferenceSerializationTest
		extends BasePropertySerializationTest<PropertyEntityReferenceSerialization, EntityReference<TestEntity>> {

	@Entity( //
			name = PropertyEntityReferenceSerializationPrototype.NAME, //
			types = { //
					@Type(name = "EntityReference<Some>", type = Type.ENTITY_REFERENCE, parameters = @Parameter(name = TestEntity.NAME)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueEntityReference", type = "EntityReference<Some>") //
			} //
	)
	public static interface PropertyEntityReferenceSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceSerialization";
	}

	@Override
	protected String getXmlFileName() {
		return "property-entity-reference-serialization.xml";
	}

	@Override
	protected String getNullXmlFileName() {
		return "property-entity-reference-null-serialization.xml";
	}

	@Override
	protected Class<PropertyEntityReferenceSerialization> getEntityClass() {
		return PropertyEntityReferenceSerialization.class;
	}

	@Override
	protected PropertyEntityReferenceSerialization createEntity() {

		PropertyEntityReferenceSerialization entity = new PropertyEntityReferenceSerialization();

		entity.setId("property-entity-reference-serialization");
		entity.setValueEntityReference(EntityReference.of("some", TestEntity.class));

		return entity;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected EntityReference<TestEntity> getPropertyValue(PropertyEntityReferenceSerialization entity) {
		return entity.getValueEntityReference();
	}

	@Override
	protected void setPropertyValue(PropertyEntityReferenceSerialization entity, EntityReference<TestEntity> value) {
		entity.setValueEntityReference(value);
	}

	@Override
	protected void checkPropertyValue(EntityReference<TestEntity> expectedValue, EntityReference<TestEntity> actualValue) {

		Assertions.assertEquals(getEntityReferenceId(expectedValue), getEntityReferenceId(actualValue));
		Assertions.assertEquals(getEntityReferenceType(expectedValue), getEntityReferenceType(actualValue));
	}

	private String getEntityReferenceId(EntityReference<TestEntity> value) {
		return Optional.ofNullable(value).map(item -> item.getId()).orElse(null);
	}

	private Class<TestEntity> getEntityReferenceType(EntityReference<TestEntity> value) {
		return Optional.ofNullable(value).map(item -> item.getType()).orElse(null);
	}
}
