package com.ijioio.aes.core.serialization.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.ijioio.aes.core.XSerializable;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationManager;

public class XmlSerializationManager implements SerializationManager {

	public static XmlSerializationManager of(XmlSerializationHandler handler) {
		return new XmlSerializationManager(handler);
	}

	private final XmlSerializationHandler handler;

	private XmlSerializationManager(XmlSerializationHandler handler) {
		this.handler = handler;
	}

	@Override
	public <X extends XSerializable> X read(Class<X> type, byte[] data) throws SerializationException {

		ByteArrayInputStream bais = new ByteArrayInputStream(data);

		X object = XmlUtil.read(handler, type, bais);

		return object;
	}

	@Override
	public <X extends XSerializable> byte[] write(X object) throws SerializationException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		XmlUtil.write(handler, object, baos);

		return baos.toByteArray();
	}
}
