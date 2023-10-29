package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyStringSerialization;

public class PropertyStringSerializationTest
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
	protected String getXmlFileName() throws Exception {
		return "property-string-serialization.xml";
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
	protected String getPropertyValue(PropertyStringSerialization entity) {
		return entity.getValueString();
	}

	@Override
	protected void checkPropertyValue(String expectedValue, String actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
