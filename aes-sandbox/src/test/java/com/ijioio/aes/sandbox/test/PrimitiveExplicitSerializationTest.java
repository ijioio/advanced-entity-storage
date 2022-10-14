package com.ijioio.aes.sandbox.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.test.model.PrimitiveExplicitSerialization;

public class PrimitiveExplicitSerializationTest {

	@Entity( //
			name = PrimitiveExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueBoolean", type = @Type(name = "boolean")), //
					@EntityProperty(name = "valueChar", type = @Type(name = "char")), //
					@EntityProperty(name = "valueByte", type = @Type(name = "byte")), //
					@EntityProperty(name = "valueShort", type = @Type(name = "short")), //
					@EntityProperty(name = "valueInt", type = @Type(name = "int")), //
					@EntityProperty(name = "valueLong", type = @Type(name = "long")), //
					@EntityProperty(name = "valueFloat", type = @Type(name = "float")), //
					@EntityProperty(name = "valueDouble", type = @Type(name = "double")) //
			} //
	)
	public static interface PrimitiveExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PrimitiveExplicitSerialization";
	}

	private Path path;

	private PrimitiveExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("primitive-explicit-serialization.xml").toURI());

		model = new PrimitiveExplicitSerialization();

		model.setId("primitive-explicit-serialization");
		model.setValueBoolean(true);
		model.setValueByte((byte) 10);
		model.setValueShort((short) 20);
		model.setValueInt(30);
		model.setValueLong(40);
		model.setValueChar('a');
		model.setValueFloat((float) 50.0);
		model.setValueDouble(60.0);
	}

	@Test
	public void testWrite() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		String actual = XmlUtil.write(handler, model);
		String expected = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		PrimitiveExplicitSerialization actual = XmlUtil.read(handler, PrimitiveExplicitSerialization.class,
				new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
		PrimitiveExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.isValueBoolean(), actual.isValueBoolean());
		Assertions.assertEquals(expected.getValueChar(), actual.getValueChar());
		Assertions.assertEquals(expected.getValueByte(), actual.getValueByte());
		Assertions.assertEquals(expected.getValueShort(), actual.getValueShort());
		Assertions.assertEquals(expected.getValueInt(), actual.getValueInt());
		Assertions.assertEquals(expected.getValueLong(), actual.getValueLong());
		Assertions.assertEquals(expected.getValueFloat(), actual.getValueFloat());
		Assertions.assertEquals(expected.getValueDouble(), actual.getValueDouble());
	}
}
