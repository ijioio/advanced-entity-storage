package com.ijioio.aes.serialization.xml.value.handler;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.serialization.SerializationException;
import com.ijioio.aes.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.serialization.xml.XmlSerializationHandler;

public class XmlInstantSerializationValueHandler extends BaseXmlSerializationValueHandler<Instant> {

	@Override
	public Class<Instant> getType() {
		return Instant.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Instant value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(DateTimeFormatter.ISO_INSTANT.format(value));
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public Instant read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Instant> type,
			Instant value) throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {
			return Optional.of(reader.getElementText()).map(item -> item.length() > 0 ? Instant.parse(item) : null)
					.orElse(null);
		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
