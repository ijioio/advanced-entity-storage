package com.ijioio.aes.core;

public interface Introspectable {

	public <T> T read(Property<T> property) throws IntrospectionException;

	public <T> void write(Property<T> property, T value) throws IntrospectionException;
}
