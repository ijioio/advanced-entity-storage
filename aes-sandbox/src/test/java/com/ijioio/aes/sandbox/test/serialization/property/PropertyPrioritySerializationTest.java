package com.ijioio.aes.sandbox.test.serialization.property;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.core.Introspectable;
import com.ijioio.aes.core.IntrospectionException;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyPrioritySerialization;

public class PropertyPrioritySerializationTest extends BaseSerializationTest {

	public static class TestIntrospectableList extends ArrayList<String> implements Introspectable {

		private static final long serialVersionUID = 7303271295828257769L;

		public static final String NAME = "com.ijioio.aes.sandbox.test.serialization.property.PropertyPrioritySerializationTest.TestIntrospectableList";

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

	public static class TestIntrospectableSet extends LinkedHashSet<String> implements Introspectable {

		private static final long serialVersionUID = -6619434629245965240L;

		public static final String NAME = "com.ijioio.aes.sandbox.test.serialization.property.PropertyPrioritySerializationTest.TestIntrospectableSet";

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

	public static class TestIntrospectableMap extends LinkedHashMap<String, String> implements Introspectable {

		private static final long serialVersionUID = 3470892087423705009L;

		public static final String NAME = "com.ijioio.aes.sandbox.test.serialization.property.PropertyPrioritySerializationTest.TestIntrospectableMap";

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
			name = PropertyPrioritySerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueIntrospectableList", type = TestIntrospectableList.NAME), //
					@EntityProperty(name = "valueIntrospectableSet", type = TestIntrospectableSet.NAME), //
					@EntityProperty(name = "valueIntrospectableMap", type = TestIntrospectableMap.NAME) //
			} //
	)
	public static interface PropertyPrioritySerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyPrioritySerialization";
	}

	protected XmlSerializationHandler handler;

	private PropertyPrioritySerialization entity;

	@BeforeEach
	public void before() throws Exception {

		handler = new XmlSerializationHandler();

		entity = createEntity();
	}

	@Test
	public void testWrite() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getXmlFileName())).toURI());

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		handler.write(entity, os);

		String actualXml = new String(os.toByteArray(), StandardCharsets.UTF_8);
		String expectedXml = readString(path);

		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));

		Assertions.assertEquals(expectedXml, actualXml);
	}

	@Test
	public void testRead() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getXmlFileName())).toURI());

		ByteArrayInputStream is = new ByteArrayInputStream(Files.readAllBytes(path));

		PropertyPrioritySerialization actualEntity = handler.read(is);
		PropertyPrioritySerialization expectedEntity = entity;

		check(expectedEntity, actualEntity);
	}

	private String getXmlFileName() {
		return "property-priority-serialization.xml";
	}

	private Class<PropertyPrioritySerialization> getEntityClass() {
		return PropertyPrioritySerialization.class;
	}

	private PropertyPrioritySerialization createEntity() {

		PropertyPrioritySerialization entity = new PropertyPrioritySerialization();

		entity.setId("property-priority-serialization");

		TestIntrospectableList valueList = new TestIntrospectableList();

		valueList.setValue("list");

		entity.setValueIntrospectableList(valueList);

		TestIntrospectableSet valueSet = new TestIntrospectableSet();

		valueSet.setValue("set");

		entity.setValueIntrospectableSet(valueSet);

		TestIntrospectableMap valueMap = new TestIntrospectableMap();

		valueMap.setValue("map");

		entity.setValueIntrospectableMap(valueMap);

		return entity;
	}

	private void check(PropertyPrioritySerialization expectedEntity, PropertyPrioritySerialization actualEntity) {

		Assertions.assertEquals(expectedEntity.getValueIntrospectableList().getValue(),
				actualEntity.getValueIntrospectableList().getValue());
		Assertions.assertEquals(expectedEntity.getValueIntrospectableSet().getValue(),
				actualEntity.getValueIntrospectableSet().getValue());
		Assertions.assertEquals(expectedEntity.getValueIntrospectableMap().getValue(),
				actualEntity.getValueIntrospectableMap().getValue());
	}
}
