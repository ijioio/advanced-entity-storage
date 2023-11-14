package com.ijioio.aes.sandbox.test.serialization.property;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.sandbox.test.serialization.property.BasePropertySerializationTest.Some;
import com.ijioio.test.model.PropertyEntityReferenceSerialization;

public class PropertyEntityReferenceSerializationTest
		extends BasePropertySerializationTest<PropertyEntityReferenceSerialization, EntityReference<Some>> {

	@Entity( //
			name = PropertyEntityReferenceSerializationPrototype.NAME, //
			types = { //
					@Type(name = "EntityReference<Some>", type = Type.ENTITY_REFERENCE, parameters = @Parameter(name = Some.NAME)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueEntityReference", type = "EntityReference<Some>") //
			} //
	)
	public static interface PropertyEntityReferenceSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceSerialization";
	}

	@Override
	protected String getXmlFileName(PropertyType type) {

		if (type == PropertyType.STANDARD) {
			return "property-entity-reference-serialization.xml";
		} else if (type == PropertyType.NULL) {
			return "property-entity-reference-null-serialization.xml";
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected Class<PropertyEntityReferenceSerialization> getEntityClass() {
		return PropertyEntityReferenceSerialization.class;
	}

	@Override
	protected PropertyEntityReferenceSerialization createEntity() {

		PropertyEntityReferenceSerialization entity = new PropertyEntityReferenceSerialization();

		entity.setId("property-entity-reference-serialization");
		entity.setValueEntityReference(EntityReference.of("some", Some.class));

		return entity;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected EntityReference<Some> getPropertyValue(PropertyEntityReferenceSerialization entity) {
		return entity.getValueEntityReference();
	}

	@Override
	protected void setPropertyValue(PropertyEntityReferenceSerialization entity, EntityReference<Some> value) {
		entity.setValueEntityReference(value);
	}

	@Override
	protected void checkPropertyValue(EntityReference<Some> expectedValue, EntityReference<Some> actualValue) {

		Assertions.assertEquals(getEntityReferenceId(expectedValue), getEntityReferenceId(actualValue));
		Assertions.assertEquals(getEntityReferenceType(expectedValue), getEntityReferenceType(actualValue));
	}

	private String getEntityReferenceId(EntityReference<Some> value) {
		return Optional.ofNullable(value).map(item -> item.getId()).orElse(null);
	}

	private Class<Some> getEntityReferenceType(EntityReference<Some> value) {
		return Optional.ofNullable(value).map(item -> item.getType()).orElse(null);
	}
}
