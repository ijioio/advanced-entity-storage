package com.ijioio.aes.core.serialization;

import com.ijioio.aes.core.XSerializable;

public interface SerializationManager {

	public <X extends XSerializable> X read(Class<X> type, byte[] data) throws SerializationException;

	public <X extends XSerializable> byte[] write(X object) throws SerializationException;
}
