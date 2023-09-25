package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyMapExplicitSerialization;

public class PropertyMapExplicitSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyMapExplicitSerializationPrototype.NAME, //
			types = { //
					@Type(name = "Map<String, String>", type = "java.util.Map", parameters = {
							@Parameter(name = Type.STRING), @Parameter(name = Type.STRING) }), //
					@Type(name = "Map<Month, Month>", type = "java.util.Map", parameters = {
							@Parameter(name = "java.time.Month"), @Parameter(name = "java.time.Month") }), //
					@Type(name = "Map<Object, Object>", type = "java.util.Map", parameters = {
							@Parameter(name = "java.lang.Object"), @Parameter(name = "java.lang.Object") }) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringMap", type = "Map<String, String>"), //
					@EntityProperty(name = "valueEnumMap", type = "Map<Month, Month>"), //
					@EntityProperty(name = "valueObjectMap", type = "Map<Object, Object>") //
			} //
	)
	public static interface PropertyMapExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyMapExplicitSerialization";
	}

	private Path path;

	private PropertyMapExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-map-explicit-serialization.xml").toURI());

		Map<String, String> stringMap = new LinkedHashMap<>();

		stringMap.put("key1", "value1");
		stringMap.put("key2", "value2");
		stringMap.put("key3", "value3");

		Map<Month, Month> enumMap = new LinkedHashMap<>();

		enumMap.put(Month.JANUARY, Month.FEBRUARY);
		enumMap.put(Month.MARCH, Month.APRIL);
		enumMap.put(Month.MAY, Month.JUNE);

		Map<Object, Object> objectMap = new LinkedHashMap<>();

		objectMap.put("key", "value");
		objectMap.put(Month.JANUARY, Month.FEBRUARY);

		model = new PropertyMapExplicitSerialization();

		model.setId("property-map-explicit-serialization");
		model.setValueStringMap(stringMap);
		model.setValueEnumMap(enumMap);
		model.setValueObjectMap(objectMap);
	}

	@Test
	public void testWrite() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		String actual = XmlUtil.write(handler, model);
		String expected = readString(path);

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		PropertyMapExplicitSerialization actual = XmlUtil.read(handler, PropertyMapExplicitSerialization.class,
				readString(path));
		PropertyMapExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueStringMap(), actual.getValueStringMap());
		Assertions.assertEquals(expected.getValueEnumMap(), actual.getValueEnumMap());
		Assertions.assertEquals(expected.getValueObjectMap(), actual.getValueObjectMap());
	}
}
