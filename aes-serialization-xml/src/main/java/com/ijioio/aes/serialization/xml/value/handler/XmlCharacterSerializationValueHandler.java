package com.ijioio.aes.serialization.xml.value.handler;

import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.serialization.SerializationException;
import com.ijioio.aes.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.serialization.xml.XmlSerializationHandler;

public class XmlCharacterSerializationValueHandler extends BaseXmlSerializationValueHandler<Character> {

	@Override
	public Class<Character> getType() {
		return Character.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Character value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(String.valueOf(value.charValue()));
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public Character read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Character> type,
			Character value) throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {
			return Optional.of(reader.getElementText())
					.map(item -> item.length() > 0 ? Character.valueOf(item.charAt(0)) : null).orElse(null);
		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
