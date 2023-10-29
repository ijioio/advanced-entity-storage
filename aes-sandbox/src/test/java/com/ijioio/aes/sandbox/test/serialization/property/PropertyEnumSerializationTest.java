package com.ijioio.aes.sandbox.test.serialization.property;

import java.time.Month;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.test.model.PropertyEnumSerialization;

public class PropertyEnumSerializationTest extends BasePropertySerializationTest<PropertyEnumSerialization, Month> {

	@Entity( //
			name = PropertyEnumSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueEnum", type = "java.time.Month") //
			} //
	)
	public static interface PropertyEnumSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEnumSerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-enum-serialization.xml";
	}

	@Override
	protected Class<PropertyEnumSerialization> getEntityClass() {
		return PropertyEnumSerialization.class;
	}

	@Override
	protected PropertyEnumSerialization createEntity() {

		PropertyEnumSerialization entity = new PropertyEnumSerialization();

		entity.setId("property-enum-serialization");
		entity.setValueEnum(Month.JANUARY);

		return entity;
	}

	@Override
	protected Month getPropertyValue(PropertyEnumSerialization entity) {
		return entity.getValueEnum();
	}

	@Override
	protected void checkPropertyValue(Month expectedValue, Month actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
