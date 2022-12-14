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
			properties = { //
					@EntityProperty(name = "valueString", type = @Type(name = Type.STRING)), //
					@EntityProperty(name = "valueStringList", type = @Type(name = Type.LIST), parameters = @Type(name = Type.STRING)), //
					@EntityProperty(name = "valueStringSet", type = @Type(name = Type.SET), parameters = @Type(name = Type.STRING)), //
					@EntityProperty(name = "valueStringMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = Type.STRING), @Type(name = Type.STRING) }) //
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
