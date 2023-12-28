package com.ijioio.aes.serialization.xml.value.handler;

import java.util.Optional;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.serialization.SerializationException;
import com.ijioio.aes.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.serialization.xml.XmlSerializationHandler;

public class XmlDoubleSerializationValueHandler extends BaseXmlSerializationValueHandler<Double> {

	@Override
	public Class<Double> getType() {
		return Double.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Double value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(String.valueOf(value.doubleValue()));
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public Double read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Double> type,
			Double value) throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {
			return Optional.of(reader.getElementText()).map(item -> item.length() > 0 ? Double.valueOf(item) : null)
					.orElse(null);
		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
