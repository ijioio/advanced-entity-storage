package com.ijioio.aes.serialization.xml.value.handler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.serialization.SerializationException;
import com.ijioio.aes.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.serialization.xml.XmlSerializationHandler;

public class XmlLocalDateSerializationValueHandler extends BaseXmlSerializationValueHandler<LocalDate> {

	@Override
	public Class<LocalDate> getType() {
		return LocalDate.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, LocalDate value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(DateTimeFormatter.ISO_LOCAL_DATE.format(value));
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public LocalDate read(XmlSerializationContext context, XmlSerializationHandler handler, Class<LocalDate> type,
			LocalDate value) throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {
			return Optional.of(reader.getElementText())
					.map(item -> item.length() > 0 ? LocalDate.parse(item, DateTimeFormatter.ISO_LOCAL_DATE) : null)
					.orElse(null);
		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
