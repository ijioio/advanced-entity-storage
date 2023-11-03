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
		extends BasePropertySerializationTest<PropertyStringMapSerialization, Map<String, String>> {

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
	protected String getXmlFileName() throws Exception {
		return "property-string-map-serialization.xml";
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
	protected Map<String, String> getPropertyValue(PropertyStringMapSerialization entity) {
		return entity.getValueStringMap();
	}

	@Override
	protected void checkPropertyValue(Map<String, String> expectedValue, Map<String, String> actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
