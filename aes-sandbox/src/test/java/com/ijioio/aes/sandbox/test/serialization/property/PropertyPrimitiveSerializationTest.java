package com.ijioio.aes.sandbox.test.serialization.property;

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
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyPrimitiveSerialization;

public class PropertyPrimitiveSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyPrimitiveSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueBoolean", type = Type.BOOLEAN), //
					@EntityProperty(name = "valueChar", type = Type.CHAR), //
					@EntityProperty(name = "valueByte", type = Type.BYTE), //
					@EntityProperty(name = "valueShort", type = Type.SHORT), //
					@EntityProperty(name = "valueInt", type = Type.INT), //
					@EntityProperty(name = "valueLong", type = Type.LONG), //
					@EntityProperty(name = "valueFloat", type = Type.FLOAT), //
					@EntityProperty(name = "valueDouble", type = Type.DOUBLE) //
			} //
	)
	public static interface PropertyPrimitiveSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyPrimitiveSerialization";
	}

	private Path path;

	private PropertyPrimitiveSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-primitive-serialization.xml").toURI());

		model = new PropertyPrimitiveSerialization();

		model.setId("property-primitive-serialization");
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
		String expected = readString(path);

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		PropertyPrimitiveSerialization actual = XmlUtil.read(handler, PropertyPrimitiveSerialization.class,
				readString(path));
		PropertyPrimitiveSerialization expected = model;

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
