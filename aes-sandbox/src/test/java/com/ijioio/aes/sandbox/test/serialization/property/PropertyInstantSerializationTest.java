package com.ijioio.aes.sandbox.test.serialization.property;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyInstantSerialization;

public class PropertyInstantSerializationTest
		extends BasePropertySerializationTest<PropertyInstantSerialization, Instant> {

	@Entity( //
			name = PropertyInstantSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueInstant", type = Type.INSTANT) //
			} //
	)
	public static interface PropertyInstantSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyInstantSerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-instant-serialization.xml";
	}

	@Override
	protected Class<PropertyInstantSerialization> getEntityClass() {
		return PropertyInstantSerialization.class;
	}

	@Override
	protected PropertyInstantSerialization createEntity() {

		PropertyInstantSerialization entity = new PropertyInstantSerialization();

		entity.setId("property-instant-serialization");
		entity.setValueInstant(
				LocalDateTime.of(2022, Month.AUGUST, 22, 14, 25, 40, 123456789).atZone(ZoneOffset.UTC).toInstant());

		return entity;
	}

	@Override
	protected Instant getPropertyValue(PropertyInstantSerialization entity) {
		return entity.getValueInstant();
	}

	@Override
	protected void checkPropertyValue(Instant expectedValue, Instant actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
