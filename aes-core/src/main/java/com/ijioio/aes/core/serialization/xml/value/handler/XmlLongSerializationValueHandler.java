package com.ijioio.aes.core.serialization.xml.value.handler;

import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;

public class XmlLongSerializationValueHandler extends BaseXmlSerializationValueHandler<Long> {

	@Override
	public Class<Long> getType() {
		return Long.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Long value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(String.valueOf(value.longValue()));
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public Long read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Long> type, Long value)
			throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {
			return Optional.of(reader.getElementText()).map(item -> item.length() > 0 ? Long.valueOf(item) : null)
					.orElse(null);
		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
