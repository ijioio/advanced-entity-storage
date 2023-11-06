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
	protected String getXmlFileName(PropertyType type) {

		if (type == PropertyType.STANDARD) {
			return "property-string-map-serialization.xml";
		} else if (type == PropertyType.NULL) {
			return "property-string-map-null-serialization.xml";
		} else {
			throw new UnsupportedOperationException();
		}
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
