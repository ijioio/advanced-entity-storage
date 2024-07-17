package com.ijioio.aes.serialization.xml.value.handler;

import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.serialization.SerializationException;
import com.ijioio.aes.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.serialization.xml.XmlSerializationHandler;

@SuppressWarnings("rawtypes")
public class XmlEnumSerializationValueHandler extends BaseXmlSerializationValueHandler<Enum> {

	@Override
	public Class<Enum> getType() {
		return Enum.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Enum value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(value.name());
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enum read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Enum> type, Enum value)
			throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {
			return Optional.of(reader.getElementText()).map(item -> item.length() > 0 ? Enum.valueOf(type, item) : null)
					.orElse(null);
		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
