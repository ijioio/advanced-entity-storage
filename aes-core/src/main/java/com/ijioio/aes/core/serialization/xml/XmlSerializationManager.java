package com.ijioio.aes.core.serialization.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.ijioio.aes.core.Entity;
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
	public <E extends Entity> E read(Class<E> type, byte[] data) throws SerializationException {

		ByteArrayInputStream bais = new ByteArrayInputStream(data);

		E object = XmlUtil.read(handler, type, bais);

		return object;
	}

	@Override
	public <E extends Entity> byte[] write(E object) throws SerializationException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		XmlUtil.write(handler, object, baos);

		return baos.toByteArray();
	}
}
