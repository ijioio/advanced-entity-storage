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
import com.ijioio.test.model.ByteArrayExplicitSerialization;

public class ByteArrayExplicitSerializationTest {

	@Entity( //
			name = ByteArrayExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByteArray", type = @Type(name = "[B")) //
			} //
	)
	public static interface ByteArrayExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.ByteArrayExplicitSerialization";
	}

	private Path path;

	private ByteArrayExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("byte-array-explicit-serialization.xml").toURI());

		model = new ByteArrayExplicitSerialization();

		model.setId("byte-array-explicit-serialization");
		model.setValueByteArray("value1\nvalue2\rvalue3\r\nvalue4".getBytes(StandardCharsets.UTF_8));
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

		ByteArrayExplicitSerialization actual = XmlUtil.read(handler, ByteArrayExplicitSerialization.class,
				Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
		ByteArrayExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertArrayEquals(expected.getValueByteArray(), actual.getValueByteArray());
	}
}
