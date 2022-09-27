package com.ijioio.aes.core;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.SerializationReader;
import com.ijioio.aes.core.serialization.SerializationWriter;

/**
 * Base class for all entities.
 */
public abstract class BaseEntity extends BaseIdentity implements Entity {

	@Override
	public Map<String, SerializationWriter> getWriters(SerializationContext context, SerializationHandler handler) {

		Map<String, SerializationWriter> writers = new LinkedHashMap<>();

		writers.put("id", () -> handler.write(context, "id", getId()));

		return writers;
	}

	@Override
	public Map<String, SerializationReader> getReaders(SerializationContext context, SerializationHandler handler) {

		Map<String, SerializationReader> readers = new LinkedHashMap<>();

		readers.put("id", () -> setId(handler.read(context, getId())));

		return readers;
	}

	@Override
	public String toString() {
		return "BaseEntity [id=" + getId() + ", type=" + getClass().getName() + "]";
	}
}
