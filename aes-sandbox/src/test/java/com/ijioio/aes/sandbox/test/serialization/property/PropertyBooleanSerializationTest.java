package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyBooleanSerialization;

public class PropertyBooleanSerializationTest
		extends BasePropertySerializationTest<PropertyBooleanSerialization, Boolean> {

	@Entity( //
			name = PropertyBooleanSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueBoolean", type = Type.BOOLEAN) //
			} //
	)
	public static interface PropertyBooleanSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyBooleanSerialization";
	}

	@Override
	protected String getXmlFileName() {
		return "property-boolean-serialization.xml";
	}

	@Override
	protected String getNullXmlFileName() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Class<PropertyBooleanSerialization> getEntityClass() {
		return PropertyBooleanSerialization.class;
	}

	@Override
	protected PropertyBooleanSerialization createEntity() {

		PropertyBooleanSerialization entity = new PropertyBooleanSerialization();

		entity.setId("property-boolean-serialization");
		entity.setValueBoolean(true);

		return entity;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected Boolean getPropertyValue(PropertyBooleanSerialization entity) {
		return entity.isValueBoolean();
	}

	@Override
	protected void setPropertyValue(PropertyBooleanSerialization entity, Boolean value) {
		entity.setValueBoolean(value);
	}

	@Override
	protected void checkPropertyValue(Boolean expectedValue, Boolean actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
