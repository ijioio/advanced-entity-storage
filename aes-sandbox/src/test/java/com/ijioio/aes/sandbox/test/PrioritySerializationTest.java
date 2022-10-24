package com.ijioio.aes.sandbox.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.XSerializable;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.SerializationReader;
import com.ijioio.aes.core.serialization.SerializationWriter;
import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.test.model.PrioritySerialization;

public class PrioritySerializationTest {

	public static class XSerializableList extends ArrayList<String> implements XSerializable {

		private static final long serialVersionUID = 829086422127798594L;

		@Override
		public Map<String, SerializationWriter> getWriters(SerializationContext context, SerializationHandler handler) {
			return Collections.emptyMap();
		}

		@Override
		public Map<String, SerializationReader> getReaders(SerializationContext context, SerializationHandler handler) {
			return Collections.emptyMap();
		}

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
		public Map<String, SerializationWriter> getWriters(SerializationContext context, SerializationHandler handler) {
			return Collections.emptyMap();
		}

		@Override
		public Map<String, SerializationReader> getReaders(SerializationContext context, SerializationHandler handler) {
			return Collections.emptyMap();
		}

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
		public Map<String, SerializationWriter> getWriters(SerializationContext context, SerializationHandler handler) {
			return Collections.emptyMap();
		}

		@Override
		public Map<String, SerializationReader> getReaders(SerializationContext context, SerializationHandler handler) {
			return Collections.emptyMap();
		}

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
			name = PrioritySerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueXSerializableList", type = @Type(name = "com.ijioio.aes.sandbox.test.PrioritySerializationTest.XSerializableList")), //
					@EntityProperty(name = "valueXSerializableSet", type = @Type(name = "com.ijioio.aes.sandbox.test.PrioritySerializationTest.XSerializableSet")), //
					@EntityProperty(name = "valueXSerializableMap", type = @Type(name = "com.ijioio.aes.sandbox.test.PrioritySerializationTest.XSerializableMap")) //
			} //
	)
	public static interface PrioritySerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PrioritySerialization";
	}

	private Path path;

	private PrioritySerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("priority-serialization.xml").toURI());

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

		model = new PrioritySerialization();

		model.setId("priority-serialization");
		model.setValueXSerializableList(xSerializableList);
		model.setValueXSerializableSet(xSerializableSet);
		model.setValueXSerializableMap(xSerializableMap);
	}

	@Test
	public void testWrite() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		String actual = XmlUtil.write(handler, model);
		String expected = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n"));

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		PrioritySerialization actual = XmlUtil.read(handler, PrioritySerialization.class,
				Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
		PrioritySerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueXSerializableList(), actual.getValueXSerializableList());
		Assertions.assertEquals(expected.getValueXSerializableSet(), actual.getValueXSerializableSet());
		Assertions.assertEquals(expected.getValueXSerializableMap(), actual.getValueXSerializableMap());
	}
}
