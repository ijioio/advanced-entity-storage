package com.ijioio.aes.core;

import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;

public interface XSerializable {

	public void write(SerializationContext context, SerializationHandler handler) throws SerializationException;

	public void read(SerializationContext context, SerializationHandler handler) throws SerializationException;
}
