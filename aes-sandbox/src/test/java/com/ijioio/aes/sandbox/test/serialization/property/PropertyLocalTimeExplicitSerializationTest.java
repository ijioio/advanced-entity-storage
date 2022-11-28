package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyLocalTimeExplicitSerialization;

public class PropertyLocalTimeExplicitSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyLocalTimeExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalTime", type = @Type(name = "java.time.LocalTime")) //
			} //
	)
	public static interface PropertyLocalTimeExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLocalTimeExplicitSerialization";
	}

	private Path path;

	private PropertyLocalTimeExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths
				.get(getClass().getClassLoader().getResource("property-local-time-explicit-serialization.xml").toURI());

		model = new PropertyLocalTimeExplicitSerialization();

		model.setId("property-local-time-explicit-serialization");
		model.setValueLocalTime(LocalTime.of(14, 25, 40, 123456789));
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

		PropertyLocalTimeExplicitSerialization actual = XmlUtil.read(handler,
				PropertyLocalTimeExplicitSerialization.class, readString(path));
		PropertyLocalTimeExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueLocalTime(), actual.getValueLocalTime());
	}
}
