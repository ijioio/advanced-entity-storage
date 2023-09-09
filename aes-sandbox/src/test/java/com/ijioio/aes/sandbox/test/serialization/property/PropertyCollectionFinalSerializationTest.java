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

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyCollectionFinalSerialization;

public class PropertyCollectionFinalSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyCollectionFinalSerializationPrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = Type.LIST, parameters = Type.STRING), //
					@Type(name = "List<Month>", type = Type.LIST, parameters = "java.time.Month"), //
					@Type(name = "List<Object>", type = Type.LIST, parameters = "java.lang.Object"), //
					@Type(name = "Set<String>", type = Type.SET, parameters = Type.STRING), //
					@Type(name = "Set<Month>", type = Type.SET, parameters = "java.time.Month"), //
					@Type(name = "Set<Object>", type = Type.SET, parameters = "java.lang.Object") //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = "List<String>", attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueEnumList", type = "List<Month>", attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueObjectList", type = "List<Object>", attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueStringSet", type = "Set<String>", attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueEnumSet", type = "Set<Month>", attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueObjectSet", type = "Set<Object>", attributes = Attribute.FINAL) //
			} //
	)
	public static interface PropertyCollectionFinalSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyCollectionFinalSerialization";
	}

	private Path path;

	private PropertyCollectionFinalSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths
				.get(getClass().getClassLoader().getResource("property-collection-final-serialization.xml").toURI());

		ArrayList<String> stringList = new ArrayList<>(Arrays.asList("value1", "value2", "value3"));
		ArrayList<Month> enumList = new ArrayList<>(Arrays.asList(Month.JANUARY, Month.FEBRUARY, Month.MARCH));
		ArrayList<Object> objectList = new ArrayList<>(Arrays.asList("value", Month.JANUARY));

		Set<String> stringSet = new LinkedHashSet<>(Arrays.asList("value1", "value2", "value3"));
		Set<Month> enumSet = new LinkedHashSet<>(Arrays.asList(Month.JANUARY, Month.FEBRUARY, Month.MARCH));
		Set<Object> objectSet = new LinkedHashSet<>(Arrays.asList("value", Month.JANUARY));

		model = new PropertyCollectionFinalSerialization();

		model.setId("property-collection-final-serialization");

		model.getValueStringList().clear();
		model.getValueStringList().addAll(stringList);

		model.getValueEnumList().clear();
		model.getValueEnumList().addAll(enumList);

		model.getValueObjectList().clear();
		model.getValueObjectList().addAll(objectList);

		model.getValueStringSet().clear();
		model.getValueStringSet().addAll(stringSet);

		model.getValueEnumSet().clear();
		model.getValueEnumSet().addAll(enumSet);

		model.getValueObjectSet().clear();
		model.getValueObjectSet().addAll(objectSet);
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

		PropertyCollectionFinalSerialization actual = XmlUtil.read(handler, PropertyCollectionFinalSerialization.class,
				readString(path));
		PropertyCollectionFinalSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueStringList(), actual.getValueStringList());
		Assertions.assertEquals(expected.getValueEnumList(), actual.getValueEnumList());
		Assertions.assertEquals(expected.getValueObjectList(), actual.getValueObjectList());
		Assertions.assertEquals(expected.getValueStringSet(), actual.getValueStringSet());
		Assertions.assertEquals(expected.getValueEnumSet(), actual.getValueEnumSet());
		Assertions.assertEquals(expected.getValueObjectSet(), actual.getValueObjectSet());
	}
}
