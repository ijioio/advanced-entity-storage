package com.ijioio.aes.serialization.xml.value.handler;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.serialization.SerializationException;
import com.ijioio.aes.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.serialization.xml.XmlSerializationHandler;

public class XmlLocalTimeSerializationValueHandler extends BaseXmlSerializationValueHandler<LocalTime> {

	@Override
	public Class<LocalTime> getType() {
		return LocalTime.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, LocalTime value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(DateTimeFormatter.ISO_LOCAL_TIME.format(value));
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public LocalTime read(XmlSerializationContext context, XmlSerializationHandler handler, Class<LocalTime> type,
			LocalTime value) throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {
			return Optional.of(reader.getElementText())
					.map(item -> item.length() > 0 ? LocalTime.parse(item, DateTimeFormatter.ISO_LOCAL_TIME) : null)
					.orElse(null);
		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
