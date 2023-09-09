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
import com.ijioio.test.model.PropertyStringExplicitSerialization;

public class PropertyStringExplicitSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyStringExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = "java.lang.String") //
			} //
	)
	public static interface PropertyStringExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringExplicitSerialization";
	}

	private Path path;

	private PropertyStringExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-string-explicit-serialization.xml").toURI());

		model = new PropertyStringExplicitSerialization();

		model.setId("property-string-explicit-serialization");
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

		PropertyStringExplicitSerialization actual = XmlUtil.read(handler, PropertyStringExplicitSerialization.class,
				readString(path));
		PropertyStringExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueString(), actual.getValueString());
	}
}
