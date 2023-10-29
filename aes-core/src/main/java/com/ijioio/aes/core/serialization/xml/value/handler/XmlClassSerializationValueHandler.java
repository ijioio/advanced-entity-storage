package com.ijioio.aes.core.serialization.xml.value.handler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

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

			handler.getValueHandler(String.class).write(context, handler, "name", value.getName(), false);
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

			Class clazz = null;

			while (reader.nextTag() != XMLEvent.END_ELEMENT) {

				if (reader.getName().getLocalPart().equals("name")) {

					String name = handler.getValueHandler(String.class).read(context, handler, String.class, null);

					if (name != null) {
						clazz = Class.forName(name);
					}

				} else {
					skipElement(reader);
				}
			}

			return clazz;

		} catch (ClassNotFoundException | XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
