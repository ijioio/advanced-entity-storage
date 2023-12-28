package com.ijioio.aes.serialization.test.fixture.entity.property;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyStringMapFinalSerialization;

public abstract class BasePropertyStringMapFinalSerializationTest
		extends BasePropertyMapSerializationTest<PropertyStringMapFinalSerialization, Map<String, String>, String> {

	@Entity( //
			name = PropertyStringMapFinalSerializationPrototype.NAME, //
			types = { //
					@Type(name = "Map<String, String>", type = Type.MAP, parameters = { @Parameter(name = Type.STRING),
							@Parameter(name = Type.STRING) }) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringMap", type = "Map<String, String>", attributes = Attribute.FINAL) //
			} //
	)
	public static interface PropertyStringMapFinalSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringMapFinalSerialization";
	}

	@Override
	protected Class<PropertyStringMapFinalSerialization> getEntityClass() {
		return PropertyStringMapFinalSerialization.class;
	}

	@Override
	protected PropertyStringMapFinalSerialization createEntity() {

		PropertyStringMapFinalSerialization entity = new PropertyStringMapFinalSerialization();

		entity.setId("property-string-map-final-serialization");

		Map<String, String> value = new LinkedHashMap<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.put(String.format("key%s", i + 1), String.format("value%s", i + 1));
		}

		entity.getValueStringMap().clear();
		entity.getValueStringMap().putAll(value);

		return entity;
	}

	@Override
	protected Map<String, String> createEmptyPropertyValue() {
		return new LinkedHashMap<>();
	}

	@Override
	protected Map<String, String> createAllNullPropertyValue() {

		Map<String, String> value = new LinkedHashMap<>();

		for (int j = 0; j < VALUE_MAX_COUNT; j++) {
			value.put(null, null);
		}

		return value;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected Map<String, String> getPropertyValue(PropertyStringMapFinalSerialization entity) {
		return entity.getValueStringMap();
	}

	@Override
	protected void setPropertyValue(PropertyStringMapFinalSerialization entity, Map<String, String> value) {

		entity.getValueStringMap().clear();
		entity.getValueStringMap().putAll(value);
	}

	@Override
	protected void checkPropertyValue(Map<String, String> expectedValue, Map<String, String> actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
