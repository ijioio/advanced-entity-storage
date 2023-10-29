package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;

public abstract class BasePropertySerializationTest<E extends Entity, V> extends BaseSerializationTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.serialization.property.BasePropertySerializationTest.Some";
	}

	public static class Some1 extends Some {
		// Empty
	}

	public static class Some2 extends Some {
		// Empty
	}

	public static class Some3 extends Some {
		// Empty
	}

	protected static List<Character> characters = new ArrayList<>();

	protected static List<Class<? extends Some>> types = new ArrayList<>();

	static {

		characters.add(Character.valueOf('a'));
		characters.add(Character.valueOf('b'));
		characters.add(Character.valueOf('c'));

		types.add(Some1.class);
		types.add(Some2.class);
		types.add(Some3.class);
	}

	protected final int VALUE_MAX_COUNT = 3;

	protected XmlSerializationHandler handler;

	protected Path path;

	protected E entity;

	@BeforeEach
	public void before() throws Exception {

		handler = new XmlSerializationHandler();

		path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getXmlFileName())).toURI());

		entity = createEntity();
	}

	@Test
	public void testWrite() throws Exception {

		String actualXml = XmlUtil.write2(handler, entity);
		String expectedXml = readString(path);

		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));

		Assertions.assertEquals(expectedXml, actualXml);
	}

	@Test
	public void testRead() throws Exception {

		E actualEntity = XmlUtil.read2(handler, getEntityClass(), readString(path));
		E expectedEntity = entity;

		check(expectedEntity, actualEntity);
	}

	private void check(E expectedEntity, E actualEntity) {

		Assertions.assertEquals(expectedEntity.getId(), actualEntity.getId());

		checkPropertyValue(getPropertyValue(expectedEntity), getPropertyValue(actualEntity));
	}

	protected abstract String getXmlFileName() throws Exception;

	protected abstract Class<E> getEntityClass();

	protected abstract E createEntity();

	protected abstract V getPropertyValue(E entity);

	protected abstract void checkPropertyValue(V expectedValue, V actualValue);
}
