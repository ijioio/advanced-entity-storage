package com.ijioio.aes.core.serialization;

import com.ijioio.aes.core.Entity;

public interface SerializationManager {

	public <E extends Entity> E read(Class<E> type, byte[] data) throws SerializationException;

	public <E extends Entity> byte[] write(E object) throws SerializationException;
}
