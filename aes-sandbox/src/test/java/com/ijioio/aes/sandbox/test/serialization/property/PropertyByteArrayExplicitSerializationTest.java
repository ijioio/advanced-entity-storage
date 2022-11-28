package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.charset.StandardCharsets;
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
import com.ijioio.test.model.PropertyByteArrayExplicitSerialization;

public class PropertyByteArrayExplicitSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyByteArrayExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByteArray", type = @Type(name = "[B")) //
			} //
	)
	public static interface PropertyByteArrayExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteArrayExplicitSerialization";
	}

	private Path path;

	private PropertyByteArrayExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths
				.get(getClass().getClassLoader().getResource("property-byte-array-explicit-serialization.xml").toURI());

		model = new PropertyByteArrayExplicitSerialization();

		model.setId("property-byte-array-explicit-serialization");
		model.setValueByteArray("value".getBytes(StandardCharsets.UTF_8));
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

		PropertyByteArrayExplicitSerialization actual = XmlUtil.read(handler,
				PropertyByteArrayExplicitSerialization.class, readString(path));
		PropertyByteArrayExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertArrayEquals(expected.getValueByteArray(), actual.getValueByteArray());
	}
}
