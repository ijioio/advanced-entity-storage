package com.ijioio.aes.core.serialization.xml;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.serialization.SerializationException;

public abstract class XmlSerializationValueHandler<T> {

	public abstract Class<T> getType();

	public abstract void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, T value)
			throws SerializationException;

	public abstract T read(XmlSerializationContext context, XmlSerializationHandler handler, Class<T> type, T value)
			throws SerializationException;

	protected void writeAttributes(XMLStreamWriter writer, Map<String, String> attributes) throws XMLStreamException {

		for (Entry<String, String> entry : attributes.entrySet()) {
			writer.writeAttribute(entry.getKey(), entry.getValue() != null ? entry.getValue() : "");
		}
	}
}
