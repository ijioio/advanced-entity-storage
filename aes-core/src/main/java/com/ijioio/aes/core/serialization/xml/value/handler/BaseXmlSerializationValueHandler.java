package com.ijioio.aes.core.serialization.xml.value.handler;

import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.Identity;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationValueHandler;

public abstract class BaseXmlSerializationValueHandler<T> implements XmlSerializationValueHandler<T> {

	protected boolean writeIdentity(XmlSerializationContext context, XMLStreamWriter writer, String name, T value)
			throws SerializationException {

		try {

			Class<?> type = value.getClass();

			if (Identity.class.isAssignableFrom(type)) {

				Identity identity = (Identity) value;

				Map<String, Identity> identities = context.getIdentities();

				if (identities.containsKey(identity.getId())) {

					writer.writeEmptyElement(name);
					writer.writeAttribute("id", identity.getId());
					writer.writeAttribute("class", type.getName());

					return true;

				} else {
					identities.put(identity.getId(), identity);
				}
			}

			return false;

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected T readIdentity(XmlSerializationContext context, XMLStreamReader reader) throws SerializationException {

		String id = reader.getAttributeValue(null, "id");

		if (id != null) {

			Map<String, Identity> identities = context.getIdentities();

			skipElement(reader);

			return (T) identities.get(id);
		}

		return null;
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
