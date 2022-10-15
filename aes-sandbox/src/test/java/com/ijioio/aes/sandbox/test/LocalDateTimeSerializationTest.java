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
import com.ijioio.test.model.LocalDateTimeSerialization;

public class LocalDateTimeSerializationTest {

	@Entity( //
			name = LocalDateTimeSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDateTime", type = @Type(name = Type.LOCAL_DATE_TIME)) //
			} //
	)
	public static interface LocalDateTimeSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.LocalDateTimeSerialization";
	}

	private Path path;

	private LocalDateTimeSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("local-date-time-serialization.xml").toURI());

		model = new LocalDateTimeSerialization();

		model.setId("local-date-time-serialization");
		model.setValueLocalDateTime(LocalDateTime.of(2022, Month.AUGUST, 22, 14, 25, 40, 123456789));
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

		LocalDateTimeSerialization actual = XmlUtil.read(handler, LocalDateTimeSerialization.class,
				Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
		LocalDateTimeSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueLocalDateTime(), actual.getValueLocalDateTime());
	}
}
