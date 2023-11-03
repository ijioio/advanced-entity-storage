package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyStringEmptySerialization;

public class PropertyStringEmptySerializationTest
		extends BasePropertySerializationTest<PropertyStringEmptySerialization, String> {

	@Entity( //
			name = PropertyStringEmptySerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING) //
			} //
	)
	public static interface PropertyStringEmptySerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringEmptySerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-string-empty-serialization.xml";
	}

	@Override
	protected Class<PropertyStringEmptySerialization> getEntityClass() {
		return PropertyStringEmptySerialization.class;
	}

	@Override
	protected PropertyStringEmptySerialization createEntity() {

		PropertyStringEmptySerialization entity = new PropertyStringEmptySerialization();

		entity.setId("property-string-empty-serialization");
		entity.setValueString("");

		return entity;
	}

	@Override
	protected String getPropertyValue(PropertyStringEmptySerialization entity) {
		return entity.getValueString();
	}

	@Override
	protected void checkPropertyValue(String expectedValue, String actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
