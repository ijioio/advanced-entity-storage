package com.ijioio.aes.core.serialization.xml;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.Identity;
import com.ijioio.aes.core.Introspectable;
import com.ijioio.aes.core.XSerializable;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.SerializationReader;
import com.ijioio.aes.core.serialization.SerializationWriter;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlBooleanSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlByteArraySerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlByteSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlCharacterSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlClassSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlCollectionSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlDoubleSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlEnumSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlFloatSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlIntrospectableSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlInstantSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlIntegerSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLocalDateSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLocalDateTimeSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLocalTimeSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlLongSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlMapSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlShortSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlStringSerializationValueHandler;
import com.ijioio.aes.core.serialization.xml.value.handler.XmlVoidSerializationValueHandler;

public class XmlSerializationHandler implements SerializationHandler {

	private static final XmlSerializationValueHandler<XSerializable> HANDLER_XSERIALIZABLE = new XmlSerializationValueHandler<XSerializable>() {

		@Override
		public Class<XSerializable> getType() {
			return XSerializable.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name,
				XSerializable value) throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				value.write(context, handler);
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public XSerializable read(XmlSerializationContext context, XmlSerializationHandler handler,
				Class<XSerializable> type, XSerializable value) throws SerializationException {

			try {

				XSerializable xSerializable = value != null ? value : type.newInstance();

				xSerializable.read(context, handler);

				return xSerializable;

			} catch (IllegalAccessException | InstantiationException e) {
				throw new SerializationException(e);
			}
		};
	};

	private final Map<String, Class<?>> types = new ConcurrentHashMap<>();

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
		registerValueHandler(HANDLER_XSERIALIZABLE);
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

	@SuppressWarnings("unchecked")
	public <T> XmlSerializationValueHandler<T> getValueHandler(String type) {
		return (XmlSerializationValueHandler<T>) handlers.get(type);
	}

	@SuppressWarnings("unchecked")
	private <T> Class<T> getType(final String name) {

		Class<?> type = types.computeIfAbsent(name, key -> {

			try {
				return Class.forName(name, true, XmlSerializationHandler.class.getClassLoader());
			} catch (ClassNotFoundException e) {
				return Void.class;
			}
		});

		return !type.equals(Void.class) ? (Class<T>) type : null;
	}

	private Class<?> getNormalizedType(Class<?> type) {

		if (Introspectable.class.isAssignableFrom(type)) {
			return Introspectable.class;
		}

		if (XSerializable.class.isAssignableFrom(type)) {
			return XSerializable.class;
		}

		if (Enum.class.isAssignableFrom(type)) {
			return Enum.class;
		}

		if (Collection.class.isAssignableFrom(type)) {
			return Collection.class;
		}

		if (Map.class.isAssignableFrom(type)) {
			return Map.class;
		}

		return type;
	}

