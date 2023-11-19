package com.ijioio.aes.core.serialization.xml;

import com.ijioio.aes.core.serialization.SerializationException;

public interface XmlSerializationValueHandler<T> {

	public Class<T> getType();

	public default void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, T value,
			boolean detailed) throws SerializationException {
		throw new UnsupportedOperationException("not supported");
	};

	public default void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, T value)
			throws SerializationException {
		throw new UnsupportedOperationException("not supported");
	};

	public default T read(XmlSerializationContext context, XmlSerializationHandler handler, Class<T> type, T value)
			throws SerializationException {
		throw new UnsupportedOperationException("not supported");
	};
}
