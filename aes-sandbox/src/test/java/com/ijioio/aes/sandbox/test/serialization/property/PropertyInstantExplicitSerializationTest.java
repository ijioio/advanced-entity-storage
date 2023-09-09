package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyInstantExplicitSerialization;

public class PropertyInstantExplicitSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyInstantExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueInstant", type = "java.time.Instant") //
			} //
	)
	public static interface PropertyInstantExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyInstantExplicitSerialization";
	}

	private Path path;

	private PropertyInstantExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths
				.get(getClass().getClassLoader().getResource("property-instant-explicit-serialization.xml").toURI());

		model = new PropertyInstantExplicitSerialization();

		model.setId("property-instant-explicit-serialization");
		model.setValueInstant(
				LocalDateTime.of(2022, Month.AUGUST, 22, 14, 25, 40, 123456789).atZone(ZoneOffset.UTC).toInstant());
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

		PropertyInstantExplicitSerialization actual = XmlUtil.read(handler, PropertyInstantExplicitSerialization.class,
				readString(path));
		PropertyInstantExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueInstant(), actual.getValueInstant());
	}
}
