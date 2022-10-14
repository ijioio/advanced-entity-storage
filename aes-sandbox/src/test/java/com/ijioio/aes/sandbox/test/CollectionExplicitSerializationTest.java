package com.ijioio.aes.sandbox.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
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
import com.ijioio.test.model.CollectionExplicitSerialization;

public class CollectionExplicitSerializationTest {

	@Entity( //
			name = CollectionExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = @Type(name = "java.util.List"), parameters = @Type(name = Type.STRING)), //
					@EntityProperty(name = "valueEnumList", type = @Type(name = "java.util.List"), parameters = @Type(name = "java.time.Month")), //
					@EntityProperty(name = "valueObjectList", type = @Type(name = "java.util.List"), parameters = @Type(name = "java.lang.Object")), //
					@EntityProperty(name = "valueStringSet", type = @Type(name = "java.util.Set"), parameters = @Type(name = Type.STRING)), //
					@EntityProperty(name = "valueEnumSet", type = @Type(name = "java.util.Set"), parameters = @Type(name = "java.time.Month")), //
					@EntityProperty(name = "valueObjectSet", type = @Type(name = "java.util.Set"), parameters = @Type(name = "java.lang.Object")) //
			} //
	)
	public static interface CollectionExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.CollectionExplicitSerialization";
	}

	private Path path;

	private CollectionExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("collection-explicit-serialization.xml").toURI());

		ArrayList<String> stringList = new ArrayList<>(Arrays.asList("value1", "value2", "value3"));
		ArrayList<Month> enumList = new ArrayList<>(Arrays.asList(Month.JANUARY, Month.FEBRUARY, Month.MARCH));
		ArrayList<Object> objectList = new ArrayList<>(Arrays.asList("value", Month.JANUARY));

		Set<String> stringSet = new LinkedHashSet<>(Arrays.asList("value1", "value2", "value3"));
		Set<Month> enumSet = new LinkedHashSet<>(Arrays.asList(Month.JANUARY, Month.FEBRUARY, Month.MARCH));
		Set<Object> objectSet = new LinkedHashSet<>(Arrays.asList("value", Month.JANUARY));

		model = new CollectionExplicitSerialization();

		model.setId("collection-explicit-serialization");
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
		String expected = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		CollectionExplicitSerialization actual = XmlUtil.read(handler, CollectionExplicitSerialization.class,
				new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
		CollectionExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueStringList(), actual.getValueStringList());
		Assertions.assertEquals(expected.getValueEnumList(), actual.getValueEnumList());
		Assertions.assertEquals(expected.getValueObjectList(), actual.getValueObjectList());
		Assertions.assertEquals(expected.getValueStringSet(), actual.getValueStringSet());
		Assertions.assertEquals(expected.getValueEnumSet(), actual.getValueEnumSet());
		Assertions.assertEquals(expected.getValueObjectSet(), actual.getValueObjectSet());
	}
}
