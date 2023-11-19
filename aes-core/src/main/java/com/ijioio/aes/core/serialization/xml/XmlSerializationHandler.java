package com.ijioio.aes.core.serialization.xml;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.Introspectable;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlBooleanSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlByteArraySerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlByteSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlCharacterSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlClassSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlCollectionSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlDoubleSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlEnumSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlFloatSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlInstantSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlIntegerSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlIntrospectableSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLocalDateSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLocalDateTimeSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLocalTimeSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLongSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlMapSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlShortSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlStringSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlVoidSerializationValueHandler;

public class XmlSerializationHandler implements SerializationHandler<XmlSerializationContext> {

	private final Map<String, XmlSerializationValueHandler<?>> handlers = new HashMap<>();

	public XmlSerializationHandler() {

		registerValueHandler(new XmlBooleanSerializationValueHandler());
		registerValueHandler(new XmlCharacterSerializationValueHandler());
		registerValueHandler(new XmlByteSerializationValueHandler());
		registerValueHandler(new XmlShortSerializationValueHandler());
		registerValueHandler(new XmlIntegerSerializationValueHandler());
		registerValueHandler(new XmlLongSerializationValueHandler());
		registerValueHandler(new XmlFloatSerializationValueHandler());
		registerValueHandler(new XmlDoubleSerializationValueHandler());
		registerValueHandler(new XmlByteArraySerializationValueHandler());
		registerValueHandler(new XmlStringSerializationValueHandler());
		registerValueHandler(new XmlInstantSerializationValueHandler());
		registerValueHandler(new XmlLocalDateSerializationValueHandler());
		registerValueHandler(new XmlLocalTimeSerializationValueHandler());
		registerValueHandler(new XmlLocalDateTimeSerializationValueHandler());
		registerValueHandler(new XmlEnumSerializationValueHandler());
		registerValueHandler(new XmlClassSerializationValueHandler());
		registerValueHandler(new XmlCollectionSerializationValueHandler());
		registerValueHandler(new XmlMapSerializationValueHandler());
		registerValueHandler(new XmlIntrospectableSerializationValueHandler());
		registerValueHandler(new XmlVoidSerializationValueHandler());
	}

	public <T> void registerValueHandler(XmlSerializationValueHandler<T> handler) {
		handlers.put(handler.getType().getName(), handler);
	}

	@SuppressWarnings("unchecked")
	public <T> XmlSerializationValueHandler<T> getValueHandler(Class<T> type) throws SerializationException {

		XmlSerializationValueHandler<T> handler = (XmlSerializationValueHandler<T>) handlers.get(type.getName());

		if (handler == null) {

			if (Introspectable.class.isAssignableFrom(type)) {
				handler = (XmlSerializationValueHandler<T>) handlers.get(Introspectable.class.getName());
			} else if (Enum.class.isAssignableFrom(type)) {
				handler = (XmlSerializationValueHandler<T>) handlers.get(Enum.class.getName());
			} else if (Collection.class.isAssignableFrom(type)) {
				handler = (XmlSerializationValueHandler<T>) handlers.get(Collection.class.getName());
			} else if (Map.class.isAssignableFrom(type)) {
				handler = (XmlSerializationValueHandler<T>) handlers.get(Map.class.getName());
			}
		}

		if (handler != null) {
			return handler;
		}

		throw new SerializationException(String.format("type %s is not supported", type));
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void write(XmlSerializationContext context, Entity entity) throws SerializationException {
		((XmlSerializationValueHandler) getValueHandler(entity.getClass())).write(context, this, "object", entity,
				true);
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void read(XmlSerializationContext context, Entity entity) throws SerializationException {

		try {

			XMLStreamReader reader = context.getReader();

			reader.nextTag();

			System.out.println("read -> " + reader.getLocalName());

			if (reader.getName().getLocalPart().equals("object")) {

				// TODO: check on class?

				((XmlSerializationValueHandler) getValueHandler(entity.getClass())).read(context, this,
						entity.getClass(), entity);
			}

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
