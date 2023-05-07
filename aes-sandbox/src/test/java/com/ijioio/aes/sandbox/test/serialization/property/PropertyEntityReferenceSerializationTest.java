package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyEntityReferenceSerialization;

public class PropertyEntityReferenceSerializationTest extends BaseSerializationTest {

	public static class Some extends BaseEntity {
		// Empty
	}

	@Entity( //
			name = PropertyEntityReferenceSerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueEntityReference", type = @Type(name = Type.ENTITY_REFERENCE), parameters = @Type(name = "com.ijioio.aes.sandbox.test.serialization.property.PropertyEntityReferenceSerializationTest.Some")) //
			} //
	)
	public static interface PropertyEntityReferenceSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceSerialization";
	}

	private Path path;

	private PropertyEntityReferenceSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths
				.get(getClass().getClassLoader().getResource("property-entity-reference-serialization.xml").toURI());

		model = new PropertyEntityReferenceSerialization();

		model.setId("property-entity-reference-serialization");
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

		PropertyEntityReferenceSerialization actual = XmlUtil.read(handler, PropertyEntityReferenceSerialization.class,
				readString(path));
		PropertyEntityReferenceSerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueEntityReference(), actual.getValueEntityReference());
	}
}
