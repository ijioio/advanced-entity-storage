package com.ijioio.aes.core.serialization.xml.value.handler;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;

public class XmlVoidSerializationValueHandler extends BaseXmlSerializationValueHandler<Void> {

	@Override
	public Class<Void> getType() {
		return Void.class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Void value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeEmptyElement(name);

			if (detailed) {
				writer.writeAttribute("class", Void.class.getName());
			}

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public Void read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Void> type, Void value)
			throws SerializationException {

		XMLStreamReader reader = context.getReader();

		skipElement(reader);

		return null;
	}
}
