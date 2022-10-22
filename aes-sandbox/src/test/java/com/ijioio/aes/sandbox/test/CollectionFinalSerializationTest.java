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
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.test.model.CollectionFinalSerialization;

public class CollectionFinalSerializationTest {

	@Entity( //
			name = CollectionFinalSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = @Type(name = Type.LIST), parameters = @Type(name = Type.STRING), attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueEnumList", type = @Type(name = Type.LIST), parameters = @Type(name = "java.time.Month"), attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueObjectList", type = @Type(name = Type.LIST), parameters = @Type(name = "java.lang.Object"), attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueStringSet", type = @Type(name = Type.SET), parameters = @Type(name = Type.STRING), attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueEnumSet", type = @Type(name = Type.SET), parameters = @Type(name = "java.time.Month"), attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueObjectSet", type = @Type(name = Type.SET), parameters = @Type(name = "java.lang.Object"), attributes = Attribute.FINAL) //
			} //
	)
	public static interface CollectionFinalSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.CollectionFinalSerialization";
	}

	private Path path;

	private CollectionFinalSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("collection-final-serialization.xml").toURI());

		ArrayList<String> stringList = new ArrayList<>(Arrays.asList("value1", "value2", "value3"));
		ArrayList<Month> enumList = new ArrayList<>(Arrays.asList(Month.JANUARY, Month.FEBRUARY, Month.MARCH));
		ArrayList<Object> objectList = new ArrayList<>(Arrays.asList("value", Month.JANUARY));

		Set<String> stringSet = new LinkedHashSet<>(Arrays.asList("value1", "value2", "value3"));
		Set<Month> enumSet = new LinkedHashSet<>(Arrays.asList(Month.JANUARY, Month.FEBRUARY, Month.MARCH));
		Set<Object> objectSet = new LinkedHashSet<>(Arrays.asList("value", Month.JANUARY));

		model = new CollectionFinalSerialization();

		model.setId("collection-final-serialization");

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
		String expected = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n"));

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		CollectionFinalSerialization actual = XmlUtil.read(handler, CollectionFinalSerialization.class,
				Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
		CollectionFinalSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueStringList(), actual.getValueStringList());
		Assertions.assertEquals(expected.getValueEnumList(), actual.getValueEnumList());
		Assertions.assertEquals(expected.getValueObjectList(), actual.getValueObjectList());
		Assertions.assertEquals(expected.getValueStringSet(), actual.getValueStringSet());
		Assertions.assertEquals(expected.getValueEnumSet(), actual.getValueEnumSet());
		Assertions.assertEquals(expected.getValueObjectSet(), actual.getValueObjectSet());
	}
}
