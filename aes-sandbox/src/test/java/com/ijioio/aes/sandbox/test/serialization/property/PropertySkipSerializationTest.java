package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertySkipSerialization;

public class PropertySkipSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertySkipSerializationPrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = Type.LIST, parameters = Type.STRING), //
					@Type(name = "Set<String>", type = Type.SET, parameters = Type.STRING), //
					@Type(name = "Map<String, String>", type = Type.MAP, parameters = { Type.STRING, Type.STRING }) //
			}, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING), //
					@EntityProperty(name = "valueStringList", type = "List<String>"), //
					@EntityProperty(name = "valueStringSet", type = "Set<String>"), //
					@EntityProperty(name = "valueStringMap", type = "Map<String, String>") //
			} //
	)
	public static interface PropertySkipSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertySkipSerialization";
	}

	private Path path;

	private PropertySkipSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-skip-serialization.xml").toURI());

		ArrayList<String> stringList = new ArrayList<>(Arrays.asList("value1", "value2"));
		Set<String> stringSet = new LinkedHashSet<>(Arrays.asList("value1", "value2"));
		Map<String, String> stringMap = new LinkedHashMap<>();

		stringMap.put("key1", "value1");
		stringMap.put("key2", "value2");

		model = new PropertySkipSerialization();

		model.setId("property-skip-serialization");
		model.setValueString("value");
		model.setValueStringList(stringList);
		model.setValueStringSet(stringSet);
		model.setValueStringMap(stringMap);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		PropertySkipSerialization actual = XmlUtil.read(handler, PropertySkipSerialization.class, readString(path));
		PropertySkipSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueString(), actual.getValueString());
		Assertions.assertEquals(expected.getValueStringList(), actual.getValueStringList());
		Assertions.assertEquals(expected.getValueStringSet(), actual.getValueStringSet());
		Assertions.assertEquals(expected.getValueStringMap(), actual.getValueStringMap());
	}
}
