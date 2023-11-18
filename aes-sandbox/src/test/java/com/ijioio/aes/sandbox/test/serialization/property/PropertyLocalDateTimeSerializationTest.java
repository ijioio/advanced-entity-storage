package com.ijioio.aes.sandbox.test.serialization.property;

import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyLocalDateTimeSerialization;

public class PropertyLocalDateTimeSerializationTest
		extends BasePropertySerializationTest<PropertyLocalDateTimeSerialization, LocalDateTime> {

	@Entity( //
			name = PropertyLocalDateTimeSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDateTime", type = Type.LOCAL_DATE_TIME) //
			} //
	)
	public static interface PropertyLocalDateTimeSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLocalDateTimeSerialization";
	}

	@Override
	protected String getXmlFileName() {
		return "property-local-date-time-serialization.xml";
	}

	@Override
	protected String getNullXmlFileName() {
		return "property-local-date-time-null-serialization.xml";
	}

	@Override
	protected Class<PropertyLocalDateTimeSerialization> getEntityClass() {
		return PropertyLocalDateTimeSerialization.class;
	}

	@Override
	protected PropertyLocalDateTimeSerialization createEntity() {

		PropertyLocalDateTimeSerialization entity = new PropertyLocalDateTimeSerialization();

		entity.setId("property-local-date-time-serialization");
		entity.setValueLocalDateTime(LocalDateTime.of(2022, Month.AUGUST, 22, 14, 25, 40, 123456789));

		return entity;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected LocalDateTime getPropertyValue(PropertyLocalDateTimeSerialization entity) {
		return entity.getValueLocalDateTime();
	}

	@Override
	protected void setPropertyValue(PropertyLocalDateTimeSerialization entity, LocalDateTime value) {
		entity.setValueLocalDateTime(value);
	}

	@Override
	protected void checkPropertyValue(LocalDateTime expectedValue, LocalDateTime actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
