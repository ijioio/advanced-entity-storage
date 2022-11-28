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
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyInstantSerialization;

public class PropertyInstantSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyInstantSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueInstant", type = @Type(name = Type.INSTANT)) //
			} //
	)
	public static interface PropertyInstantSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyInstantSerialization";
	}

	private Path path;

	private PropertyInstantSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-instant-serialization.xml").toURI());

		model = new PropertyInstantSerialization();

		model.setId("property-instant-serialization");
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

		PropertyInstantSerialization actual = XmlUtil.read(handler, PropertyInstantSerialization.class,
				readString(path));
		PropertyInstantSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueInstant(), actual.getValueInstant());
	}
}
