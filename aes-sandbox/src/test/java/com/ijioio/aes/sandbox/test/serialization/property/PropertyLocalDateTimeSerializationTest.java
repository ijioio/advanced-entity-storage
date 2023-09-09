package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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
import com.ijioio.test.model.PropertyLocalDateTimeSerialization;

public class PropertyLocalDateTimeSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertyLocalDateTimeSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDateTime", type = Type.LOCAL_DATE_TIME) //
			} //
	)
	public static interface PropertyLocalDateTimeSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLocalDateTimeSerialization";
	}

	private Path path;

	private PropertyLocalDateTimeSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-local-date-time-serialization.xml").toURI());

		model = new PropertyLocalDateTimeSerialization();

		model.setId("property-local-date-time-serialization");
		model.setValueLocalDateTime(LocalDateTime.of(2022, Month.AUGUST, 22, 14, 25, 40, 123456789));
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

		PropertyLocalDateTimeSerialization actual = XmlUtil.read(handler, PropertyLocalDateTimeSerialization.class,
				readString(path));
		PropertyLocalDateTimeSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueLocalDateTime(), actual.getValueLocalDateTime());
	}
}
