package com.ijioio.aes.core;

import java.util.Map;

import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.SerializationReader;
import com.ijioio.aes.core.serialization.SerializationWriter;

public interface XSerializable {

	public Map<String, SerializationWriter> getWriters(SerializationContext context, SerializationHandler handler);

	public Map<String, SerializationReader> getReaders(SerializationContext context, SerializationHandler handler);

	public default void write(SerializationContext context, SerializationHandler handler)
			throws SerializationException {
		handler.write(context, getWriters(context, handler));
	}

	public default void read(SerializationContext context, SerializationHandler handler) throws SerializationException {
		handler.read(context, getReaders(context, handler));
	}
}
