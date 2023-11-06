package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyShortSerialization;

public class PropertyShortSerializationTest extends BasePropertySerializationTest<PropertyShortSerialization, Short> {

	@Entity( //
			name = PropertyShortSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueShort", type = Type.SHORT) //
			} //
	)
	public static interface PropertyShortSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyShortSerialization";
	}

	@Override
	protected String getXmlFileName(PropertyType type) {

		if (type == PropertyType.STANDARD) {
			return "property-short-serialization.xml";
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected Class<PropertyShortSerialization> getEntityClass() {
		return PropertyShortSerialization.class;
	}

	@Override
	protected PropertyShortSerialization createEntity() {

		PropertyShortSerialization entity = new PropertyShortSerialization();

		entity.setId("property-short-serialization");
		entity.setValueShort((short) 1);

		return entity;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected Short getPropertyValue(PropertyShortSerialization entity) {
		return entity.getValueShort();
	}

	@Override
	protected void setPropertyValue(PropertyShortSerialization entity, Short value) {
		entity.setValueShort(value);
	}

	@Override
	protected void checkPropertyValue(Short expectedValue, Short actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
