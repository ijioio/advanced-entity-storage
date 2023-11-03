package com.ijioio.aes.sandbox.test.serialization.property;

import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyEnumMapSerialization;

public class PropertyEnumMapSerializationTest
		extends BasePropertySerializationTest<PropertyEnumMapSerialization, Map<Month, Month>> {

	@Entity( //
			name = PropertyEnumMapSerializationPrototype.NAME, //
			types = { //
					@Type(name = "Map<Month, Month>", type = Type.MAP, parameters = {
							@Parameter(name = "java.time.Month"), @Parameter(name = "java.time.Month") }) //
			}, //
			properties = { //
					@EntityProperty(name = "valueEnumMap", type = "Map<Month, Month>") //
			} //
	)
	public static interface PropertyEnumMapSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEnumMapSerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-enum-map-serialization.xml";
	}

	@Override
	protected Class<PropertyEnumMapSerialization> getEntityClass() {
		return PropertyEnumMapSerialization.class;
	}

	@Override
	protected PropertyEnumMapSerialization createEntity() {

		PropertyEnumMapSerialization entity = new PropertyEnumMapSerialization();

		entity.setId("property-enum-map-serialization");

		Map<Month, Month> value = new LinkedHashMap<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.put(Month.values()[i], Month.values()[i]);
		}

		entity.setValueEnumMap(value);

		return entity;
	}

	@Override
	protected Map<Month, Month> getPropertyValue(PropertyEnumMapSerialization entity) {
		return entity.getValueEnumMap();
	}

	@Override
	protected void checkPropertyValue(Map<Month, Month> expectedValue, Map<Month, Month> actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
