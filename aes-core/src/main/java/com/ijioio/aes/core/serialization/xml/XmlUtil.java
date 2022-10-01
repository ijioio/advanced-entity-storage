package com.ijioio.aes.core.serialization.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import com.ijioio.aes.core.XSerializable;

public class XmlUtil {

	public static String write(XmlSerializationHandler handler, final XSerializable object) throws Exception {

		try (StringWriter stringWriter = new StringWriter()) {

			XMLPrettyPrintStreamWriter writer = XMLPrettyPrintStreamWriter
					.of(XMLOutputFactory.newInstance().createXMLStreamWriter(stringWriter));

			try {

				XmlSerializationContext context = XmlSerializationContext.of(writer);

				writer.writeStartElement("object");
				writer.writeAttribute("class", object.getClass().getName());
				object.write(context, handler);
				writer.writeEndElement();

			} finally {
				writer.close();
			}

			stringWriter.flush();

			return stringWriter.toString();
		}
	}

	public static void write(XmlSerializationHandler handler, final XSerializable object, final OutputStream os)
			throws Exception {

		XMLStreamWriter writer = XMLPrettyPrintStreamWriter
				.of(XMLOutputFactory.newInstance().createXMLStreamWriter(os));

		try {

			XmlSerializationContext context = XmlSerializationContext.of(writer);

			writer.writeStartElement("object");
			writer.writeAttribute("class", object.getClass().getName());
			object.write(context, handler);
			writer.writeEndElement();

		} finally {
			writer.close();
		}
	}

	public static <T extends XSerializable> T read(XmlSerializationHandler handler, final Class<T> type,
			final String content) throws Exception {

		T object = type.newInstance();

		try (StringReader stringReader = new StringReader(content)) {

			XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(stringReader);

			try {

				XmlSerializationContext context = XmlSerializationContext.of(reader);

				while (reader.next() != XMLEvent.END_DOCUMENT) {

					if (reader.getName().getLocalPart().equals("object")) {
						object.read(context, handler);
					}
				}

				return object;

			} finally {
				reader.close();
			}
		}
	}

	public static void read(XmlSerializationHandler handler, final XSerializable object, final InputStream is)
			throws Exception {

		XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(is);

		try {

			XmlSerializationContext context = XmlSerializationContext.of(reader);

			object.read(context, handler);

		} finally {
			reader.close();
		}
	}

	public static class XMLPrettyPrintStreamWriter implements XMLStreamWriter {

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
