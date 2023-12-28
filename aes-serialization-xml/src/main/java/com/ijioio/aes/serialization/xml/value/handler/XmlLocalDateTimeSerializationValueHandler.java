package com.ijioio.aes.serialization.xml.value.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.serialization.SerializationException;
import com.ijioio.aes.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.serialization.xml.XmlSerializationHandler;

public class XmlLocalDateTimeSerializationValueHandler extends BaseXmlSerializationValueHandler<LocalDateTime> {

	@Override
	public Class<LocalDateTime> getType() {
		return LocalDateTime.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name,
			LocalDateTime value, boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value));
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public LocalDateTime read(XmlSerializationContext context, XmlSerializationHandler handler,
			Class<LocalDateTime> type, LocalDateTime value) throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {
			return Optional.of(reader.getElementText()).map(
					item -> item.length() > 0 ? LocalDateTime.parse(item, DateTimeFormatter.ISO_LOCAL_DATE_TIME) : null)
					.orElse(null);
		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
