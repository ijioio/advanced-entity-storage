package com.ijioio.aes.serialization.test.fixture;

import com.ijioio.aes.serialization.SerializationHandler;

public interface HandlerProvider {

	public SerializationHandler createHandler() throws Exception;
}
