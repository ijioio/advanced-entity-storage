package com.ijioio.aes.sandbox.test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.test.model.IdentityBarSerialization;
import com.ijioio.test.model.IdentityFooSerialization;
import com.ijioio.test.model.IdentitySerialization;

public class IdentitySerializationTest {

	@Entity( //
			name = IdentitySerializationPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueIdentityFoo", type = @Type(name = IdentityFooSerializationPrototype.NAME)), //
					@EntityProperty(name = "valueIdentityBar", type = @Type(name = IdentityBarSerializationPrototype.NAME)), //
					@EntityProperty(name = "valueIdentityFooList", type = @Type(name = Type.LIST), parameters = @Type(name = IdentityFooSerializationPrototype.NAME)), //
					@EntityProperty(name = "valueIdentityBarList", type = @Type(name = Type.LIST), parameters = @Type(name = IdentityBarSerializationPrototype.NAME)), //
					@EntityProperty(name = "valueIdentityMixList", type = @Type(name = Type.LIST), parameters = @Type(name = "java.lang.Object")), //
					@EntityProperty(name = "valueIdentityFooSet", type = @Type(name = Type.SET), parameters = @Type(name = IdentityFooSerializationPrototype.NAME)), //
					@EntityProperty(name = "valueIdentityBarSet", type = @Type(name = Type.SET), parameters = @Type(name = IdentityBarSerializationPrototype.NAME)), //
					@EntityProperty(name = "valueIdentityMixSet", type = @Type(name = Type.SET), parameters = @Type(name = "java.lang.Object")), //
					@EntityProperty(name = "valueIdentityFooMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = Type.STRING), @Type(name = IdentityFooSerializationPrototype.NAME) }), //
					@EntityProperty(name = "valueIdentityBarMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = Type.STRING), @Type(name = IdentityBarSerializationPrototype.NAME) }), //
					@EntityProperty(name = "valueIdentityMixMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = Type.STRING), @Type(name = "java.lang.Object") }), //
			} //
	)
	public static interface IdentitySerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.IdentitySerialization";
	}

	@Entity( //
			name = IdentityFooSerializationPrototype.NAME //
	)
	public static interface IdentityFooSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.IdentityFooSerialization";
	}

