package com.ijioio.aes.core.serialization.xml.value.handler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;

@SuppressWarnings("rawtypes")
public class XmlClassSerializationValueHandler extends BaseXmlSerializationValueHandler<Class> {

	@Override
	public Class<Class> getType() {
		return Class.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Class value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(value.getName());
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public Class read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Class> type, Class value)
			throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {

			String name = reader.getElementText();

			return name != null && name.length() > 0 ? Class.forName(name) : null;

		} catch (ClassNotFoundException | XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
