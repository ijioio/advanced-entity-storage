package com.ijioio.aes.core.serialization.xml.value.handler;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlSerializationValueHandler;

@SuppressWarnings("rawtypes")
public class XmlMapSerializationValueHandler extends BaseXmlSerializationValueHandler<Map> {

	@Override
	public Class<Map> getType() {
		return Map.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Map values,
			boolean detailed) throws SerializationException {

		XMLStreamWriter writer = context.getWriter();

		try {

			writer.writeStartElement(name);
			writer.writeAttribute("class", values.getClass().getName());

			for (Entry<Object, Object> entry : ((Map<Object, Object>) values).entrySet()) {

				Object key = entry.getKey();
				Object value = entry.getValue();

				writer.writeStartElement("entry");

				if (key != null) {
					((XmlSerializationValueHandler) handler.getValueHandler(key.getClass())).write(context, handler,
							"key", key, true);
				} else {
					((XmlSerializationValueHandler) handler.getValueHandler(Void.class)).write(context, handler, "key",
							null, true);
				}

				if (value != null) {
					((XmlSerializationValueHandler) handler.getValueHandler(value.getClass())).write(context, handler,
							"value", value, true);
				} else {
					((XmlSerializationValueHandler) handler.getValueHandler(Void.class)).write(context, handler,
							"value", null, true);
				}

				writer.writeEndElement();
			}

			writer.writeEndElement();

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Map> type, Map values)
			throws SerializationException {

		XMLStreamReader reader = context.getReader();

		try {

			// TODO: handle cases when creation of original collection is not possible
			// (i.e. Collections.singletonMap, etc.)
			Map map = values != null ? values : type.newInstance();

			map.clear();

			while (reader.nextTag() != XMLStreamConstants.END_ELEMENT) {

				if (reader.getName().getLocalPart().equals("entry")) {

					Object key = null;
					Object value = null;

					while (reader.nextTag() != XMLStreamConstants.END_ELEMENT) {

						if (reader.getName().getLocalPart().equals("key")) {

							String keyTypeName = reader.getAttributeValue(null, "class");

							if (keyTypeName == null) {
								throw new SerializationException("key type is not defined");
							}

							Class<?> keyType = Class.forName(keyTypeName);

							key = ((XmlSerializationValueHandler) handler.getValueHandler(keyType)).read(context,
									handler, keyType, null);

						} else if (reader.getName().getLocalPart().equals("value")) {

							String valueTypeName = reader.getAttributeValue(null, "class");

							if (valueTypeName == null) {
								throw new SerializationException("value type is not defined");
							}

							Class<?> valueType = Class.forName(valueTypeName);

							value = ((XmlSerializationValueHandler) handler.getValueHandler(valueType)).read(context,
									handler, valueType, null);

						} else {
							skipElement(reader);
						}
					}

					map.put(key, value);

				} else {
					skipElement(reader);
				}
			}

			return map;

		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
