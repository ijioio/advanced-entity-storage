package com.ijioio.aes.sandbox.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.test.model.LocalDateTimeExplicitSerialization;

public class LocalDateTimeExplicitSerializationTest {

	@Entity( //
			name = LocalDateTimeExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDateTime", type = @Type(name = "java.time.LocalDateTime")) //
			} //
	)
	public static interface LocalDateTimeExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.LocalDateTimeExplicitSerialization";
	}

	private Path path;

	private LocalDateTimeExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("local-date-time-explicit-serialization.xml").toURI());

		model = new LocalDateTimeExplicitSerialization();

		model.setId("local-date-time-explicit-serialization");
		model.setValueLocalDateTime(LocalDateTime.of(2022, Month.AUGUST, 22, 14, 25, 40, 123456789));
	}

	@Test
	public void testWrite() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		String actual = XmlUtil.write(handler, model);
		Files.writeString(Paths.get("c://deleteme/local-date-time-serialization.xml"), actual);
		String expected = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n"));

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		LocalDateTimeExplicitSerialization actual = XmlUtil.read(handler, LocalDateTimeExplicitSerialization.class,
				Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
		LocalDateTimeExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueLocalDateTime(), actual.getValueLocalDateTime());
	}
}
