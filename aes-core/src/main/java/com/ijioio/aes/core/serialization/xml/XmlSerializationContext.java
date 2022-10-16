package com.ijioio.aes.core.serialization.xml;

import java.util.HashMap;
import java.util.Map;

import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.ijioio.aes.core.Identity;
import com.ijioio.aes.core.serialization.SerializationContext;

public class XmlSerializationContext implements SerializationContext {

	public static XmlSerializationContext of(XMLStreamWriter writer) {
		return new XmlSerializationContext(writer, null);
	}

	public static XmlSerializationContext of(XMLStreamReader reader) {
		return new XmlSerializationContext(null, reader);
	}

	private final XMLStreamWriter writer;

	private final XMLStreamReader reader;

	private final Map<String, String> attributes = new HashMap<>();

	private final Map<String, Identity> identities = new HashMap<>();

	private XmlSerializationContext(XMLStreamWriter writer, XMLStreamReader reader) {

		this.writer = writer;
		this.reader = reader;
	}

	public XMLStreamWriter getWriter() {
		return writer;
	}

	public XMLStreamReader getReader() {
		return reader;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public Map<String, Identity> getIdentities() {
		return identities;
	}
}
