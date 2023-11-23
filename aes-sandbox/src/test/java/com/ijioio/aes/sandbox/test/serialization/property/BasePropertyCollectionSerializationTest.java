package com.ijioio.aes.sandbox.test.serialization.property;

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
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.Entity;

public abstract class BasePropertyCollectionSerializationTest<E extends Entity, V extends Collection<I>, I>
		extends BasePropertySerializationTest<E, V> {

	public static class TestEntity1 extends TestEntity {
		// Empty
	}

	public static class TestEntity2 extends TestEntity {
		// Empty
	}

	public static class TestEntity3 extends TestEntity {
		// Empty
	}

	protected static List<Character> characters = new ArrayList<>();

	protected static List<Class<? extends TestEntity>> types = new ArrayList<>();

	static {

		characters.add(Character.valueOf('a'));
		characters.add(Character.valueOf('b'));
		characters.add(Character.valueOf('c'));

		types.add(TestEntity1.class);
		types.add(TestEntity2.class);
		types.add(TestEntity3.class);
	}

	protected final int VALUE_MAX_COUNT = 3;

	@Test
	public void testWriteElementsEmpty() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getElementsEmptyXmlFileName())).toURI());

		setPropertyValue(entity, createEmptyPropertyValue());

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		handler.write(entity, os);

		String actualXml = new String(os.toByteArray(), StandardCharsets.UTF_8);
		String expectedXml = readString(path);

		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));

		Assertions.assertEquals(expectedXml, actualXml);
	}

	@Test
	public void testWriteElementsNull() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getElementsNullXmlFileName())).toURI());

		setPropertyValue(entity, createAllNullPropertyValue());

		ByteArrayOutputStream os = new ByteArrayOutputStream();

		handler.write(entity, os);

		String actualXml = new String(os.toByteArray(), StandardCharsets.UTF_8);
		String expectedXml = readString(path);

		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));

		Assertions.assertEquals(expectedXml, actualXml);
	}

	@Test
	public void testReadElementsEmpty() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getElementsEmptyXmlFileName())).toURI());

		setPropertyValue(entity, createEmptyPropertyValue());

		ByteArrayInputStream is = new ByteArrayInputStream(Files.readAllBytes(path));

		E actualEntity = handler.read(is);
		E expectedEntity = entity;

		check(expectedEntity, actualEntity);
	}

	@Test
	public void testReadElementsNull() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getElementsNullXmlFileName())).toURI());

		setPropertyValue(entity, createAllNullPropertyValue());

		ByteArrayInputStream is = new ByteArrayInputStream(Files.readAllBytes(path));

		E actualEntity = handler.read(is);
		E expectedEntity = entity;

		check(expectedEntity, actualEntity);
	}

	protected abstract String getElementsEmptyXmlFileName();

	protected abstract String getElementsNullXmlFileName();

	protected abstract V createEmptyPropertyValue();

	protected abstract V createAllNullPropertyValue();
}
