package com.ijioio.aes.sandbox.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Month;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.test.model.EnumSerialization;

public class EnumSerializationTest {

	@Entity( //
			name = EnumSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueEnum", type = @Type(name = "java.time.Month")) //
			} //
	)
	public static interface EnumSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.EnumSerialization";
	}

	private EnumSerialization model;

	@BeforeEach
	public void before() {

		model = new EnumSerialization();

		model.setId("enum-serialization");
		model.setValueEnum(Month.JANUARY);
	}

	@Test
	public void testWrite() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		String actual = XmlUtil.write(handler, model);
		String expected = new String(
				Files.readAllBytes(
						Paths.get(getClass().getClassLoader().getResource("enum-serialization.xml").toURI())),
				StandardCharsets.UTF_8);

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		EnumSerialization actual = XmlUtil.read(handler, EnumSerialization.class,
				new String(
						Files.readAllBytes(
								Paths.get(getClass().getClassLoader().getResource("enum-serialization.xml").toURI())),
						StandardCharsets.UTF_8));
		EnumSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueEnum(), actual.getValueEnum());
	}
}
