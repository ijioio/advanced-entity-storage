package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;

public abstract class BasePropertySerializationTest<E extends Entity, V> extends BaseSerializationTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.serialization.property.BasePropertySerializationTest.Some";
	}

	protected XmlSerializationHandler handler;

	protected E entity;

	@BeforeEach
	public void before() throws Exception {

		handler = new XmlSerializationHandler();

		entity = createEntity();
	}

	@Test
	public void testWrite() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getXmlFileName(PropertyType.STANDARD)))
				.toURI());

		String actualXml = XmlUtil.write2(handler, entity);
		String expectedXml = readString(path);

		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));

		Assertions.assertEquals(expectedXml, actualXml);
	}

	@EnabledIf("isNullPropertyValueAllowed")
	@Test
	public void testWriteNull() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getXmlFileName(PropertyType.NULL)))
				.toURI());

		setPropertyValue(entity, null);

		String actualXml = XmlUtil.write2(handler, entity);
		String expectedXml = readString(path);

		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));

		Assertions.assertEquals(expectedXml, actualXml);
	}

	@Test
	public void testRead() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getXmlFileName(PropertyType.STANDARD)))
				.toURI());

		E actualEntity = XmlUtil.read2(handler, getEntityClass(), readString(path));
		E expectedEntity = entity;

		check(expectedEntity, actualEntity);
	}

	@EnabledIf("isNullPropertyValueAllowed")
	@Test
	public void testReadNull() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getXmlFileName(PropertyType.NULL)))
				.toURI());

		setPropertyValue(entity, null);

		E actualEntity = XmlUtil.read2(handler, getEntityClass(), readString(path));
		E expectedEntity = entity;

		check(expectedEntity, actualEntity);
	}

	protected void check(E expectedEntity, E actualEntity) {

		Assertions.assertEquals(expectedEntity.getId(), actualEntity.getId());

		checkPropertyValue(getPropertyValue(expectedEntity), getPropertyValue(actualEntity));
	}

	protected abstract String getXmlFileName(PropertyType type);

	protected abstract Class<E> getEntityClass();

	protected abstract E createEntity();

	protected abstract boolean isNullPropertyValueAllowed();

	protected abstract V getPropertyValue(E entity);

	protected abstract void setPropertyValue(E entity, V value);

	protected abstract void checkPropertyValue(V expectedValue, V actualValue);

	public static enum PropertyType {

		STANDARD, NULL;
	}
}
