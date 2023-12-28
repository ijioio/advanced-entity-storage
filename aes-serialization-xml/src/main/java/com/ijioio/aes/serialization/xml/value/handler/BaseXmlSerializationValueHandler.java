package com.ijioio.aes.serialization.xml.value.handler;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.ijioio.aes.serialization.SerializationException;
import com.ijioio.aes.serialization.xml.XmlSerializationValueHandler;

public abstract class BaseXmlSerializationValueHandler<T> implements XmlSerializationValueHandler<T> {

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
