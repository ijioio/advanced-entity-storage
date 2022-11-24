package com.ijioio.aes.core;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.SerializationReader;
import com.ijioio.aes.core.serialization.SerializationWriter;

/**
 * Base class for all entities.
 */
public abstract class BaseEntity extends BaseIdentity implements Entity {

	@Override
	public void write(SerializationContext context, SerializationHandler handler) throws SerializationException {
		handler.write(context, getWriters(context, handler));
	}

	@Override
	public void read(SerializationContext context, SerializationHandler handler) throws SerializationException {
		handler.read(context, getReaders(context, handler));
	}

	public Map<String, SerializationWriter> getWriters(SerializationContext context, SerializationHandler handler) {

		Map<String, SerializationWriter> writers = new LinkedHashMap<>();

		writers.put("id", () -> handler.write(context, "id", getId()));

		return writers;
	}

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
