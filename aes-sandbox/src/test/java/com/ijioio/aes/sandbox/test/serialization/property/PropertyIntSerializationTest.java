package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyIntSerialization;

public class PropertyIntSerializationTest extends BasePropertySerializationTest<PropertyIntSerialization, Integer> {

	@Entity( //
			name = PropertyIntSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueInt", type = Type.INT) //
			} //
	)
	public static interface PropertyIntSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyIntSerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-int-serialization.xml";
	}

	@Override
	protected Class<PropertyIntSerialization> getEntityClass() {
		return PropertyIntSerialization.class;
	}

	@Override
	protected PropertyIntSerialization createEntity() {

		PropertyIntSerialization entity = new PropertyIntSerialization();

		entity.setId("property-int-serialization");
		entity.setValueInt(1);

		return entity;
	}

	@Override
	protected Integer getPropertyValue(PropertyIntSerialization entity) {
		return entity.getValueInt();
	}

	@Override
	protected void checkPropertyValue(Integer expectedValue, Integer actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
