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
import com.ijioio.test.model.LocalDateSerialization;

public class LocalDateSerializationTest {

	@Entity( //
			name = LocalDateSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDate", type = @Type(name = Type.LOCAL_DATE)) //
			} //
	)
	public static interface LocalDateSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.LocalDateSerialization";
	}

	private Path path;

	private LocalDateSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("local-date-serialization.xml").toURI());

		model = new LocalDateSerialization();

		model.setId("local-date-serialization");
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

		LocalDateSerialization actual = XmlUtil.read(handler, LocalDateSerialization.class,
				Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
		LocalDateSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueLocalDate(), actual.getValueLocalDate());
	}
}
