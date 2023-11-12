package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.serialization.xml.XmlUtil;

public abstract class BasePropertyMapSerializationTest<E extends Entity, V extends Map<I, I>, I>
		extends BasePropertySerializationTest<E, V> {

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

	@Test
	public void testWriteEntriesEmpty() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getEntriesEmptyXmlFileName())).toURI());

		setPropertyValue(entity, createEmptyPropertyValue());

		String actualXml = XmlUtil.write2(handler, entity);
		String expectedXml = readString(path);

		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));

		Assertions.assertEquals(expectedXml, actualXml);
	}

//	@Test
//	public void testWriteEntriesNull() throws Exception {
//
//		Path path = Paths.get(getClass().getClassLoader()
//				.getResource(String.format("serialization/entity/property/%s", getElementsNullXmlFileName())).toURI());
//
//		setPropertyValue(entity, createAllNullPropertyValue());
//
//		String actualXml = XmlUtil.write2(handler, entity);
//		String expectedXml = readString(path);
//
//		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));
//
//		Assertions.assertEquals(expectedXml, actualXml);
//	}

	@Test
	public void testReadEntriesEmpty() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getEntriesEmptyXmlFileName())).toURI());

		setPropertyValue(entity, createEmptyPropertyValue());

		E actualEntity = XmlUtil.read2(handler, getEntityClass(), readString(path));
		E expectedEntity = entity;

		check(expectedEntity, actualEntity);
	}

//	@Test
//	public void testReadEntriesNull() throws Exception {
//
//		Path path = Paths.get(getClass().getClassLoader()
//				.getResource(String.format("serialization/entity/property/%s", getElementsNullXmlFileName())).toURI());
//
//		setPropertyValue(entity, createAllNullPropertyValue());
//
//		E actualEntity = XmlUtil.read2(handler, getEntityClass(), readString(path));
//		E expectedEntity = entity;
//
//		check(expectedEntity, actualEntity);
//	}

	protected abstract String getEntriesEmptyXmlFileName();

//	protected abstract String getEntriesNullXmlFileName();

	protected abstract V createEmptyPropertyValue();

//	protected abstract V createAllNullPropertyValue();
}
