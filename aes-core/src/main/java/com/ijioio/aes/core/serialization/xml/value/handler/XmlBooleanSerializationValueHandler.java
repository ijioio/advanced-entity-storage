package com.ijioio.aes.core.serialization.xml.value.handler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;

public class XmlBooleanSerializationValueHandler extends BaseXmlSerializationValueHandler<Boolean> {

	@Override
	public Class<Boolean> getType() {
		return Boolean.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Boolean value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(String.valueOf(value.booleanValue()));
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public Boolean read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Boolean> type,
			Boolean value) throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {
			return Boolean.valueOf(reader.getElementText());
		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
