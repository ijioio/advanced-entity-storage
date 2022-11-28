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
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyLocalDateSerialization;

public class PropertyLocalDateSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyLocalDateSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDate", type = @Type(name = Type.LOCAL_DATE)) //
			} //
	)
	public static interface PropertyLocalDateSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLocalDateSerialization";
	}

	private Path path;

	private PropertyLocalDateSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-local-date-serialization.xml").toURI());

		model = new PropertyLocalDateSerialization();

		model.setId("property-local-date-serialization");
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

		PropertyLocalDateSerialization actual = XmlUtil.read(handler, PropertyLocalDateSerialization.class,
				readString(path));
		PropertyLocalDateSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueLocalDate(), actual.getValueLocalDate());
	}
}
