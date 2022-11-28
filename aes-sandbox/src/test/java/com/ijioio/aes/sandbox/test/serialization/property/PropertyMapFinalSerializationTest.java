package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyMapFinalSerialization;

public class PropertyMapFinalSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyMapFinalSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueStringMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = Type.STRING), @Type(name = Type.STRING) }, attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueEnumMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = "java.time.Month"),
							@Type(name = "java.time.Month") }, attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueObjectMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = "java.lang.Object"),
							@Type(name = "java.lang.Object") }, attributes = Attribute.FINAL) //
			} //
	)
	public static interface PropertyMapFinalSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyMapFinalSerialization";
	}

	private Path path;

	private PropertyMapFinalSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-map-final-serialization.xml").toURI());

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

		model = new PropertyMapFinalSerialization();

		model.setId("property-map-final-serialization");

		model.getValueStringMap().clear();
		model.getValueStringMap().putAll(stringMap);

		model.getValueEnumMap().clear();
		model.getValueEnumMap().putAll(enumMap);

		model.getValueObjectMap().clear();
		model.getValueObjectMap().putAll(objectMap);
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

		PropertyMapFinalSerialization actual = XmlUtil.read(handler, PropertyMapFinalSerialization.class,
				readString(path));
		PropertyMapFinalSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueStringMap(), actual.getValueStringMap());
		Assertions.assertEquals(expected.getValueEnumMap(), actual.getValueEnumMap());
		Assertions.assertEquals(expected.getValueObjectMap(), actual.getValueObjectMap());
	}
}
