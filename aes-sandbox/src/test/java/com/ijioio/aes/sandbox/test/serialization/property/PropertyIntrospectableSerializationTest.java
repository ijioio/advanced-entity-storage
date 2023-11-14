package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.sandbox.test.serialization.property.BasePropertySerializationTest.SomeIntrospectable;
import com.ijioio.test.model.PropertyIntrospectableSerialization;

public class PropertyIntrospectableSerializationTest
		extends BasePropertySerializationTest<PropertyIntrospectableSerialization, SomeIntrospectable> {

	@Entity( //
			name = PropertyIntrospectableSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueIntrospectable", type = "com.ijioio.aes.sandbox.test.serialization.property.BasePropertySerializationTest.SomeIntrospectable") //
			} //
	)
	public static interface PropertyIntrospectableSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyIntrospectableSerialization";
	}

	@Override
	protected String getXmlFileName(PropertyType type) {

		if (type == PropertyType.STANDARD) {
			return "property-introspectable-serialization.xml";
		} else if (type == PropertyType.NULL) {
			return "property-introspectable-null-serialization.xml";
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected Class<PropertyIntrospectableSerialization> getEntityClass() {
		return PropertyIntrospectableSerialization.class;
	}

	@Override
	protected PropertyIntrospectableSerialization createEntity() {

		PropertyIntrospectableSerialization entity = new PropertyIntrospectableSerialization();

		entity.setId("property-introspectable-serialization");

		SomeIntrospectable value = new SomeIntrospectable();

		value.setValue("value");

		entity.setValueIntrospectable(value);

		return entity;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected SomeIntrospectable getPropertyValue(PropertyIntrospectableSerialization entity) {
		return entity.getValueIntrospectable();
	}

	@Override
	protected void setPropertyValue(PropertyIntrospectableSerialization entity, SomeIntrospectable value) {
		entity.setValueIntrospectable(value);
	}

	@Override
	protected void checkPropertyValue(SomeIntrospectable expectedValue, SomeIntrospectable actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
