package com.ijioio.aes.core.serialization.xml;

import com.ijioio.aes.core.serialization.SerializationException;

public interface XmlSerializationValueHandler<T> {

	public Class<T> getType();

	public void write(XmlSerializationContext context, XmlSerializationHandler handler, String name, T value)
			throws SerializationException;

	public T read(XmlSerializationContext context, XmlSerializationHandler handler, Class<T> type, T value)
			throws SerializationException;
}