	@Entity( //
			name = IdentityBarSerializationPrototype.NAME //
	)
	public static interface IdentityBarSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.IdentityBarSerialization";
	}

	private Path path;

	private IdentitySerialization model;

	private IdentityFooSerialization fooModel;

	private IdentityBarSerialization barModel;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("identity-serialization.xml").toURI());

		fooModel = new IdentityFooSerialization();

		fooModel.setId("identity-foo-serialization");

		barModel = new IdentityBarSerialization();

		barModel.setId("identity-bar-serialization");

		List<IdentityFooSerialization> identityFooList = new ArrayList<>(Arrays.asList(fooModel, fooModel));
		List<IdentityBarSerialization> identityBarList = new ArrayList<>(Arrays.asList(barModel, barModel));
		List<Object> identityMixList = new ArrayList<>(Arrays.asList(fooModel, barModel));

		Set<IdentityFooSerialization> identityFooSet = new LinkedHashSet<>(Arrays.asList(fooModel));
		Set<IdentityBarSerialization> identityBarSet = new LinkedHashSet<>(Arrays.asList(barModel));
		Set<Object> identityMixSet = new LinkedHashSet<>(Arrays.asList(fooModel, barModel));

		Map<String, IdentityFooSerialization> identityFooMap = new LinkedHashMap<>();

		identityFooMap.put("key1", fooModel);
		identityFooMap.put("key2", fooModel);

		Map<String, IdentityBarSerialization> identityBarMap = new LinkedHashMap<>();

		identityBarMap.put("key1", barModel);
		identityBarMap.put("key2", barModel);

		Map<String, Object> identityMixMap = new LinkedHashMap<>();

		identityMixMap.put("key1", fooModel);
		identityMixMap.put("key2", barModel);

		model = new IdentitySerialization();

		model.setId("identity-serialization");
		model.setValueIdentityFoo(fooModel);
		model.setValueIdentityBar(barModel);
		model.setValueIdentityFooList(identityFooList);
		model.setValueIdentityBarList(identityBarList);
		model.setValueIdentityMixList(identityMixList);
		model.setValueIdentityFooSet(identityFooSet);
		model.setValueIdentityBarSet(identityBarSet);
		model.setValueIdentityMixSet(identityMixSet);
		model.setValueIdentityFooMap(identityFooMap);
		model.setValueIdentityBarMap(identityBarMap);
		model.setValueIdentityMixMap(identityMixMap);
	}

	@Test
	public void testWrite() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		String actual = XmlUtil.write(handler, model);
		String expected = Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n"));

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		IdentitySerialization actual = XmlUtil.read(handler, IdentitySerialization.class,
				Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n")));
		IdentitySerialization expected = model;

		Assertions.assertEquals(expected.getId(), actual.getId());
		Assertions.assertEquals(expected.getValueIdentityFoo(), actual.getValueIdentityFoo());
		Assertions.assertEquals(expected.getValueIdentityBar(), actual.getValueIdentityBar());
		Assertions.assertEquals(expected.getValueIdentityFooList(), actual.getValueIdentityFooList());
		Assertions.assertEquals(expected.getValueIdentityBarList(), actual.getValueIdentityBarList());
		Assertions.assertEquals(expected.getValueIdentityFooList(), actual.getValueIdentityFooList());
		Assertions.assertEquals(expected.getValueIdentityMixList(), actual.getValueIdentityMixList());
		Assertions.assertEquals(expected.getValueIdentityBarSet(), actual.getValueIdentityBarSet());
		Assertions.assertEquals(expected.getValueIdentityFooSet(), actual.getValueIdentityFooSet());
		Assertions.assertEquals(expected.getValueIdentityMixSet(), actual.getValueIdentityMixSet());
		Assertions.assertEquals(expected.getValueIdentityBarMap(), actual.getValueIdentityBarMap());
		Assertions.assertEquals(expected.getValueIdentityFooMap(), actual.getValueIdentityFooMap());
		Assertions.assertEquals(expected.getValueIdentityMixMap(), actual.getValueIdentityMixMap());

		//////////////////////////////////////////////////////////////////
		// Check all lists on object raw equal
		//////////////////////////////////////////////////////////////////

		Iterator<IdentityFooSerialization> valueIdentityFooListIterator = actual.getValueIdentityFooList().iterator();

		Assertions.assertTrue(valueIdentityFooListIterator.next() == actual.getValueIdentityFoo());
		Assertions.assertTrue(valueIdentityFooListIterator.next() == actual.getValueIdentityFoo());

		Iterator<IdentityBarSerialization> valueIdentityBarListIterator = actual.getValueIdentityBarList().iterator();

		Assertions.assertTrue(valueIdentityBarListIterator.next() == actual.getValueIdentityBar());
		Assertions.assertTrue(valueIdentityBarListIterator.next() == actual.getValueIdentityBar());

		Iterator<Object> valueIdentityMixListIterator = actual.getValueIdentityMixList().iterator();

		Assertions.assertTrue(valueIdentityMixListIterator.next() == actual.getValueIdentityFoo());
		Assertions.assertTrue(valueIdentityMixListIterator.next() == actual.getValueIdentityBar());

		//////////////////////////////////////////////////////////////////
		// Check all sets on object raw equal
		//////////////////////////////////////////////////////////////////

		Iterator<IdentityFooSerialization> valueIdentityFooSetIterator = actual.getValueIdentityFooSet().iterator();

		Assertions.assertTrue(valueIdentityFooSetIterator.next() == actual.getValueIdentityFoo());

		Iterator<IdentityBarSerialization> valueIdentityBarSetIterator = actual.getValueIdentityBarSet().iterator();

		Assertions.assertTrue(valueIdentityBarSetIterator.next() == actual.getValueIdentityBar());

		Iterator<Object> valueIdentityMixSetIterator = actual.getValueIdentityMixSet().iterator();

		Assertions.assertTrue(valueIdentityMixSetIterator.next() == actual.getValueIdentityFoo());
		Assertions.assertTrue(valueIdentityMixSetIterator.next() == actual.getValueIdentityBar());

		//////////////////////////////////////////////////////////////////
		// Check all maps on object raw equal
		//////////////////////////////////////////////////////////////////

		Iterator<Entry<String, IdentityFooSerialization>> valueIdentityFooMapIterator = actual.getValueIdentityFooMap()
				.entrySet().iterator();

		Assertions.assertTrue(valueIdentityFooMapIterator.next().getValue() == actual.getValueIdentityFoo());
		Assertions.assertTrue(valueIdentityFooMapIterator.next().getValue() == actual.getValueIdentityFoo());

		Iterator<Entry<String, IdentityBarSerialization>> valueIdentityBarMapIterator = actual.getValueIdentityBarMap()
				.entrySet().iterator();

		Assertions.assertTrue(valueIdentityBarMapIterator.next().getValue() == actual.getValueIdentityBar());
		Assertions.assertTrue(valueIdentityBarMapIterator.next().getValue() == actual.getValueIdentityBar());

		Iterator<Entry<String, Object>> valueIdentityMixMapIterator = actual.getValueIdentityMixMap().entrySet()
				.iterator();

		Assertions.assertTrue(valueIdentityMixMapIterator.next().getValue() == actual.getValueIdentityFoo());
		Assertions.assertTrue(valueIdentityMixMapIterator.next().getValue() == actual.getValueIdentityBar());
	}
}
