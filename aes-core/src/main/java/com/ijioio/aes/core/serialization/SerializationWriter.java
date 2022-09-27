package com.ijioio.aes.core.serialization;

@FunctionalInterface
public interface SerializationWriter {

	public void write() throws SerializationException;
}
