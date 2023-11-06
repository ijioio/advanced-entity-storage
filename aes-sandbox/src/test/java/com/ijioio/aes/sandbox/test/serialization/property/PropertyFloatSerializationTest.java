package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyFloatSerialization;

public class PropertyFloatSerializationTest extends BasePropertySerializationTest<PropertyFloatSerialization, Float> {

	@Entity( //
			name = PropertyFloatSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueFloat", type = Type.FLOAT) //
			} //
	)
	public static interface PropertyFloatSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyFloatSerialization";
	}

	@Override
	protected String getXmlFileName(PropertyType type) {

		if (type == PropertyType.STANDARD) {
			return "property-float-serialization.xml";
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected Class<PropertyFloatSerialization> getEntityClass() {
		return PropertyFloatSerialization.class;
	}

	@Override
	protected PropertyFloatSerialization createEntity() {

		PropertyFloatSerialization entity = new PropertyFloatSerialization();

		entity.setId("property-float-serialization");
		entity.setValueFloat((float) 1.0);

		return entity;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected Float getPropertyValue(PropertyFloatSerialization entity) {
		return entity.getValueFloat();
	}

	@Override
	protected void setPropertyValue(PropertyFloatSerialization entity, Float value) {
		entity.setValueFloat(value);
	}

	@Override
	protected void checkPropertyValue(Float expectedValue, Float actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
