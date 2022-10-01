package com.ijioio.aes.sandbox.test;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.test.model.MapSerialization;

public class MapSerializationTest {

	@Entity( //
			name = MapSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueStringMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = Type.STRING), @Type(name = Type.STRING) }), //
					@EntityProperty(name = "valueEnumMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = "java.time.Month"), @Type(name = "java.time.Month") }), //
					@EntityProperty(name = "valueObjectMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = "java.lang.Object"), @Type(name = "java.lang.Object") }) //
			} //
	)

	public static interface MapSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.MapSerialization";
	}

	private MapSerialization model;

	@BeforeEach
	public void before() {

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

		model = new MapSerialization();

		model.setId("map-serialization");
		model.setValueStringMap(stringMap);
		model.setValueEnumMap(enumMap);
		model.setValueObjectMap(objectMap);
	}

	@Test
	public void testWrite() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		String actual = XmlUtil.write(handler, model);
		String expected = Files
				.readString(Paths.get(getClass().getClassLoader().getResource("map-serialization.xml").toURI()));

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		MapSerialization actual = XmlUtil.read(handler, MapSerialization.class,
				Files.readString(Paths.get(getClass().getClassLoader().getResource("map-serialization.xml").toURI())));
		MapSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueStringMap(), actual.getValueStringMap());
		Assertions.assertEquals(expected.getValueEnumMap(), actual.getValueEnumMap());
		Assertions.assertEquals(expected.getValueObjectMap(), actual.getValueObjectMap());
	}
}
