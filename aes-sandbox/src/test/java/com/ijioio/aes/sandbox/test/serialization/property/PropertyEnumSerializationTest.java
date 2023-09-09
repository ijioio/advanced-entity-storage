package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Month;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyEnumSerialization;

public class PropertyEnumSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyEnumSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueEnum", type = "java.time.Month") //
			} //
	)
	public static interface PropertyEnumSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEnumSerialization";
	}

	private Path path;

	private PropertyEnumSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-enum-serialization.xml").toURI());

		model = new PropertyEnumSerialization();

		model.setId("property-enum-serialization");
		model.setValueEnum(Month.JANUARY);
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

		PropertyEnumSerialization actual = XmlUtil.read(handler, PropertyEnumSerialization.class, readString(path));
		PropertyEnumSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueEnum(), actual.getValueEnum());
	}
}
