package com.ijioio.aes.sandbox.test;

import java.io.StringReader;
import java.io.StringWriter;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.junit.Test;

import com.ijioio.aes.core.serialization.xml.XmlSerializationContext;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.model.Sandbox;

public class SandboxTest {

	// public static final String BOOLEAN = "BOOLEAN";
	// public static final String BYTE = "BYTE";
	// public static final String SHORT = "SHORT";
	// public static final String INT = "INT";
	// public static final String LONG = "LONG";
	// public static final String CHAR = "CHAR";
	// public static final String FLOAT = "FLOAT";
	// public static final String DOUBLE = "DOUBLE";
	// public static final String STRING = "STRING";
	// public static final String LIST = "LIST";
	// public static final String SET = "SET";
	// public static final String MAP = "MAP";

	@Test
	public void formatterCollectionTest() throws Exception {

		Sandbox sandbox = new Sandbox();

		sandbox.setValueBoolean(true);
		sandbox.setValueByte((byte) 1);
		sandbox.setValueShort((short) 1);
		sandbox.setValueInt(1);
		sandbox.setValueLong(1);
		sandbox.setValueChar('a');
		sandbox.setValueFloat((float) 1.0);
		sandbox.setValueDouble(1.0);
//		sandbox.setValueByte(Byte.MAX_VALUE);
//		sandbox.setValueShort(Short.MAX_VALUE);
//		sandbox.setValueInt(Integer.MAX_VALUE);
//		sandbox.setValueLong(Long.MAX_VALUE);
//		sandbox.setValueChar(Character.MAX_VALUE);
//		sandbox.setValueFloat(Float.MAX_VALUE);
//		sandbox.setValueDouble(Double.MAX_VALUE);
		sandbox.setValueString("value");
		sandbox.setValueEnum(Month.JANUARY);
		sandbox.setValueStringList(new ArrayList<>(Arrays.asList("value1", "value2")));
		sandbox.setValueEnumList(new ArrayList<>(Arrays.asList(Month.JANUARY, Month.FEBRUARY)));
		sandbox.setValueObjectList(new ArrayList<>(Arrays.asList("value", Month.JANUARY, Arrays.asList("value"))));

		String content = null;

		XmlSerializationHandler handler = new XmlSerializationHandler();

		XMLOutputFactory output = XMLOutputFactory.newInstance();

		try (StringWriter stringWriter = new StringWriter()) {

			XMLStreamWriter writer = output.createXMLStreamWriter(stringWriter);

			XmlSerializationContext context = XmlSerializationContext.of(writer);

			writer.writeStartDocument();
			writer.writeStartElement("root");
			sandbox.write(context, handler);
			writer.writeEndElement();
			writer.writeEndDocument();

			writer.flush();
			writer.close();

			content = stringWriter.toString();
		}

		System.out.println(formatXml(content));

		Sandbox sandboxOutput = new Sandbox();

		XMLInputFactory input = XMLInputFactory.newInstance();

		try {

			try (StringReader stringReader = new StringReader(content)) {

				XMLStreamReader reader = input.createXMLStreamReader(stringReader);

				XmlSerializationContext context = XmlSerializationContext.of(reader);

				while (reader.next() != XMLEvent.END_DOCUMENT) {

					if (reader.getName().getLocalPart().equals("root")) {
						sandboxOutput.read(context, handler);
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("sandbox output -> " + sandboxOutput);
	}

	private String formatXml(String xml) throws TransformerException {

		// write data to xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();

		Transformer transformer = transformerFactory.newTransformer();

		// alternative
		// Transformer transformer =
		// SAXTransformerFactory.newInstance().newTransformer();

		// pretty print by indention
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		// add standalone="yes", add line break before the root element
		transformer.setOutputProperty(OutputKeys.STANDALONE, "yes");

		/*
		 * transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC,
		 * "-//W3C//DTD XHTML 1.0 Transitional//EN");
		 * 
		 * transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM,
		 * "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd");
		 */

		// ignore <?xml version="1.0" encoding="UTF-8"?>
		// transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

		StreamSource source = new StreamSource(new StringReader(xml));
		StringWriter output = new StringWriter();
		transformer.transform(source, new StreamResult(output));

		return output.toString();
	}
}
