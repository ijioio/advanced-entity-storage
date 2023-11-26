package com.ijioio.aes.core.serialization.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.Introspectable;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlBooleanSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlByteArraySerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlByteSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlCharacterSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlClassSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlCollectionSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlDoubleSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlEnumSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlFloatSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlInstantSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlIntegerSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlIntrospectableSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLocalDateSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLocalDateTimeSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLocalTimeSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLongSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlMapSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlShortSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlStringSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlVoidSerializationValueHandler;

public class XmlSerializationHandler implements SerializationHandler {

	private final Map<String, XmlSerializationValueHandler<?>> handlers = new HashMap<>();

	public XmlSerializationHandler() {

		registerValueHandler(new XmlBooleanSerializationValueHandler());
		registerValueHandler(new XmlCharacterSerializationValueHandler());
		registerValueHandler(new XmlByteSerializationValueHandler());
		registerValueHandler(new XmlShortSerializationValueHandler());
		registerValueHandler(new XmlIntegerSerializationValueHandler());
		registerValueHandler(new XmlLongSerializationValueHandler());
		registerValueHandler(new XmlFloatSerializationValueHandler());
		registerValueHandler(new XmlDoubleSerializationValueHandler());
		registerValueHandler(new XmlByteArraySerializationValueHandler());
		registerValueHandler(new XmlStringSerializationValueHandler());
		registerValueHandler(new XmlInstantSerializationValueHandler());
		registerValueHandler(new XmlLocalDateSerializationValueHandler());
		registerValueHandler(new XmlLocalTimeSerializationValueHandler());
		registerValueHandler(new XmlLocalDateTimeSerializationValueHandler());
		registerValueHandler(new XmlEnumSerializationValueHandler());
		registerValueHandler(new XmlClassSerializationValueHandler());
		registerValueHandler(new XmlCollectionSerializationValueHandler());
		registerValueHandler(new XmlMapSerializationValueHandler());
		registerValueHandler(new XmlIntrospectableSerializationValueHandler());
		registerValueHandler(new XmlVoidSerializationValueHandler());
	}

	public <T> void registerValueHandler(XmlSerializationValueHandler<T> handler) {
		handlers.put(handler.getType().getName(), handler);
	}

