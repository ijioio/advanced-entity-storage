package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.core.XSerializable;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyXSerializableFinalSerialization;

public class PropertyXSerializableFinalSerializationTest extends BaseSerializationTest {

	public static class XSerializableObject implements XSerializable {

		private String valueString;

		public String getValueString() {
			return valueString;
		}

		public void setValueString(String valueString) {
			this.valueString = valueString;
		}

		@Override
		public void write(SerializationContext context, SerializationHandler handler) throws SerializationException {
			handler.write(context,
					Collections.singletonMap("valueString", () -> handler.write(context, "valueString", valueString)));
		}

		@Override
		public void read(SerializationContext context, SerializationHandler handler) throws SerializationException {
			handler.read(context,
					Collections.singletonMap("valueString", () -> valueString = handler.read(context, valueString)));
		}
	}

	@Entity( //
			name = PropertyXSerializableFinalSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueXSerializableObject", type = "com.ijioio.aes.sandbox.test.serialization.property.PropertyXSerializableFinalSerializationTest.XSerializableObject", attributes = Attribute.FINAL) //
			} //
	)
	public static interface PropertyXSerializableFinalSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyXSerializableFinalSerialization";
	}

	private Path path;

	private PropertyXSerializableFinalSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths
				.get(getClass().getClassLoader().getResource("property-xserializable-final-serialization.xml").toURI());

		model = new PropertyXSerializableFinalSerialization();

		model.setId("property-xserializable-final-serialization");
		model.getValueXSerializableObject().setValueString("value");
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

		PropertyXSerializableFinalSerialization actual = XmlUtil.read(handler,
				PropertyXSerializableFinalSerialization.class, readString(path));
		PropertyXSerializableFinalSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueXSerializableObject().getValueString(),
				actual.getValueXSerializableObject().getValueString());
	}
}
