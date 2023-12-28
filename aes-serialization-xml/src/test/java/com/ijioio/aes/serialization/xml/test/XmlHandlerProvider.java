package com.ijioio.aes.serialization.xml.test;

import com.ijioio.aes.serialization.SerializationHandler;
import com.ijioio.aes.serialization.test.fixture.HandlerProvider;
import com.ijioio.aes.serialization.xml.XmlSerializationHandler;

public interface XmlHandlerProvider extends HandlerProvider {

	@Override
	public default SerializationHandler createHandler() throws Exception {
		return new XmlSerializationHandler();
	}
}