	@SuppressWarnings("unchecked")
	public <T> XmlSerializationValueHandler<T> getValueHandler(Class<T> type) throws SerializationException {

		XmlSerializationValueHandler<T> handler = (XmlSerializationValueHandler<T>) handlers.get(type.getName());

		if (handler == null) {

			if (Introspectable.class.isAssignableFrom(type)) {
				handler = (XmlSerializationValueHandler<T>) handlers.get(Introspectable.class.getName());
			} else if (Enum.class.isAssignableFrom(type)) {
				handler = (XmlSerializationValueHandler<T>) handlers.get(Enum.class.getName());
			} else if (Collection.class.isAssignableFrom(type)) {
				handler = (XmlSerializationValueHandler<T>) handlers.get(Collection.class.getName());
			} else if (Map.class.isAssignableFrom(type)) {
				handler = (XmlSerializationValueHandler<T>) handlers.get(Map.class.getName());
			}
		}

		if (handler != null) {
			return handler;
		}

		throw new SerializationException(String.format("type %s is not supported", type));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <E extends Entity> void write(E entity, OutputStream os) throws SerializationException {

		try {

			XMLPrettyPrintStreamWriter writer = XMLPrettyPrintStreamWriter
					.of(XMLOutputFactory.newInstance().createXMLStreamWriter(os));

			XmlSerializationContext context = new XmlSerializationContext(writer);

			try {
				((XmlSerializationValueHandler) getValueHandler(entity.getClass())).write(context, this, "object",
						entity, true);
			} finally {
				writer.close();
			}

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public <E extends Entity> E read(InputStream is) throws SerializationException {

		try {

			XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(is);

			XmlSerializationContext context = new XmlSerializationContext(reader);

			try {

				while (reader.nextTag() != XMLEvent.END_ELEMENT) {

					System.out.println("read -> " + reader.getLocalName());

					if (reader.getName().getLocalPart().equals("object")) {

						String entityTypeName = reader.getAttributeValue(null, "class");

						if (entityTypeName == null) {
							throw new SerializationException("entity class is missing");
						}

						Class<?> entityType = Class.forName(entityTypeName);

						if (!Entity.class.isAssignableFrom(entityType)) {
							throw new SerializationException("entity class is not of entity type");
						}

						return (E) ((XmlSerializationValueHandler) getValueHandler(entityType)).read(context, this,
								entityType, null);
					}
				}

			} finally {
				reader.close();
			}

			return null;

		} catch (ClassNotFoundException | XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	private static class XMLPrettyPrintStreamWriter implements XMLStreamWriter {

		public static XMLPrettyPrintStreamWriter of(XMLStreamWriter writer) {
			return new XMLPrettyPrintStreamWriter(writer);
		}

		private final XMLStreamWriter writer;

		private int level = 0;

		private boolean lastWasStart = false;

		private XMLPrettyPrintStreamWriter(XMLStreamWriter writer) {
			this.writer = writer;
		}

		private void indent() throws XMLStreamException {
			for (int i = 0; i < level; i++) {
				writer.writeCharacters("    ");
			}
		}

		@Override
		public void writeStartElement(final String localName) throws XMLStreamException {
			if (level > 0) {
				writer.writeCharacters("\n");
			}
			lastWasStart = true;
			indent();
			level++;
			writer.writeStartElement(localName);
		}

		@Override
		public void writeStartElement(final String namespaceURI, final String localName) throws XMLStreamException {
			writer.writeCharacters("\n");
			lastWasStart = true;
			indent();
			level++;
			writer.writeStartElement(namespaceURI, localName);
		}

		@Override
		public void writeStartElement(final String prefix, final String localName, final String namespaceURI)
				throws XMLStreamException {
			writer.writeCharacters("\n");
			lastWasStart = true;
			level++;
			writer.writeStartElement(prefix, localName, namespaceURI);
		}

		@Override
		public void writeEmptyElement(final String namespaceURI, final String localName) throws XMLStreamException {
			writer.writeCharacters("\n");
			lastWasStart = false;
			indent();
			writer.writeEmptyElement(namespaceURI, localName);
		}

		@Override
		public void writeEmptyElement(final String prefix, final String localName, final String namespaceURI)
				throws XMLStreamException {
			writer.writeCharacters("\n");
			lastWasStart = false;
			indent();
			writer.writeEmptyElement(prefix, localName, namespaceURI);
		}

		@Override
		public void writeEmptyElement(final String localName) throws XMLStreamException {
			writer.writeCharacters("\n");
			lastWasStart = false;
			indent();
			writer.writeEmptyElement(localName);
		}

		@Override
		public void writeEndElement() throws XMLStreamException {
			level--;
			if (!lastWasStart) {
				writer.writeCharacters("\n");
				indent();
			} else {
				lastWasStart = false;
			}
			writer.writeEndElement();
		}

		@Override
		public void writeEndDocument() throws XMLStreamException {
			if (!lastWasStart) {
				writer.writeCharacters("\n");
				indent();
			} else {
				lastWasStart = false;
			}
			writer.writeEndDocument();
		}

		@Override
		public void close() throws XMLStreamException {
			level = 0;
			lastWasStart = false;
			writer.close();
		}

		@Override
		public void flush() throws XMLStreamException {
			writer.flush();
		}

		@Override
		public void writeAttribute(final String localName, final String value) throws XMLStreamException {
			writer.writeAttribute(localName, value);
		}

		@Override
		public void writeAttribute(final String prefix, final String namespaceURI, final String localName,
				final String value) throws XMLStreamException {
			writer.writeAttribute(prefix, namespaceURI, localName, value);
		}

		@Override
		public void writeAttribute(final String namespaceURI, final String localName, final String value)
				throws XMLStreamException {
			writer.writeAttribute(namespaceURI, localName, value);
		}

		@Override
		public void writeNamespace(final String prefix, final String namespaceURI) throws XMLStreamException {
			writer.writeNamespace(prefix, namespaceURI);
		}

		@Override
		public void writeDefaultNamespace(final String namespaceURI) throws XMLStreamException {
			writer.writeDefaultNamespace(namespaceURI);
		}

		@Override
		public void writeComment(final String data) throws XMLStreamException {
			writer.writeCharacters("\n");
			lastWasStart = false;
			indent();
			writer.writeComment(data);
		}

		@Override
		public void writeProcessingInstruction(final String target) throws XMLStreamException {
			writer.writeProcessingInstruction(target);
		}

		@Override
		public void writeProcessingInstruction(final String target, final String data) throws XMLStreamException {
			writer.writeProcessingInstruction(target, data);
		}

		@Override
		public void writeCData(final String data) throws XMLStreamException {
			if (data == null) {
				writer.writeCData(data);
			} else {
				String[] parts = data.split("]]>");
				if (parts.length == 1) {
					writer.writeCData(data);
				} else {
					for (int i = 0; i < parts.length; i++) {
						StringBuilder sb = new StringBuilder();
						if (i > 0) {
							sb.append(">");
						}
						sb.append(parts[i]);
						if ((i + 1) < parts.length) {
							sb.append("]]");
						}
						writer.writeCData(sb.toString());
					}
				}
			}
		}

		@Override
		public void writeDTD(final String dtd) throws XMLStreamException {
			writer.writeDTD(dtd);
		}

		@Override
		public void writeEntityRef(final String name) throws XMLStreamException {
			writer.writeEntityRef(name);
		}

		@Override
		public void writeStartDocument() throws XMLStreamException {
			writer.writeStartDocument();
			writer.writeCharacters("\n");
		}

		@Override
		public void writeStartDocument(final String version) throws XMLStreamException {
			writer.writeStartDocument(version);
			writer.writeCharacters("\n");
		}

		@Override
		public void writeStartDocument(final String encoding, final String version) throws XMLStreamException {
			writer.writeStartDocument(encoding, version);
			writer.writeCharacters("\n");
		}

		@Override
		public void writeCharacters(final String text) throws XMLStreamException {
			writer.writeCharacters(text);
		}

		@Override
		public void writeCharacters(final char[] text, final int start, final int len) throws XMLStreamException {
			writer.writeCharacters(text, start, len);
		}

		@Override
		public String getPrefix(final String uri) throws XMLStreamException {
			return writer.getPrefix(uri);
		}

		@Override
		public void setPrefix(final String prefix, final String uri) throws XMLStreamException {
			writer.setPrefix(prefix, uri);
		}

		@Override
		public void setDefaultNamespace(final String uri) throws XMLStreamException {
			writer.setDefaultNamespace(uri);
		}

		@Override
		public void setNamespaceContext(final NamespaceContext context) throws XMLStreamException {
			writer.setNamespaceContext(context);
		}

		@Override
		public NamespaceContext getNamespaceContext() {
			return writer.getNamespaceContext();
		}

		@Override
		public Object getProperty(final String name) throws IllegalArgumentException {
			return writer.getProperty(name);
		}
	}
}
