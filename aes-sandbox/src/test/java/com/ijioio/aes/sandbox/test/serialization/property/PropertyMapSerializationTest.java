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
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyMapSerialization;

public class PropertyMapSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyMapSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueStringMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = Type.STRING), @Type(name = Type.STRING) }), //
					@EntityProperty(name = "valueEnumMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = "java.time.Month"), @Type(name = "java.time.Month") }), //
					@EntityProperty(name = "valueObjectMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = "java.lang.Object"), @Type(name = "java.lang.Object") }) //
			} //
	)
	public static interface PropertyMapSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyMapSerialization";
	}

	private Path path;

	private PropertyMapSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-map-serialization.xml").toURI());

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

		model = new PropertyMapSerialization();

		model.setId("property-map-serialization");
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

		PropertyMapSerialization actual = XmlUtil.read(handler, PropertyMapSerialization.class, readString(path));
		PropertyMapSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueStringMap(), actual.getValueStringMap());
		Assertions.assertEquals(expected.getValueEnumMap(), actual.getValueEnumMap());
		Assertions.assertEquals(expected.getValueObjectMap(), actual.getValueObjectMap());
	}
}
