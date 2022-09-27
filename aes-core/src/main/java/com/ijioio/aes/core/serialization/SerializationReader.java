package com.ijioio.aes.core.serialization;

@FunctionalInterface
public interface SerializationReader {

	public void read() throws SerializationException;
}
