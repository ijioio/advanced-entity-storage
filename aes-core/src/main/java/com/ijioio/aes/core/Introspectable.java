package com.ijioio.aes.core;

import java.util.Collection;

public interface Introspectable {

	public Collection<Property<?>> getProperties();

	public <T> T read(Property<T> property) throws IntrospectionException;

	public <T> void write(Property<T> property, T value) throws IntrospectionException;
}
