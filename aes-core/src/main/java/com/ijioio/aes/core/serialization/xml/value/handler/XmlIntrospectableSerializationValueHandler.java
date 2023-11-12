package com.ijioio.aes.core.serialization.xml.value.handler;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import com.ijioio.aes.core.Introspectable;
import com.ijioio.aes.core.IntrospectionException;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlSerializationValueHandler;

public class XmlIntrospectableSerializationValueHandler extends BaseXmlSerializationValueHandler<Introspectable> {

	@Override
	public Class<Introspectable> getType() {
		return Introspectable.class;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name,
			Introspectable value, boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			if (writeIdentity(context, writer, name, value)) {
				return;
			}

			writer.writeStartElement(name);
			writer.writeAttribute("class", value.getClass().getName());

			Collection<Property<?>> properties = value.getProperties();

			for (Property<?> property : properties) {

				Object propertyValue = value.read(property);

				if (propertyValue != null) {
					((XmlSerializationValueHandler) handler.getValueHandler(propertyValue.getClass())).write(context,
							handler, property.getName(), propertyValue, false);
				} else {
					// TODO: handle null?
				}
			}

			writer.writeEndElement();

		} catch (IntrospectionException | XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Introspectable read(XmlSerializationContext context, XmlSerializationHandler handler,
			Class<Introspectable> type, Introspectable value) throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {

			Introspectable introspectable = readIdentity(context, reader);

			if (introspectable != null) {
				return introspectable;
			}

			introspectable = value != null ? value : type.newInstance();

			Map<String, Property<?>> properties = introspectable.getProperties().stream()
					.collect(Collectors.toMap(item -> item.getName(), item -> item));

			while (reader.nextTag() != XMLEvent.END_ELEMENT) {

				System.out.println("read -> " + reader.getLocalName());

				Property<?> property = properties.get(reader.getName().getLocalPart());

				if (property != null) {

					String propertyTypeName = reader.getAttributeValue(null, "class");

					// TODO: take into account final field. i.e. use class of final field value!
					Class<?> propertyType = propertyTypeName != null ? Class.forName(propertyTypeName)
							: property.getType().getRawType();

					// TODO: take into account final field, i.e. use null for non final field!
					Object propertyValue = value.read(property);

					introspectable.write((Property) property,
							((XmlSerializationValueHandler) handler.getValueHandler(propertyType)).read(context,
									handler, propertyType, propertyValue));

				} else {
					skipElement(reader);
				}
			}

			// TODO: handle properties that were not found to clean up fields!

			return introspectable;

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IntrospectionException
				| XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
