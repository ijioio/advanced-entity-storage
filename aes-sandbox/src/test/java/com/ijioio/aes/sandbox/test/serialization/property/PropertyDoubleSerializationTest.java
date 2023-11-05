package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyDoubleSerialization;

public class PropertyDoubleSerializationTest
		extends BasePropertySerializationTest<PropertyDoubleSerialization, Double> {

	@Entity( //
			name = PropertyDoubleSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueDouble", type = Type.DOUBLE) //
			} //
	)
	public static interface PropertyDoubleSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyDoubleSerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-double-serialization.xml";
	}

	@Override
	protected Class<PropertyDoubleSerialization> getEntityClass() {
		return PropertyDoubleSerialization.class;
	}

	@Override
	protected PropertyDoubleSerialization createEntity() {

		PropertyDoubleSerialization entity = new PropertyDoubleSerialization();

		entity.setId("property-double-serialization");
		entity.setValueDouble(1.0);

		return entity;
	}

	@Override
	protected Double getPropertyValue(PropertyDoubleSerialization entity) {
		return entity.getValueDouble();
	}

	@Override
	protected void checkPropertyValue(Double expectedValue, Double actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
