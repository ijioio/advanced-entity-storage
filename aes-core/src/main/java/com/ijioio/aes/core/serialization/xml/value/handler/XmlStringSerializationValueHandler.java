package com.ijioio.aes.core.serialization.xml.value.handler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;

public class XmlStringSerializationValueHandler extends BaseXmlSerializationValueHandler<String> {

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, String value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(value);
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public String read(XmlSerializationContext context, XmlSerializationHandler handler, Class<String> type,
			String value) throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {
			return reader.getElementText();
		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
