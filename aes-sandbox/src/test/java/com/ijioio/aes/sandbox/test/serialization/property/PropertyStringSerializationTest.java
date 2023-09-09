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
import com.ijioio.test.model.PropertyStringSerialization;

public class PropertyStringSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyStringSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING) //
			} //
	)
	public static interface PropertyStringSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringSerialization";
	}

	private Path path;

	private PropertyStringSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-string-serialization.xml").toURI());

		model = new PropertyStringSerialization();

		model.setId("property-string-serialization");
		model.setValueString("value");
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

		PropertyStringSerialization actual = XmlUtil.read(handler, PropertyStringSerialization.class, readString(path));
		PropertyStringSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueString(), actual.getValueString());
	}
}
