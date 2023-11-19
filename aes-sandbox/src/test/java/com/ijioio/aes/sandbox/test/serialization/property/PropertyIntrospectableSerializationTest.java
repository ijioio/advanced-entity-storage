package com.ijioio.aes.sandbox.test.serialization.property;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.sandbox.test.serialization.property.BasePropertySerializationTest.TestIntrospectable;
import com.ijioio.test.model.PropertyIntrospectableSerialization;

public class PropertyIntrospectableSerializationTest
		extends BasePropertySerializationTest<PropertyIntrospectableSerialization, TestIntrospectable> {

	@Entity( //
			name = PropertyIntrospectableSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueIntrospectable", type = TestIntrospectable.NAME) //
			} //
	)
	public static interface PropertyIntrospectableSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyIntrospectableSerialization";
	}

	@Override
	protected String getXmlFileName() {
		return "property-introspectable-serialization.xml";
	}

	@Override
	protected String getNullXmlFileName() {
		return "property-introspectable-null-serialization.xml";
	}

	@Override
	protected Class<PropertyIntrospectableSerialization> getEntityClass() {
		return PropertyIntrospectableSerialization.class;
	}

	@Override
	protected PropertyIntrospectableSerialization createEntity() {

		PropertyIntrospectableSerialization entity = new PropertyIntrospectableSerialization();

		entity.setId("property-introspectable-serialization");

		TestIntrospectable value = new TestIntrospectable();

		value.setValue("value");

		entity.setValueIntrospectable(value);

		return entity;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected TestIntrospectable getPropertyValue(PropertyIntrospectableSerialization entity) {
		return entity.getValueIntrospectable();
	}

	@Override
	protected void setPropertyValue(PropertyIntrospectableSerialization entity, TestIntrospectable value) {
		entity.setValueIntrospectable(value);
	}

	@Override
	protected void checkPropertyValue(TestIntrospectable expectedValue, TestIntrospectable actualValue) {
		Assertions.assertEquals(getIntrospectableValue(expectedValue), getIntrospectableValue(actualValue));
	}

	private String getIntrospectableValue(TestIntrospectable value) {
		return Optional.ofNullable(value).map(item -> item.getValue()).orElse(null);
	}
}
