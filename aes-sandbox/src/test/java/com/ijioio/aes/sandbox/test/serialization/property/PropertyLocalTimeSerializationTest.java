package com.ijioio.aes.sandbox.test.serialization.property;

import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyLocalTimeSerialization;

public class PropertyLocalTimeSerializationTest
		extends BasePropertySerializationTest<PropertyLocalTimeSerialization, LocalTime> {

	@Entity( //
			name = PropertyLocalTimeSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalTime", type = Type.LOCAL_TIME) //
			} //
	)
	public static interface PropertyLocalTimeSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLocalTimeSerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-local-time-serialization.xml";
	}

	@Override
	protected Class<PropertyLocalTimeSerialization> getEntityClass() {
		return PropertyLocalTimeSerialization.class;
	}

	@Override
	protected PropertyLocalTimeSerialization createEntity() {

		PropertyLocalTimeSerialization entity = new PropertyLocalTimeSerialization();

		entity.setId("property-local-time-serialization");
		entity.setValueLocalTime(LocalTime.of(14, 25, 40, 123456789));

		return entity;
	}

	@Override
	protected LocalTime getPropertyValue(PropertyLocalTimeSerialization entity) {
		return entity.getValueLocalTime();
	}

	@Override
	protected void checkPropertyValue(LocalTime expectedValue, LocalTime actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
