package com.ijioio.aes.sandbox.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
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
import com.ijioio.test.model.LocalDateExplicitSerialization;

public class LocalDateExplicitSerializationTest {

	@Entity( //
			name = LocalDateExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDate", type = @Type(name = "java.time.LocalDate")) //
			} //
	)
	public static interface LocalDateExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.LocalDateExplicitSerialization";
	}

	private Path path;

	private LocalDateExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("local-date-explicit-serialization.xml").toURI());

		model = new LocalDateExplicitSerialization();

		model.setId("local-date-explicit-serialization");
		model.setValueLocalDate(LocalDate.of(2022, Month.AUGUST, 22));
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

		LocalDateExplicitSerialization actual = XmlUtil.read(handler, LocalDateExplicitSerialization.class,
				Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
		LocalDateExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueLocalDate(), actual.getValueLocalDate());
	}
}
