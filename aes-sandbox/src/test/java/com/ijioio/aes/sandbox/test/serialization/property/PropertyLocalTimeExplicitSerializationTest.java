package com.ijioio.aes.sandbox.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.test.model.LocalTimeExplicitSerialization;

public class LocalTimeExplicitSerializationTest {

	@Entity( //
			name = LocalTimeExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalTime", type = @Type(name = "java.time.LocalTime")) //
			} //
	)
	public static interface LocalTimeExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.LocalTimeExplicitSerialization";
	}

	private Path path;

	private LocalTimeExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("local-time-explicit-serialization.xml").toURI());

		model = new LocalTimeExplicitSerialization();

		model.setId("local-time-explicit-serialization");
		model.setValueLocalTime(LocalTime.of(14, 25, 40, 123456789));
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

		LocalTimeExplicitSerialization actual = XmlUtil.read(handler, LocalTimeExplicitSerialization.class,
				Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
		LocalTimeExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueLocalTime(), actual.getValueLocalTime());
	}
}
