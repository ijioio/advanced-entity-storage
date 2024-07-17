package com.ijioio.aes.serialization.test.fixture.entity.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyStringSerialization;

public abstract class BasePropertyStringSerializationTest
		extends BasePropertySerializationTest<PropertyStringSerialization, String> {

	@Entity( //
			name = PropertyStringSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING) //
			} //
	)
	public static interface PropertyStringSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringSerialization";
	}

	@Override
	protected Class<PropertyStringSerialization> getEntityClass() {
		return PropertyStringSerialization.class;
	}

	@Override
	protected PropertyStringSerialization createEntity() {

		PropertyStringSerialization entity = new PropertyStringSerialization();

		entity.setId("property-string-serialization");
		entity.setValueString("value");

		return entity;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected String getPropertyValue(PropertyStringSerialization entity) {
		return entity.getValueString();
	}

	@Override
	protected void setPropertyValue(PropertyStringSerialization entity, String value) {
		entity.setValueString(value);
	}

	@Override
	protected void checkPropertyValue(String expectedValue, String actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
