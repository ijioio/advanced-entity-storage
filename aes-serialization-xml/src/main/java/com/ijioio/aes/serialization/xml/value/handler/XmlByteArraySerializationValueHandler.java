package com.ijioio.aes.serialization.xml.value.handler;

import java.util.Base64;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.serialization.SerializationException;
import com.ijioio.aes.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.serialization.xml.XmlSerializationHandler;

public class XmlByteArraySerializationValueHandler extends BaseXmlSerializationValueHandler<byte[]> {

	@Override
	public Class<byte[]> getType() {
		return byte[].class;
	}

	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, byte[] value,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);

			if (detailed) {
				writer.writeAttribute("class", value.getClass().getName());
			}

			writer.writeCharacters(Base64.getEncoder().encodeToString(value));
			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public byte[] read(XmlSerializationContext context, XmlSerializationHandler handler, Class<byte[]> type,
			byte[] value) throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {
			return Base64.getDecoder().decode(reader.getElementText());
		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
