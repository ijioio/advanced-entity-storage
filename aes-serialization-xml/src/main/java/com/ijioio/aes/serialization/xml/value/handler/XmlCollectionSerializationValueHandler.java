package com.ijioio.aes.serialization.xml.value.handler;

import java.util.Collection;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.serialization.SerializationException;
import com.ijioio.aes.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.serialization.xml.XmlSerializationValueHandler;

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
					((XmlSerializationValueHandler) handler.getValueHandler(Void.class)).write(context, handler, "item",
							null, true);
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

			// TODO: handle cases when creation of original collection is not possible
			// (i.e. Collections.singletonList, etc.)
			Collection collection = values != null ? values : type.newInstance();

			collection.clear();

			while (reader.nextTag() != XMLStreamConstants.END_ELEMENT) {

				if (reader.getName().getLocalPart().equals("item")) {

					String elementTypeName = reader.getAttributeValue(null, "class");

					if (elementTypeName == null) {
						throw new SerializationException("item type is not defined");
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
