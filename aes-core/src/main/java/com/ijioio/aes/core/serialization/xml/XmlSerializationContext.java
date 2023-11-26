package com.ijioio.aes.core.serialization.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.Identity;
import com.ijioio.aes.core.serialization.SerializationContext;

public class XmlSerializationContext implements SerializationContext {

	private final XMLStreamWriter writer;

	private final XMLStreamReader reader;

	private final Map<String, Identity> identities = new HashMap<>();

	public XmlSerializationContext(XMLStreamWriter writer) {

		this.writer = writer;
		this.reader = null;
	}

	public XmlSerializationContext(XMLStreamReader reader) {

		this.writer = null;
		this.reader = reader;
	}

	public XMLStreamWriter getWriter() {
		return writer;
	}

	public XMLStreamReader getReader() {
		return reader;
	}

	public Map<String, Identity> getIdentities() {
		return identities;
	}
}
