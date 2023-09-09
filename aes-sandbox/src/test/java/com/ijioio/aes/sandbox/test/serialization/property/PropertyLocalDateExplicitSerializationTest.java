package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyLocalDateExplicitSerialization;

public class PropertyLocalDateExplicitSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyLocalDateExplicitSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDate", type = "java.time.LocalDate") //
			} //
	)
	public static interface PropertyLocalDateExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLocalDateExplicitSerialization";
	}

	private Path path;

	private PropertyLocalDateExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths
				.get(getClass().getClassLoader().getResource("property-local-date-explicit-serialization.xml").toURI());

		model = new PropertyLocalDateExplicitSerialization();

		model.setId("property-local-date-explicit-serialization");
		model.setValueLocalDate(LocalDate.of(2022, Month.AUGUST, 22));
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

		PropertyLocalDateExplicitSerialization actual = XmlUtil.read(handler,
				PropertyLocalDateExplicitSerialization.class, readString(path));
		PropertyLocalDateExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueLocalDate(), actual.getValueLocalDate());
	}
}
