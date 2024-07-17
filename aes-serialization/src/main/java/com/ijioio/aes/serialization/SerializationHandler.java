package com.ijioio.aes.serialization;

import java.io.InputStream;
import java.io.OutputStream;

import com.ijioio.aes.core.Entity;

public interface SerializationHandler {

	public <E extends Entity> void write(E entity, OutputStream os) throws SerializationException;

	public <E extends Entity> E read(InputStream is) throws SerializationException;
}
