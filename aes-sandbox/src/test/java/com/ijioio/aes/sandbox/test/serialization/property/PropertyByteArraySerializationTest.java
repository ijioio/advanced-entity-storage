package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyByteArraySerialization;

public class PropertyByteArraySerializationTest
		extends BasePropertySerializationTest<PropertyByteArraySerialization, byte[]> {

	@Entity( //
			name = PropertyByteArraySerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByteArray", type = Type.BYTE_ARRAY) //
			} //
	)
	public static interface PropertyByteArraySerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteArraySerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-byte-array-serialization.xml";
	}

	@Override
	protected Class<PropertyByteArraySerialization> getEntityClass() {
		return PropertyByteArraySerialization.class;
	}

	@Override
	protected PropertyByteArraySerialization createEntity() {

		PropertyByteArraySerialization entity = new PropertyByteArraySerialization();

		entity.setId("property-byte-array-serialization");
		entity.setValueByteArray("value".getBytes(StandardCharsets.UTF_8));

		return entity;
	}

	@Override
	protected byte[] getPropertyValue(PropertyByteArraySerialization entity) {
		return entity.getValueByteArray();
	}

	@Override
	protected void checkPropertyValue(byte[] expectedValue, byte[] actualValue) {
		Assertions.assertArrayEquals(expectedValue, actualValue);
	}
}
