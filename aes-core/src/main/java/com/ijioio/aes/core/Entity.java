package com.ijioio.aes.core;

import java.util.Collection;

/**
 * Interface defining entity. Entity is a simple object containing properties.
 */
public interface Entity extends Identity, Introspectable, XSerializable {

	public Collection<Property<?>> getProperties();
}