	// ================================================

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void write(SerializationContext context, Entity entity) throws SerializationException {
		((XmlSerializationValueHandler) getValueHandler(entity.getClass())).write((XmlSerializationContext) context,
				this, "object", entity, true);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void read(SerializationContext context, Entity entity) throws SerializationException {

		try {

			XMLStreamReader reader = ((XmlSerializationContext) context).getReader();

			reader.nextTag();

			System.out.println("read -> " + reader.getLocalName());

			if (reader.getName().getLocalPart().equals("object")) {

				// TODO: check on class?

				((XmlSerializationValueHandler) getValueHandler(entity.getClass()))
						.read((XmlSerializationContext) context, this, entity.getClass(), entity);
			}

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	// ================================================

	public void writeAttributes(XMLStreamWriter writer, Map<String, String> attributes) throws SerializationException {

		try {

			for (Entry<String, String> entry : attributes.entrySet()) {
				writer.writeAttribute(entry.getKey(), entry.getValue() != null ? entry.getValue() : "");
			}

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	public Map<String, String> readAttributes(XMLStreamReader reader) {

		Map<String, String> attributes = new HashMap<>();

		for (int i = 0; i < reader.getAttributeCount(); i++) {
			attributes.put(reader.getAttributeName(i).getLocalPart(), reader.getAttributeValue(i));
		}

		return attributes;
	}

	public void skipElement(final XMLStreamReader reader) throws SerializationException {

		try {

			int count = 0;

			while (reader.hasNext()) {

				int event = reader.next();

				if (event == XMLStreamConstants.START_ELEMENT) {

					count++;

				} else if (event == XMLStreamConstants.END_ELEMENT) {

					if (count == 0) {
						return;
					}

					count--;

				} else if (event == XMLStreamConstants.END_DOCUMENT) {
					throw new SerializationException("unexpected end of document");
				}
			}

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public void write(SerializationContext context, String name, boolean value) throws SerializationException {
		write(context, name, Boolean.valueOf(value), false);
	}

	@Override
	public void write(SerializationContext context, String name, char value) throws SerializationException {
		write(context, name, Character.valueOf(value), false);
	}

	@Override
	public void write(SerializationContext context, String name, byte value) throws SerializationException {
		write(context, name, Byte.valueOf(value), false);
	}

	@Override
	public void write(SerializationContext context, String name, short value) throws SerializationException {
		write(context, name, Short.valueOf(value), false);
	}

	@Override
	public void write(SerializationContext context, String name, int value) throws SerializationException {
		write(context, name, Integer.valueOf(value), false);
	}

	@Override
	public void write(SerializationContext context, String name, long value) throws SerializationException {
		write(context, name, Long.valueOf(value), false);
	}

	@Override
	public void write(SerializationContext context, String name, float value) throws SerializationException {
		write(context, name, Float.valueOf(value), false);
	}

	@Override
	public void write(SerializationContext context, String name, double value) throws SerializationException {
		write(context, name, Double.valueOf(value), false);
	}

	@Override
	public void write(SerializationContext context, String name, byte[] value) throws SerializationException {
		write(context, name, value, false);
	}

	@Override
	public void write(SerializationContext context, String name, String value) throws SerializationException {
		write(context, name, value, false);
	}

	@Override
	public void write(SerializationContext context, String name, Instant value) throws SerializationException {
		write(context, name, value, false);
	}

	@Override
	public void write(SerializationContext context, String name, LocalDate value) throws SerializationException {
		write(context, name, value, false);
	}

	@Override
	public void write(SerializationContext context, String name, LocalTime value) throws SerializationException {
		write(context, name, value, false);
	}

	@Override
	public void write(SerializationContext context, String name, LocalDateTime value) throws SerializationException {
		write(context, name, value, false);
	}

	@Override
	public <T> void write(SerializationContext context, String name, T value) throws SerializationException {
		write(context, name, value, true);
	}

	protected <T> void write(SerializationContext context, String name, T value, boolean detailed)
			throws SerializationException {

		if (value == null) {
			return;
		}

		XMLStreamWriter writer = ((XmlSerializationContext) context).getWriter();

		try {

			Map<String, String> attributes = ((XmlSerializationContext) context).getAttributes();

			attributes.clear();

			Class<?> type = value.getClass();

			if (Identity.class.isAssignableFrom(type)) {

				Map<String, Identity> identities = ((XmlSerializationContext) context).getIdentities();
				Identity identity = (Identity) value;

				if (identities.containsKey(identity.getId())) {

					attributes.put("id", identity.getId());
					attributes.put("class", type.getName());

					writer.writeEmptyElement(name);
					writeAttributes(writer, attributes);

					return;

				} else {
					identities.put(identity.getId(), identity);
				}
			}

			XmlSerializationValueHandler<Object> handler = getValueHandler(type.getName());

			if (handler == null) {
				handler = getValueHandler(getNormalizedType(type).getName());
			}

			if (handler != null) {

				if (detailed) {
					attributes.put("class", type.getName());
				}

				handler.write((XmlSerializationContext) context, this, name, value);

			} else {
				throw new SerializationException(String.format("type %s is not supported", type));
			}

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}

	@Override
	public void write(SerializationContext context, Map<String, SerializationWriter> writers)
			throws SerializationException {

		for (Entry<String, SerializationWriter> entry : writers.entrySet()) {

			SerializationWriter writer = entry.getValue();

			writer.write();
		}
	}

	@Override
	public boolean read(SerializationContext context, boolean value) throws SerializationException {
		return read(context, Boolean.valueOf(value), Boolean.class).booleanValue();
	}

	@Override
	public char read(SerializationContext context, char value) throws SerializationException {
		return read(context, Character.valueOf(value), Character.class).charValue();
	}

	@Override
	public byte read(SerializationContext context, byte value) throws SerializationException {
		return read(context, Byte.valueOf(value), Byte.class).byteValue();
	}

	@Override
	public short read(SerializationContext context, short value) throws SerializationException {
		return read(context, Short.valueOf(value), Short.class).shortValue();
	}

	@Override
	public int read(SerializationContext context, int value) throws SerializationException {
		return read(context, Integer.valueOf(value), Integer.class).intValue();
	}

	@Override
	public long read(SerializationContext context, long value) throws SerializationException {
		return read(context, Long.valueOf(value), Long.class).longValue();
	}

	@Override
	public float read(SerializationContext context, float value) throws SerializationException {
		return read(context, Float.valueOf(value), Float.class).floatValue();
	}

	@Override
	public double read(SerializationContext context, double value) throws SerializationException {
		return read(context, Double.valueOf(value), Double.class).doubleValue();
	}

	@Override
	public byte[] read(SerializationContext context, byte[] value) throws SerializationException {
		return read(context, value, byte[].class);
	}

	@Override
	public String read(SerializationContext context, String value) throws SerializationException {
		return read(context, value, String.class);
	}

	@Override
	public Instant read(SerializationContext context, Instant value) throws SerializationException {
		return read(context, value, Instant.class);
	}

	@Override
	public LocalDate read(SerializationContext context, LocalDate value) throws SerializationException {
		return read(context, value, LocalDate.class);
	}

	@Override
	public LocalTime read(SerializationContext context, LocalTime value) throws SerializationException {
		return read(context, value, LocalTime.class);
	}

	@Override
	public LocalDateTime read(SerializationContext context, LocalDateTime value) throws SerializationException {
		return read(context, value, LocalDateTime.class);
	}

	@Override
	public <T> T read(SerializationContext context, T value) throws SerializationException {

		XMLStreamReader reader = ((XmlSerializationContext) context).getReader();

		Map<String, String> attributes = readAttributes(reader);

		String typeName = attributes.get("class");

		if (typeName == null) {
			throw new SerializationException("type is not defined");
		}

		Class<T> type = getType(typeName);

		if (type == null) {
			throw new SerializationException(String.format("failed to load class %s", typeName));
		}

		return read(context, value, type);
	}

	@SuppressWarnings("unchecked")
	protected <T> T read(SerializationContext context, T value, Class<T> type) throws SerializationException {

		XMLStreamReader reader = ((XmlSerializationContext) context).getReader();

		Map<String, String> attributes = readAttributes(reader);

		String id = attributes.get("id");

		if (id != null) {

			Map<String, Identity> identities = ((XmlSerializationContext) context).getIdentities();

			skipElement(reader);

			return (T) identities.get(id);
		}

		XmlSerializationValueHandler<T> handler = getValueHandler(type.getName());

		if (handler == null) {
			handler = getValueHandler(getNormalizedType(type).getName());
		}

		if (handler != null) {

			T object = handler.read((XmlSerializationContext) context, this, type, value);

			if (Identity.class.isAssignableFrom(type)) {

				Map<String, Identity> identities = ((XmlSerializationContext) context).getIdentities();
				Identity identity = (Identity) object;

				identities.put(identity.getId(), identity);
			}

			return object;

		} else {
			throw new SerializationException(String.format("type %s is not supported", type));
		}
	}

	@Override
	public void read(SerializationContext context, Map<String, SerializationReader> readers)
			throws SerializationException {

		XMLStreamReader reader = ((XmlSerializationContext) context).getReader();

		try {

			while (reader.nextTag() != XMLEvent.END_ELEMENT) {

//				System.out.println("read -> " + reader.getLocalName());

				SerializationReader serializationReader = readers.get(reader.getName().getLocalPart());

				if (serializationReader != null) {
					serializationReader.read();
				} else {
					skipElement(reader);
				}
			}

		} catch (XMLStreamException e) {
			throw new SerializationException(e);
		}
	}
}
