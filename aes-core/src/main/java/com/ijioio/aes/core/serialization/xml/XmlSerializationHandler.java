package com.ijioio.aes.core.serialization.xml;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;

import com.ijioio.aes.core.Identity;
import com.ijioio.aes.core.XSerializable;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.SerializationReader;
import com.ijioio.aes.core.serialization.SerializationWriter;
import com.ijioio.aes.core.util.TextUtil;

public class XmlSerializationHandler implements SerializationHandler {

	private static final XmlSerializationValueHandler<Boolean> HANDLER_BOOLEAN = new XmlSerializationValueHandler<Boolean>() {

		@Override
		public Class<Boolean> getType() {
			return Boolean.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Boolean value)
				throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(String.valueOf(value.booleanValue()));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public Boolean read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Boolean> type,
				Boolean value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return Boolean.valueOf(reader.getElementText());

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<Character> HANDLER_CHARACTER = new XmlSerializationValueHandler<Character>() {

		@Override
		public Class<Character> getType() {
			return Character.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name,
				Character value) throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(String.valueOf(value.charValue()));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public Character read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Character> type,
				Character value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				// TODO: make it safe!
				return Character.valueOf(reader.getElementText().charAt(0));

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<Byte> HANDLER_BYTE = new XmlSerializationValueHandler<Byte>() {

		@Override
		public Class<Byte> getType() {
			return Byte.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Byte value)
				throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(String.valueOf(value.byteValue()));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public Byte read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Byte> type, Byte value)
				throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return Byte.valueOf(reader.getElementText());

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<Integer> HANDLER_INTEGER = new XmlSerializationValueHandler<Integer>() {

		@Override
		public Class<Integer> getType() {
			return Integer.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Integer value)
				throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(String.valueOf(value.intValue()));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public Integer read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Integer> type,
				Integer value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return Integer.valueOf(reader.getElementText());

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<Short> HANDLER_SHORT = new XmlSerializationValueHandler<Short>() {

		@Override
		public Class<Short> getType() {
			return Short.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Short value)
				throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(String.valueOf(value.shortValue()));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public Short read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Short> type,
				Short value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return Short.valueOf(reader.getElementText());

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<Long> HANDLER_LONG = new XmlSerializationValueHandler<Long>() {

		@Override
		public Class<Long> getType() {
			return Long.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Long value)
				throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(String.valueOf(value.longValue()));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public Long read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Long> type, Long value)
				throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return Long.valueOf(reader.getElementText());

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<Float> HANDLER_FLOAT = new XmlSerializationValueHandler<Float>() {

		@Override
		public Class<Float> getType() {
			return Float.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Float value)
				throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(String.valueOf(value.floatValue()));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public Float read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Float> type,
				Float value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return Float.valueOf(reader.getElementText());

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<Double> HANDLER_DOUBLE = new XmlSerializationValueHandler<Double>() {

		@Override
		public Class<Double> getType() {
			return Double.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Double value)
				throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(String.valueOf(value.doubleValue()));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public Double read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Double> type,
				Double value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return Double.valueOf(reader.getElementText());

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<byte[]> HANDLER_BYTE_ARRAY = new XmlSerializationValueHandler<byte[]>() {

		@Override
		public Class<byte[]> getType() {
			return byte[].class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, byte[] value)
				throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
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
		};
	};

	private static final XmlSerializationValueHandler<String> HANDLER_STRING = new XmlSerializationValueHandler<String>() {

		@Override
		public Class<String> getType() {
			return String.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, String value)
				throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());

				if (!TextUtil.isEmpty(value)) {
					writer.writeCharacters(value);
				}

				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public String read(XmlSerializationContext context, XmlSerializationHandler handler, Class<String> type,
				String value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return reader.getElementText();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<Instant> HANDLER_INSTANT = new XmlSerializationValueHandler<Instant>() {

		@Override
		public Class<Instant> getType() {
			return Instant.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Instant value)
				throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(DateTimeFormatter.ISO_INSTANT.format(value));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public Instant read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Instant> type,
				Instant value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return Instant.parse(reader.getElementText());

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<LocalDate> HANDLER_LOCAL_DATE = new XmlSerializationValueHandler<LocalDate>() {

		@Override
		public Class<LocalDate> getType() {
			return LocalDate.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name,
				LocalDate value) throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(DateTimeFormatter.ISO_LOCAL_DATE.format(value));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public LocalDate read(XmlSerializationContext context, XmlSerializationHandler handler, Class<LocalDate> type,
				LocalDate value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return LocalDate.parse(reader.getElementText(), DateTimeFormatter.ISO_LOCAL_DATE);

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<LocalTime> HANDLER_LOCAL_TIME = new XmlSerializationValueHandler<LocalTime>() {

		@Override
		public Class<LocalTime> getType() {
			return LocalTime.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name,
				LocalTime value) throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(DateTimeFormatter.ISO_LOCAL_TIME.format(value));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public LocalTime read(XmlSerializationContext context, XmlSerializationHandler handler, Class<LocalTime> type,
				LocalTime value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return LocalTime.parse(reader.getElementText(), DateTimeFormatter.ISO_LOCAL_TIME);

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	private static final XmlSerializationValueHandler<LocalDateTime> HANDLER_LOCAL_DATE_TIME = new XmlSerializationValueHandler<LocalDateTime>() {

		@Override
		public Class<LocalDateTime> getType() {
			return LocalDateTime.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name,
				LocalDateTime value) throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(value));
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@Override
		public LocalDateTime read(XmlSerializationContext context, XmlSerializationHandler handler,
				Class<LocalDateTime> type, LocalDateTime value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				return LocalDateTime.parse(reader.getElementText(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	@SuppressWarnings("rawtypes")
	private static final XmlSerializationValueHandler<Enum> HANDLER_ENUM = new XmlSerializationValueHandler<Enum>() {

		@Override
		public Class<Enum> getType() {
			return Enum.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Enum value)
				throws SerializationException {

			if (value == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());
				writer.writeCharacters(value.name());
				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public Enum read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Enum> type, Enum value)
				throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				// TODO: make it safe!
				return Enum.valueOf(type, reader.getElementText());

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}
	};

	@SuppressWarnings("rawtypes")
	private static final XmlSerializationValueHandler<Collection> HANDLER_COLLECTION = new XmlSerializationValueHandler<Collection>() {

		@Override
		public Class<Collection> getType() {
			return Collection.class;
		}

		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name,
				Collection values) throws SerializationException {

			if (values == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());

				for (Object value : values) {
					handler.write(context, "item", value);
				}

				writer.writeEndElement();

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection read(XmlSerializationContext context, XmlSerializationHandler handler, Class<Collection> type,
				Collection value) throws SerializationException {

			XMLStreamReader reader = context.getReader();

			try {

				Collection collection = value != null ? value
						: List.class.isAssignableFrom(type) ? new ArrayList<>() : new LinkedHashSet<>();

				collection.clear();

				while (reader.nextTag() != XMLStreamConstants.END_ELEMENT) {

					if (reader.getName().getLocalPart().equals("item")) {

						Object item = handler.read(context, (Object) null);

						if (item != null) {
							collection.add(item);
						}

					} else {
						handler.skipElement(reader);
					}
				}

				return collection;

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

	@SuppressWarnings("rawtypes")
	private static final XmlSerializationValueHandler<Map> HANDLER_MAP = new XmlSerializationValueHandler<Map>() {

		@Override
		public Class<Map> getType() {
			return Map.class;
		}

		@SuppressWarnings("unchecked")
		@Override
		public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, Map values)
				throws SerializationException {

			if (values == null) {
				return;
			}

			XMLStreamWriter writer = context.getWriter();

			try {

				writer.writeStartElement(name);
				handler.writeAttributes(writer, context.getAttributes());

				for (Entry<Object, Object> entry : ((Map<Object, Object>) values).entrySet()) {

					writer.writeStartElement("entry");
					handler.write(context, "key", entry.getKey());
					handler.write(context, "value", entry.getValue());
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

				Map map = values != null ? values : new LinkedHashMap<>();

				map.clear();

				while (reader.nextTag() != XMLStreamConstants.END_ELEMENT) {

					if (reader.getName().getLocalPart().equals("entry")) {

						Object key = null;
						Object value = null;

						while (reader.nextTag() != XMLStreamConstants.END_ELEMENT) {

							if (reader.getName().getLocalPart().equals("key")) {
								key = handler.read(context, (Object) null);
							} else if (reader.getName().getLocalPart().equals("value")) {
								value = handler.read(context, (Object) null);
							} else {
								handler.skipElement(reader);
							}
						}

						if (key != null && value != null) {
							map.put(key, value);
						}

					} else {
						handler.skipElement(reader);
					}
				}

				return map;

			} catch (XMLStreamException e) {
				throw new SerializationException(e);
			}
		};
	};

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

				XSerializable result = type.newInstance();

				result.read(context, handler);

				return result;

			} catch (IllegalAccessException | InstantiationException e) {
				throw new SerializationException(e);
			}
		};
	};

	private final Map<String, Class<?>> types = new ConcurrentHashMap<>();

	private final Map<String, XmlSerializationValueHandler<?>> handlers = new HashMap<>();

	public XmlSerializationHandler() {

		registerValueHandler(HANDLER_BOOLEAN);
		registerValueHandler(HANDLER_CHARACTER);
		registerValueHandler(HANDLER_BYTE);
		registerValueHandler(HANDLER_SHORT);
		registerValueHandler(HANDLER_INTEGER);
		registerValueHandler(HANDLER_LONG);
		registerValueHandler(HANDLER_FLOAT);
		registerValueHandler(HANDLER_DOUBLE);
		registerValueHandler(HANDLER_BYTE_ARRAY);
		registerValueHandler(HANDLER_STRING);
		registerValueHandler(HANDLER_INSTANT);
		registerValueHandler(HANDLER_LOCAL_DATE);
		registerValueHandler(HANDLER_LOCAL_TIME);
		registerValueHandler(HANDLER_LOCAL_DATE_TIME);
		registerValueHandler(HANDLER_ENUM);
		registerValueHandler(HANDLER_COLLECTION);
		registerValueHandler(HANDLER_MAP);
		registerValueHandler(HANDLER_XSERIALIZABLE);
	}

	public <T> void registerValueHandler(XmlSerializationValueHandler<T> handler) {
		handlers.put(handler.getType().getName(), handler);
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
