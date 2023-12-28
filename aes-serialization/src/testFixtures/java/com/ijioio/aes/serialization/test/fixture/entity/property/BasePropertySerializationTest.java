package com.ijioio.aes.serialization.test.fixture.entity.property;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
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
import org.junit.jupiter.api.condition.EnabledIf;

import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.Introspectable;
import com.ijioio.aes.core.IntrospectionException;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.serialization.test.fixture.BaseSerializationTest;

public abstract class BasePropertySerializationTest<E extends Entity, V> extends BaseSerializationTest {

	public static class TestEntity extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.serialization.test.fixture.entity.property.BasePropertySerializationTest.TestEntity";
	}

	public static class TestIntrospectable implements Introspectable {

		public static final String NAME = "com.ijioio.aes.serialization.test.fixture.entity.property.BasePropertySerializationTest.TestIntrospectable";

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

	protected E entity;

	@BeforeEach
	public void before() throws Exception {

		entity = createEntity();
	}

	@Test
	public void testWrite() throws Exception {

		Path path = Paths.get(getClass().getClassLoader().getResource(getDataFilePath()).toURI());

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		handler.write(entity, os);

		String actualXml = new String(os.toByteArray(), StandardCharsets.UTF_8);
		String expectedXml = readString(path);

		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));

		Assertions.assertEquals(expectedXml, actualXml);
	}

	@EnabledIf("isNullPropertyValueAllowed")
	@Test
	public void testWriteNull() throws Exception {

		Path path = Paths.get(getClass().getClassLoader().getResource(getNullDataFilePath()).toURI());

		setPropertyValue(entity, null);

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		handler.write(entity, os);

		String actualXml = new String(os.toByteArray(), StandardCharsets.UTF_8);
		String expectedXml = readString(path);

		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));

		Assertions.assertEquals(expectedXml, actualXml);
	}

	@Test
	public void testRead() throws Exception {

		Path path = Paths.get(getClass().getClassLoader().getResource(getDataFilePath()).toURI());

		ByteArrayInputStream is = new ByteArrayInputStream(Files.readAllBytes(path));

		E actualEntity = handler.read(is);
		E expectedEntity = entity;

		check(expectedEntity, actualEntity);
	}

	@EnabledIf("isNullPropertyValueAllowed")
	@Test
	public void testReadNull() throws Exception {

		Path path = Paths.get(getClass().getClassLoader().getResource(getNullDataFilePath()).toURI());

		setPropertyValue(entity, null);

		ByteArrayInputStream is = new ByteArrayInputStream(Files.readAllBytes(path));

		E actualEntity = handler.read(is);
		E expectedEntity = entity;

		check(expectedEntity, actualEntity);
	}

	protected void check(E expectedEntity, E actualEntity) {

		Assertions.assertEquals(expectedEntity.getId(), actualEntity.getId());

		checkPropertyValue(getPropertyValue(expectedEntity), getPropertyValue(actualEntity));
	}

	protected abstract String getDataFilePath();

	protected abstract String getNullDataFilePath();

	protected abstract Class<E> getEntityClass();

	protected abstract E createEntity();

	protected abstract boolean isNullPropertyValueAllowed();

	protected abstract V getPropertyValue(E entity);

	protected abstract void setPropertyValue(E entity, V value);

	protected abstract void checkPropertyValue(V expectedValue, V actualValue);
}
