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
import com.ijioio.test.model.PropertyByteArraySerialization;

public class PropertyByteArraySerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyByteArraySerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByteArray", type = @Type(name = Type.BYTE_ARRAY)) //
			} //
	)
	public static interface PropertyByteArraySerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteArraySerialization";
	}

	private Path path;

	private PropertyByteArraySerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-byte-array-serialization.xml").toURI());

		model = new PropertyByteArraySerialization();

		model.setId("property-byte-array-serialization");
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

		PropertyByteArraySerialization actual = XmlUtil.read(handler, PropertyByteArraySerialization.class,
				readString(path));
		PropertyByteArraySerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertArrayEquals(expected.getValueByteArray(), actual.getValueByteArray());
	}
}
