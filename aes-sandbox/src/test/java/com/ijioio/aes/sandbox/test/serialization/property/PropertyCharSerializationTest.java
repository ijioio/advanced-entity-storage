package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyCharSerialization;

public class PropertyCharSerializationTest extends BasePropertySerializationTest<PropertyCharSerialization, Character> {

	@Entity( //
			name = PropertyCharSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueChar", type = Type.CHAR) //
			} //
	)
	public static interface PropertyCharSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyCharSerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-char-serialization.xml";
	}

	@Override
	protected Class<PropertyCharSerialization> getEntityClass() {
		return PropertyCharSerialization.class;
	}

	@Override
	protected PropertyCharSerialization createEntity() {

		PropertyCharSerialization entity = new PropertyCharSerialization();

		entity.setId("property-char-serialization");
		entity.setValueChar('a');

		return entity;
	}

	@Override
	protected Character getPropertyValue(PropertyCharSerialization entity) {
		return entity.getValueChar();
	}

	@Override
	protected void checkPropertyValue(Character expectedValue, Character actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
