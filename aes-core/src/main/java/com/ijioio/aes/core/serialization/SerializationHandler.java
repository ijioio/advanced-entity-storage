package com.ijioio.aes.core.serialization;

import com.ijioio.aes.core.Entity;

public interface SerializationHandler<C extends SerializationContext> {

	public void write(C context, Entity entity) throws SerializationException;

	public void read(C context, Entity entity) throws SerializationException;
}
