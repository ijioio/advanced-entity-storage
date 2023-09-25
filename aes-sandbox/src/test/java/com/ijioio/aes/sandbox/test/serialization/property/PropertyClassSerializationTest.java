package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyClassSerialization;

public class PropertyClassSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyClassSerializationPrototype.NAME, //
			types = { //
					@Type(name = "Class<String>", type = Type.CLASS, parameters = @Parameter(name = Type.STRING)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueClass", type = "Class<String>") //
			} //
	)
	public static interface PropertyClassSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassSerialization";
	}

	private Path path;

	private PropertyClassSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-class-serialization.xml").toURI());

		model = new PropertyClassSerialization();

		model.setId("property-class-serialization");
		model.setValueClass(String.class);
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

		PropertyClassSerialization actual = XmlUtil.read(handler, PropertyClassSerialization.class, readString(path));
		PropertyClassSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueClass(), actual.getValueClass());
	}
}
