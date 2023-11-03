package com.ijioio.aes.sandbox.test.serialization.property;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.serialization.property.BasePropertySerializationTest.Some;
import com.ijioio.test.model.PropertyClassMapSerialization;

public class PropertyClassMapSerializationTest extends
		BasePropertySerializationTest<PropertyClassMapSerialization, Map<Class<? extends Some>, Class<? extends Some>>> {

	@Entity( //
			name = PropertyClassMapSerializationPrototype.NAME, //
			types = { //
					@Type(name = "Class<? extends Some>", type = Type.CLASS, parameters = @Parameter(name = Some.NAME, wildcard = true)), //
					@Type(name = "Map<Class<? extends Some>, Class<? extends Some>>", type = Type.MAP, parameters = {
							@Parameter(name = "Class<? extends Some>"), @Parameter(name = "Class<? extends Some>") }) //
			}, //
			properties = { //
					@EntityProperty(name = "valueClassMap", type = "Map<Class<? extends Some>, Class<? extends Some>>") //
			} //
	)
	public static interface PropertyClassMapSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassMapSerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-class-map-serialization.xml";
	}

	@Override
	protected Class<PropertyClassMapSerialization> getEntityClass() {
		return PropertyClassMapSerialization.class;
	}

	@Override
	protected PropertyClassMapSerialization createEntity() {

		PropertyClassMapSerialization entity = new PropertyClassMapSerialization();

		entity.setId("property-class-map-serialization");

		Map<Class<? extends Some>, Class<? extends Some>> value = new LinkedHashMap<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.put(types.get(i), types.get(i));
		}

		entity.setValueClassMap(value);

		return entity;
	}

	@Override
	protected Map<Class<? extends Some>, Class<? extends Some>> getPropertyValue(PropertyClassMapSerialization entity) {
		return entity.getValueClassMap();
	}

	@Override
	protected void checkPropertyValue(Map<Class<? extends Some>, Class<? extends Some>> expectedValue,
			Map<Class<? extends Some>, Class<? extends Some>> actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
