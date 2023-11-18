package com.ijioio.aes.sandbox.test.serialization.property;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyStringMapSerialization;

public class PropertyStringMapSerializationTest
		extends BasePropertyMapSerializationTest<PropertyStringMapSerialization, Map<String, String>, String> {

	@Entity( //
			name = PropertyStringMapSerializationPrototype.NAME, //
			types = { //
					@Type(name = "Map<String, String>", type = Type.MAP, parameters = { @Parameter(name = Type.STRING),
							@Parameter(name = Type.STRING) }) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringMap", type = "Map<String, String>") //
			} //
	)
	public static interface PropertyStringMapSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringMapSerialization";
	}

	@Override
	protected String getXmlFileName() {
		return "property-string-map-serialization.xml";
	}

	@Override
	protected String getNullXmlFileName() {
		return "property-string-map-null-serialization.xml";
	}

	@Override
	protected String getEntriesEmptyXmlFileName() {
		return "property-string-map-entries-empty-serialization.xml";
	}

	@Override
	protected String getEntriesNullXmlFileName() {
		return "property-string-map-entries-null-serialization.xml";
	}

	@Override
	protected Class<PropertyStringMapSerialization> getEntityClass() {
		return PropertyStringMapSerialization.class;
	}

	@Override
	protected PropertyStringMapSerialization createEntity() {

		PropertyStringMapSerialization entity = new PropertyStringMapSerialization();

		entity.setId("property-string-map-serialization");

		Map<String, String> value = new LinkedHashMap<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.put(String.format("key%s", i + 1), String.format("value%s", i + 1));
		}

		entity.setValueStringMap(value);

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
		return true;
	}

	@Override
	protected Map<String, String> getPropertyValue(PropertyStringMapSerialization entity) {
		return entity.getValueStringMap();
	}

	@Override
	protected void setPropertyValue(PropertyStringMapSerialization entity, Map<String, String> value) {
		entity.setValueStringMap(value);
	}

	@Override
	protected void checkPropertyValue(Map<String, String> expectedValue, Map<String, String> actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
