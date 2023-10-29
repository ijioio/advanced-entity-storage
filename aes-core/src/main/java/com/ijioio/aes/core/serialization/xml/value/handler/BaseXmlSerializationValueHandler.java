package com.ijioio.aes.core.serialization.xml.value.handler;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.xml.XmlSerializationValueHandler;

public abstract class BaseXmlSerializationValueHandler<T> implements XmlSerializationValueHandler<T> {

	protected Map<String, String> readAttributes(XMLStreamReader reader) {

		Map<String, String> attributes = new HashMap<>();

		for (int i = 0; i < reader.getAttributeCount(); i++) {
			attributes.put(reader.getAttributeName(i).getLocalPart(), reader.getAttributeValue(i));
		}

		return attributes;
	}

	protected void writeAttributes(XMLStreamWriter writer, Map<String, String> attributes)
			throws SerializationException {

		try {

			for (Entry<String, String> entry : attributes.entrySet()) {
				writer.writeAttribute(entry.getKey(), entry.getValue() != null ? entry.getValue() : "");
			}

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	protected void skipElement(final XMLStreamReader reader) throws SerializationException {

		try {

			int count = 0;

			while (reader.hasNext()) {

				int event = reader.next();

				if (event == XMLStreamConstants.START_ELEMENT) {

					count++;

				} else if (event == XMLStreamConstants.END_ELEMENT) {

					if (count == 0) {
						return;
					}

					count--;

				} else if (event == XMLStreamConstants.END_DOCUMENT) {
					throw new SerializationException("unexpected end of document");
				}
			}

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
