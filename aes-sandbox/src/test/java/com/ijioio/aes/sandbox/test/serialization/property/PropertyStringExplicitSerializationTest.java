package com.ijioio.aes.sandbox.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.test.model.StringExplicitSerialization;

public class StringExplicitSerializationTest {

	@Entity( //
			name = StringExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = @Type(name = "java.lang.String")) //
			} //
	)
	public static interface StringExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.StringExplicitSerialization";
	}

	private Path path;

	private StringExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("string-explicit-serialization.xml").toURI());

		model = new StringExplicitSerialization();

		model.setId("string-explicit-serialization");
		model.setValueString("value");
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

		StringExplicitSerialization actual = XmlUtil.read(handler, StringExplicitSerialization.class,
				Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
		StringExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueString(), actual.getValueString());
	}
}
