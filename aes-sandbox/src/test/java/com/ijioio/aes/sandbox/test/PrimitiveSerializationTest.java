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
import com.ijioio.test.model.PrimitiveSerialization;

public class PrimitiveSerializationTest {

	@Entity( //
			name = PrimitiveSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueBoolean", type = @Type(name = Type.BOOLEAN)), //
					@EntityProperty(name = "valueChar", type = @Type(name = Type.CHAR)), //
					@EntityProperty(name = "valueByte", type = @Type(name = Type.BYTE)), //
					@EntityProperty(name = "valueShort", type = @Type(name = Type.SHORT)), // s
					@EntityProperty(name = "valueInt", type = @Type(name = Type.INT)), //
					@EntityProperty(name = "valueLong", type = @Type(name = Type.LONG)), //
					@EntityProperty(name = "valueFloat", type = @Type(name = Type.FLOAT)), //
					@EntityProperty(name = "valueDouble", type = @Type(name = Type.DOUBLE)) //
			} //
	)
	public static interface PrimitiveSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PrimitiveSerialization";
	}

	private Path path;

	private PrimitiveSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("primitive-serialization.xml").toURI());

		model = new PrimitiveSerialization();

		model.setId("primitive-serialization");
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

		PrimitiveSerialization actual = XmlUtil.read(handler, PrimitiveSerialization.class,
				new String(Files.readAllBytes(path), StandardCharsets.UTF_8));
		PrimitiveSerialization expected = model;

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
