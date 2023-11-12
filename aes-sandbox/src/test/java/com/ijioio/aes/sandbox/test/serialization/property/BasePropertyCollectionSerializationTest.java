package com.ijioio.aes.sandbox.test.serialization.property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ijioio.aes.core.Entity;

public abstract class BasePropertyCollectionSerializationTest<E extends Entity, V extends Collection<I>, I>
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

//	@Test
//	public void testWriteElementsEmpty() throws Exception {
//
//		Path path = Paths.get(getClass().getClassLoader()
//				.getResource(String.format("serialization/entity/property/%s", getXmlFileName(ElementType.EMPTY)))
//				.toURI());
//
//		setPropertyValue(entity, createEmptyPropertyValue());
//
//		String actualXml = XmlUtil.write2(handler, entity);
//		String expectedXml = readString(path);
//
//		Files.write(Paths.get("c:/deleteme/entity.xml"), actualXml.getBytes(StandardCharsets.UTF_8));
//
//		Assertions.assertEquals(expectedXml, actualXml);
//	}
//
//	@Test
//	public void testWriteElementsNull() throws Exception {
//
//		Path path = Paths.get(getClass().getClassLoader()
//				.getResource(String.format("serialization/entity/property/%s", getXmlFileName(ElementType.NULL)))
//				.toURI());
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
//
//	@Test
//	public void testReadElementsEmpty() throws Exception {
//
//		Path path = Paths.get(getClass().getClassLoader()
//				.getResource(String.format("serialization/entity/property/%s", getXmlFileName(ElementType.EMPTY)))
//				.toURI());
//
//		setPropertyValue(entity, createEmptyPropertyValue());
//
//		E actualEntity = XmlUtil.read2(handler, getEntityClass(), readString(path));
//		E expectedEntity = entity;
//
//		check(expectedEntity, actualEntity);
//	}
//
//	@Test
//	public void testReadElementsNull() throws Exception {
//
//		Path path = Paths.get(getClass().getClassLoader()
//				.getResource(String.format("serialization/entity/property/%s", getXmlFileName(ElementType.NULL)))
//				.toURI());
//
//		setPropertyValue(entity, createAllNullPropertyValue());
//
//		E actualEntity = XmlUtil.read2(handler, getEntityClass(), readString(path));
//		E expectedEntity = entity;
//
//		check(expectedEntity, actualEntity);
//	}
//
//	protected abstract String getXmlFileName(ElementType type);
//
//	protected abstract V createEmptyPropertyValue();
//
//	protected abstract V createAllNullPropertyValue();
//
//	public static enum ElementType {
//
//		EMPTY, NULL;
//	}
}