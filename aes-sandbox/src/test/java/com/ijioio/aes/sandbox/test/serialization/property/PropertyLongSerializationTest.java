package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyLongSerialization;

public class PropertyLongSerializationTest extends BasePropertySerializationTest<PropertyLongSerialization, Long> {

	@Entity( //
			name = PropertyLongSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLong", type = Type.LONG) //
			} //
	)
	public static interface PropertyLongSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLongSerialization";
	}

	@Override
	protected String getXmlFileName(PropertyType type) {

		if (type == PropertyType.STANDARD) {
			return "property-long-serialization.xml";
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected Class<PropertyLongSerialization> getEntityClass() {
		return PropertyLongSerialization.class;
	}

	@Override
	protected PropertyLongSerialization createEntity() {

		PropertyLongSerialization entity = new PropertyLongSerialization();

		entity.setId("property-long-serialization");
		entity.setValueLong(1);

		return entity;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected Long getPropertyValue(PropertyLongSerialization entity) {
		return entity.getValueLong();
	}

	@Override
	protected void setPropertyValue(PropertyLongSerialization entity, Long value) {
		entity.setValueLong(value);
	}

	@Override
	protected void checkPropertyValue(Long expectedValue, Long actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
