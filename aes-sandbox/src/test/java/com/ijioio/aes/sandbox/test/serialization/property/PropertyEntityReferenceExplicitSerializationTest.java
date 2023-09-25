package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyEntityReferenceExplicitSerialization;

public class PropertyEntityReferenceExplicitSerializationTest extends BaseSerializationTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.serialization.property.PropertyEntityReferenceExplicitSerializationTest.Some";
	}

	@Entity( //
			name = PropertyEntityReferenceExplicitSerializationPrototype.NAME, //
			types = { //
					@Type(name = "EntityReference<Some>", type = "com.ijioio.aes.core.EntityReference", parameters = @Parameter(name = Some.NAME)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueEntityReference", type = "EntityReference<Some>") //
			} //
	)
	public static interface PropertyEntityReferenceExplicitSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceExplicitSerialization";
	}

	private Path path;

	private PropertyEntityReferenceExplicitSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-entity-reference-explicit-serialization.xml")
				.toURI());

		model = new PropertyEntityReferenceExplicitSerialization();

		model.setId("property-entity-reference-explicit-serialization");
		model.setValueEntityReference(EntityReference.of("some", Some.class));
	}

	@Test
	public void testWrite() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		String actual = XmlUtil.write(handler, model);
		String expected = readString(path);

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		PropertyEntityReferenceExplicitSerialization actual = XmlUtil.read(handler,
				PropertyEntityReferenceExplicitSerialization.class, readString(path));
		PropertyEntityReferenceExplicitSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueEntityReference(), actual.getValueEntityReference());
	}
}
