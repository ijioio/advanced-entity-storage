package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
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
import com.ijioio.test.model.PropertyCollectionExplicitSerialization;

public class PropertyCollectionExplicitSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyCollectionExplicitSerializationPrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = "java.util.List", parameters = Type.STRING), //
					@Type(name = "List<Month>", type = "java.util.List", parameters = "java.time.Month"), //
					@Type(name = "List<Object>", type = "java.util.List", parameters = "java.lang.Object"), //
					@Type(name = "Set<String>", type = "java.util.Set", parameters = Type.STRING), //
					@Type(name = "Set<Month>", type = "java.util.Set", parameters = "java.time.Month"), //
					@Type(name = "Set<Object>", type = "java.util.Set", parameters = "java.lang.Object") //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = "List<String>"), //
					@EntityProperty(name = "valueEnumList", type = "List<Month>"), //
					@EntityProperty(name = "valueObjectList", type = "List<Object>"), //
					@EntityProperty(name = "valueStringSet", type = "Set<String>"), //
					@EntityProperty(name = "valueEnumSet", type = "Set<Month>"), //
					@EntityProperty(name = "valueObjectSet", type = "Set<Object>") //
			} //
	)
	public static interface PropertyCollectionExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyCollectionExplicitSerialization";
	}

	private Path path;

	private PropertyCollectionExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths
				.get(getClass().getClassLoader().getResource("property-collection-explicit-serialization.xml").toURI());

		ArrayList<String> stringList = new ArrayList<>(Arrays.asList("value1", "value2", "value3"));
		ArrayList<Month> enumList = new ArrayList<>(Arrays.asList(Month.JANUARY, Month.FEBRUARY, Month.MARCH));
		ArrayList<Object> objectList = new ArrayList<>(Arrays.asList("value", Month.JANUARY));

		Set<String> stringSet = new LinkedHashSet<>(Arrays.asList("value1", "value2", "value3"));
		Set<Month> enumSet = new LinkedHashSet<>(Arrays.asList(Month.JANUARY, Month.FEBRUARY, Month.MARCH));
		Set<Object> objectSet = new LinkedHashSet<>(Arrays.asList("value", Month.JANUARY));

		model = new PropertyCollectionExplicitSerialization();

		model.setId("property-collection-explicit-serialization");
		model.setValueStringList(stringList);
		model.setValueEnumList(enumList);
		model.setValueObjectList(objectList);
		model.setValueStringSet(stringSet);
		model.setValueEnumSet(enumSet);
		model.setValueObjectSet(objectSet);
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

		PropertyCollectionExplicitSerialization actual = XmlUtil.read(handler,
				PropertyCollectionExplicitSerialization.class, readString(path));
		PropertyCollectionExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueStringList(), actual.getValueStringList());
		Assertions.assertEquals(expected.getValueEnumList(), actual.getValueEnumList());
		Assertions.assertEquals(expected.getValueObjectList(), actual.getValueObjectList());
		Assertions.assertEquals(expected.getValueStringSet(), actual.getValueStringSet());
		Assertions.assertEquals(expected.getValueEnumSet(), actual.getValueEnumSet());
		Assertions.assertEquals(expected.getValueObjectSet(), actual.getValueObjectSet());
	}
}
