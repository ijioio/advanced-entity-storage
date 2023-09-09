package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyPrimitiveExplicitSerialization;

public class PropertyPrimitiveExplicitSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyPrimitiveExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueBoolean", type = "boolean"), //
					@EntityProperty(name = "valueChar", type = "char"), //
					@EntityProperty(name = "valueByte", type = "byte"), //
					@EntityProperty(name = "valueShort", type = "short"), //
					@EntityProperty(name = "valueInt", type = "int"), //
					@EntityProperty(name = "valueLong", type = "long"), //
					@EntityProperty(name = "valueFloat", type = "float"), //
					@EntityProperty(name = "valueDouble", type = "double") //
			} //
	)
	public static interface PropertyPrimitiveExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyPrimitiveExplicitSerialization";
	}

	private Path path;

	private PropertyPrimitiveExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths
				.get(getClass().getClassLoader().getResource("property-primitive-explicit-serialization.xml").toURI());

		model = new PropertyPrimitiveExplicitSerialization();

		model.setId("property-primitive-explicit-serialization");
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

		PropertyPrimitiveExplicitSerialization actual = XmlUtil.read(handler,
				PropertyPrimitiveExplicitSerialization.class, readString(path));
		PropertyPrimitiveExplicitSerialization expected = model;

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
