package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.core.BaseIdentity;
import com.ijioio.aes.core.Introspectable;
import com.ijioio.aes.core.IntrospectionException;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyIdentitySerialization;

public class PropertyIdentitySerializationTest extends BaseSerializationTest {

	public static class TestIdentity extends BaseIdentity implements Introspectable {

		public static final String NAME = "com.ijioio.aes.sandbox.test.serialization.property.PropertyIdentitySerializationTest.TestIdentity";

		public static class Properties {

			public static final Property<String> value = Property.of("value", new TypeReference<String>() {
			});

			private static final List<Property<?>> values = new ArrayList<>();

			static {
				values.add(value);
			}
		}

		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public Collection<Property<?>> getProperties() {
			return Properties.values;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T read(Property<T> property) throws IntrospectionException {

			if (Properties.value.equals(property)) {
				return (T) this.value;
			} else {
				throw new IntrospectionException(String.format("property %s is not supported", property));
			}
		}

		@Override
		public <T> void write(Property<T> property, T value) throws IntrospectionException {

			if (Properties.value.equals(property)) {
				this.value = (String) value;
			} else {
				throw new IntrospectionException(String.format("property %s is not supported", property));
			}
		}
	}

	@Entity( //
			name = PropertyIdentitySerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueIdentity1", type = TestIdentity.NAME), //
					@EntityProperty(name = "valueIdentity2", type = TestIdentity.NAME), //
					@EntityProperty(name = "valueIdentity3", type = TestIdentity.NAME) //
			} //
	)
	public static interface PropertyIdentitySerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyIdentitySerialization";
	}

	protected XmlSerializationHandler handler;

	private PropertyIdentitySerialization entity;

	@BeforeEach
	public void before() throws Exception {

		handler = new XmlSerializationHandler();

		entity = createEntity();
	}

	@Test
	public void testWrite() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getXmlFileName())).toURI());

		String actualXml = XmlUtil.write2(handler, entity);
		String expectedXml = readString(path);

		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));

		Assertions.assertEquals(expectedXml, actualXml);
	}

	@Test
	public void testRead() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getXmlFileName())).toURI());

		PropertyIdentitySerialization actualEntity = XmlUtil.read2(handler, getEntityClass(), readString(path));
		PropertyIdentitySerialization expectedEntity = entity;

		check(expectedEntity, actualEntity);
	}

	private String getXmlFileName() {
		return "property-identity-serialization.xml";
	}

	private Class<PropertyIdentitySerialization> getEntityClass() {
		return PropertyIdentitySerialization.class;
	}

	private PropertyIdentitySerialization createEntity() {

		PropertyIdentitySerialization entity = new PropertyIdentitySerialization();

		entity.setId("property-identity-serialization");

		TestIdentity value = new TestIdentity();

		value.setId("some-identity");
		value.setValue("value");

		entity.setValueIdentity1(value);
		entity.setValueIdentity2(value);
		entity.setValueIdentity3(value);

		return entity;
	}

	private void check(PropertyIdentitySerialization expectedEntity, PropertyIdentitySerialization actualEntity) {

		Assertions.assertTrue(expectedEntity.getValueIdentity1() == expectedEntity.getValueIdentity2());
		Assertions.assertTrue(expectedEntity.getValueIdentity1() == expectedEntity.getValueIdentity3());
	}
}
