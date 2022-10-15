package com.ijioio.aes.sandbox.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.test.model.InstantExplicitSerialization;

public class InstantExplicitSerializationTest {

	@Entity( //
			name = InstantExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueInstant", type = @Type(name = "java.time.Instant")) //
			} //
	)
	public static interface InstantExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.InstantExplicitSerialization";
	}

	private Path path;

	private InstantExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("instant-explicit-serialization.xml").toURI());

		model = new InstantExplicitSerialization();

		model.setId("instant-explicit-serialization");
		model.setValueInstant(
				LocalDateTime.of(2022, Month.AUGUST, 22, 14, 25, 40, 123456789).atZone(ZoneOffset.UTC).toInstant());
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

		InstantExplicitSerialization actual = XmlUtil.read(handler, InstantExplicitSerialization.class,
				Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
		InstantExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueInstant(), actual.getValueInstant());
	}
}
