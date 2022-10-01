package com.ijioio.aes.sandbox.test;

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
		String expected = Files
				.readString(Paths.get(getClass().getClassLoader().getResource("enum-serialization.xml").toURI()));

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		EnumSerialization actual = XmlUtil.read(handler, EnumSerialization.class,
				Files.readString(Paths.get(getClass().getClassLoader().getResource("enum-serialization.xml").toURI())));
		EnumSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueEnum(), actual.getValueEnum());
	}
}
