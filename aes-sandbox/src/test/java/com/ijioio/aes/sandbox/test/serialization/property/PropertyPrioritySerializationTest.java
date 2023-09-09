package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.core.XSerializable;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyPrioritySerialization;

public class PropertyPrioritySerializationTest extends BaseSerializationTest {

	public static class XSerializableList extends ArrayList<String> implements XSerializable {

		private static final long serialVersionUID = 829086422127798594L;

		@Override
		public void write(SerializationContext context, SerializationHandler handler) throws SerializationException {

			XMLStreamWriter writer = ((XmlSerializationContext) context).getWriter();

			try {

				for (String value : this) {

					writer.writeStartElement("customItem");
					writer.writeCharacters(value);
					writer.writeEndElement();
				}

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public void read(SerializationContext context, SerializationHandler handler) throws SerializationException {

			XMLStreamReader reader = ((XmlSerializationContext) context).getReader();

			try {

				this.clear();

				while (reader.nextTag() != XMLStreamConstants.END_ELEMENT) {

					if (reader.getName().getLocalPart().equals("customItem")) {
						this.add(reader.getElementText());
					}
				}

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}
	}

	public static class XSerializableSet extends LinkedHashSet<String> implements XSerializable {

		private static final long serialVersionUID = 4675527390057694654L;

		@Override
		public void write(SerializationContext context, SerializationHandler handler) throws SerializationException {

			XMLStreamWriter writer = ((XmlSerializationContext) context).getWriter();

			try {

				for (String value : this) {

					writer.writeStartElement("customItem");
					writer.writeCharacters(value);
					writer.writeEndElement();
				}

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public void read(SerializationContext context, SerializationHandler handler) throws SerializationException {

			XMLStreamReader reader = ((XmlSerializationContext) context).getReader();

			try {

				this.clear();

				while (reader.nextTag() != XMLStreamConstants.END_ELEMENT) {

					if (reader.getName().getLocalPart().equals("customItem")) {
						this.add(reader.getElementText());
					}
				}

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}
	}

	public static class XSerializableMap extends HashMap<String, String> implements XSerializable {

		private static final long serialVersionUID = 1104595344886510054L;

		@Override
		public void write(SerializationContext context, SerializationHandler handler) throws SerializationException {

			XMLStreamWriter writer = ((XmlSerializationContext) context).getWriter();

			try {

				for (Entry<String, String> entry : this.entrySet()) {

					writer.writeStartElement("customEntry");
					writer.writeStartElement("customKey");
					writer.writeCharacters(entry.getKey());
					writer.writeEndElement();
					writer.writeStartElement("customValue");
					writer.writeCharacters(entry.getValue());
					writer.writeEndElement();
					writer.writeEndElement();
				}

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public void read(SerializationContext context, SerializationHandler handler) throws SerializationException {

			XMLStreamReader reader = ((XmlSerializationContext) context).getReader();

			try {

				this.clear();

				while (reader.nextTag() != XMLStreamConstants.END_ELEMENT) {

					if (reader.getName().getLocalPart().equals("customEntry")) {

						String key = null;
						String value = null;

						while (reader.nextTag() != XMLStreamConstants.END_ELEMENT) {

							if (reader.getName().getLocalPart().equals("customKey")) {
								key = reader.getElementText();
							} else if (reader.getName().getLocalPart().equals("customValue")) {
								value = reader.getElementText();
							}
						}

						this.put(key, value);
					}
				}

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}
	}

	@Entity( //
			name = PropertyPrioritySerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueXSerializableList", type = "com.ijioio.aes.sandbox.test.serialization.property.PropertyPrioritySerializationTest.XSerializableList"), //
					@EntityProperty(name = "valueXSerializableSet", type = "com.ijioio.aes.sandbox.test.serialization.property.PropertyPrioritySerializationTest.XSerializableSet"), //
					@EntityProperty(name = "valueXSerializableMap", type = "com.ijioio.aes.sandbox.test.serialization.property.PropertyPrioritySerializationTest.XSerializableMap") //
			} //
	)
	public static interface PropertyPrioritySerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyPrioritySerialization";
	}

	private Path path;

	private PropertyPrioritySerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-priority-serialization.xml").toURI());

		XSerializableList xSerializableList = new XSerializableList();

		xSerializableList.add("value1");
		xSerializableList.add("value2");
		xSerializableList.add("value3");

		XSerializableSet xSerializableSet = new XSerializableSet();

		xSerializableSet.add("value1");
		xSerializableSet.add("value2");
		xSerializableSet.add("value3");

		XSerializableMap xSerializableMap = new XSerializableMap();

		xSerializableMap.put("key1", "value1");
		xSerializableMap.put("key2", "value2");
		xSerializableMap.put("key3", "value3");

		model = new PropertyPrioritySerialization();

		model.setId("property-priority-serialization");
		model.setValueXSerializableList(xSerializableList);
		model.setValueXSerializableSet(xSerializableSet);
		model.setValueXSerializableMap(xSerializableMap);
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

		PropertyPrioritySerialization actual = XmlUtil.read(handler, PropertyPrioritySerialization.class,
				readString(path));
		PropertyPrioritySerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueXSerializableList(), actual.getValueXSerializableList());
		Assertions.assertEquals(expected.getValueXSerializableSet(), actual.getValueXSerializableSet());
		Assertions.assertEquals(expected.getValueXSerializableMap(), actual.getValueXSerializableMap());
	}
}
