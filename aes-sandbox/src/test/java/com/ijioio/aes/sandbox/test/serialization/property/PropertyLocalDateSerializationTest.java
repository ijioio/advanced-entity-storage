package com.ijioio.aes.sandbox.test.serialization.property;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyLocalDateSerialization;

public class PropertyLocalDateSerializationTest
		extends BasePropertySerializationTest<PropertyLocalDateSerialization, LocalDate> {

	@Entity( //
			name = PropertyLocalDateSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDate", type = Type.LOCAL_DATE) //
			} //
	)
	public static interface PropertyLocalDateSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLocalDateSerialization";
	}

	@Override
	protected String getXmlFileName() {
		return "property-local-date-serialization.xml";
	}

	@Override
	protected String getNullXmlFileName() {
		return "property-local-date-null-serialization.xml";
	}

	@Override
	protected Class<PropertyLocalDateSerialization> getEntityClass() {
		return PropertyLocalDateSerialization.class;
	}

	@Override
	protected PropertyLocalDateSerialization createEntity() {

		PropertyLocalDateSerialization entity = new PropertyLocalDateSerialization();

		entity.setId("property-local-date-serialization");
		entity.setValueLocalDate(LocalDate.of(2022, Month.AUGUST, 22));

		return entity;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected LocalDate getPropertyValue(PropertyLocalDateSerialization entity) {
		return entity.getValueLocalDate();
	}

	@Override
	protected void setPropertyValue(PropertyLocalDateSerialization entity, LocalDate value) {
		entity.setValueLocalDate(value);
	}

	@Override
	protected void checkPropertyValue(LocalDate expectedValue, LocalDate actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
