package com.ijioio.aes.sandbox.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.test.model.StringSerialization;

public class StringSerializationTest {

	@Entity( //
			name = StringSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = @Type(name = Type.STRING)) //
			} //
	)
	public static interface StringSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.StringSerialization";
	}

	private StringSerialization model;

	@BeforeEach
	public void before() {

		model = new StringSerialization();

		model.setId("string-serialization");
		model.setValueString("value");
	}

	@Test
	public void testWrite() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		String actual = XmlUtil.write(handler, model);
		String expected = new String(
				Files.readAllBytes(
						Paths.get(getClass().getClassLoader().getResource("string-serialization.xml").toURI())),
				StandardCharsets.UTF_8);

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		StringSerialization actual = XmlUtil.read(handler, StringSerialization.class,
				new String(
						Files.readAllBytes(
								Paths.get(getClass().getClassLoader().getResource("string-serialization.xml").toURI())),
						StandardCharsets.UTF_8));
		StringSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueString(), actual.getValueString());
	}
}
