package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyByteSerialization;

public class PropertyByteSerializationTest extends BasePropertySerializationTest<PropertyByteSerialization, Byte> {

	@Entity( //
			name = PropertyByteSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByte", type = Type.BYTE) //
			} //
	)
	public static interface PropertyByteSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteSerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-byte-serialization.xml";
	}

	@Override
	protected Class<PropertyByteSerialization> getEntityClass() {
		return PropertyByteSerialization.class;
	}

	@Override
	protected PropertyByteSerialization createEntity() {

		PropertyByteSerialization entity = new PropertyByteSerialization();

		entity.setId("property-byte-serialization");
		entity.setValueByte((byte) 1);

		return entity;
	}

	@Override
	protected Byte getPropertyValue(PropertyByteSerialization entity) {
		return entity.getValueByte();
	}

	@Override
	protected void checkPropertyValue(Byte expectedValue, Byte actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
