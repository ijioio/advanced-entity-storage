package com.ijioio.aes.core.serialization.xml.value.handler;

import java.util.Collection;
import java.util.Map;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlSerializationValueHandler;

@SuppressWarnings("rawtypes")
public class XmlCollectionSerializationValueHandler extends BaseXmlSerializationValueHandler<Collection> {

	@Override
	public Class<Collection> getType() {
		return Collection.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Collection values,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);
			writer.writeAttribute("class", values.getClass().getName());

			for (Object value : values) {

				if (value != null) {
					((XmlSerializationValueHandler) handler.getValueHandler(value.getClass())).write(context, handler,
							"item", value, true);
				} else {
					// TODO: handle null?
				}
			}

			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Collection> type,
			Collection values) throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {

			Collection collection = values != null ? values : type.newInstance();

			collection.clear();

			while (reader.nextTag() != XMLStreamConstants.END_ELEMENT) {

				if (reader.getName().getLocalPart().equals("item")) {

					Map<String, String> attributes = readAttributes(reader);

					String elementTypeName = attributes.get("class");

					if (elementTypeName == null) {
						throw new SerializationException("type is not defined");
					}

					Class<?> elementType = Class.forName(elementTypeName);

					collection.add(((XmlSerializationValueHandler) handler.getValueHandler(elementType)).read(context,
							handler, elementType, null));

				} else {
					skipElement(reader);
				}
			}

			return collection;

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
