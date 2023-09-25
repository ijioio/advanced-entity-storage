package com.ijioio.aes.sandbox.test.serialization.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.BaseIdentity;
import com.ijioio.aes.core.XSerializable;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlUtil;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.test.model.PropertyIdentitySerialization;

public class PropertyIdentitySerializationTest extends BaseSerializationTest {

	public static class IdentityFoo extends BaseIdentity implements XSerializable {

		public static final String NAME = "com.ijioio.aes.sandbox.test.serialization.property.PropertyIdentitySerializationTest.IdentityFoo";

		@Override
		public void write(SerializationContext context, SerializationHandler handler) throws SerializationException {
			handler.write(context, Collections.singletonMap("id", () -> handler.write(context, "id", getId())));
		}

		@Override
		public void read(SerializationContext context, SerializationHandler handler) throws SerializationException {
			handler.read(context, Collections.singletonMap("id", () -> setId(handler.read(context, getId()))));
		}
	}

	public static class IdentityBar extends BaseIdentity implements XSerializable {

		public static final String NAME = "com.ijioio.aes.sandbox.test.serialization.property.PropertyIdentitySerializationTest.IdentityBar";

		@Override
		public void write(SerializationContext context, SerializationHandler handler) throws SerializationException {
			handler.write(context, Collections.singletonMap("id", () -> handler.write(context, "id", getId())));
		}

		@Override
		public void read(SerializationContext context, SerializationHandler handler) throws SerializationException {
			handler.read(context, Collections.singletonMap("id", () -> setId(handler.read(context, getId()))));
		}
	}

	@Entity( //
			name = PropertyIdentitySerializationPrototype.NAME, //
			types = { //
					@Type(name = "List<IdentityFoo>", type = Type.LIST, parameters = @Parameter(name = IdentityFoo.NAME)), //
					@Type(name = "List<IdentityBar>", type = Type.LIST, parameters = @Parameter(name = IdentityBar.NAME)), //
					@Type(name = "List<Object>", type = Type.LIST, parameters = @Parameter(name = "java.lang.Object")), //
					@Type(name = "Set<IdentityFoo>", type = Type.SET, parameters = @Parameter(name = IdentityFoo.NAME)), //
					@Type(name = "Set<IdentityBar>", type = Type.SET, parameters = @Parameter(name = IdentityBar.NAME)), //
					@Type(name = "Set<Object>", type = Type.SET, parameters = @Parameter(name = "java.lang.Object")), //
					@Type(name = "Map<String, IdentityFoo>", type = Type.MAP, parameters = {
							@Parameter(name = Type.STRING), @Parameter(name = IdentityFoo.NAME) }), //
					@Type(name = "Map<String, IdentityBar>", type = Type.MAP, parameters = {
							@Parameter(name = Type.STRING), @Parameter(name = IdentityBar.NAME) }), //
					@Type(name = "Map<String, Object>", type = Type.MAP, parameters = { @Parameter(name = Type.STRING),
							@Parameter(name = "java.lang.Object") }) //
			}, //
			properties = { //
					@EntityProperty(name = "valueIdentityFoo", type = IdentityFoo.NAME), //
					@EntityProperty(name = "valueIdentityBar", type = IdentityBar.NAME), //
					@EntityProperty(name = "valueIdentityFooList", type = "List<IdentityFoo>"), //
					@EntityProperty(name = "valueIdentityBarList", type = "List<IdentityBar>"), //
					@EntityProperty(name = "valueIdentityMixList", type = "List<Object>"), //
					@EntityProperty(name = "valueIdentityFooSet", type = "Set<IdentityFoo>"), //
					@EntityProperty(name = "valueIdentityBarSet", type = "Set<IdentityBar>"), //
					@EntityProperty(name = "valueIdentityMixSet", type = "Set<Object>"), //
					@EntityProperty(name = "valueIdentityFooMap", type = "Map<String, IdentityFoo>"), //
					@EntityProperty(name = "valueIdentityBarMap", type = "Map<String, IdentityBar>"), //
					@EntityProperty(name = "valueIdentityMixMap", type = "Map<String, Object>") //
			} //
	)
	public static interface PropertyIdentitySerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyIdentitySerialization";
	}

	private Path path;

	private PropertyIdentitySerialization model;

	private IdentityFoo foo;

	private IdentityBar bar;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-identity-serialization.xml").toURI());

		foo = new IdentityFoo();

		foo.setId("identity-foo");

		bar = new IdentityBar();

		bar.setId("identity-bar");

		List<IdentityFoo> identityFooList = new ArrayList<>(Arrays.asList(foo, foo));
		List<IdentityBar> identityBarList = new ArrayList<>(Arrays.asList(bar, bar));
		List<Object> identityMixList = new ArrayList<>(Arrays.asList(foo, bar));

		Set<IdentityFoo> identityFooSet = new LinkedHashSet<>(Arrays.asList(foo));
		Set<IdentityBar> identityBarSet = new LinkedHashSet<>(Arrays.asList(bar));
		Set<Object> identityMixSet = new LinkedHashSet<>(Arrays.asList(foo, bar));

		Map<String, IdentityFoo> identityFooMap = new LinkedHashMap<>();

		identityFooMap.put("key1", foo);
		identityFooMap.put("key2", foo);

		Map<String, IdentityBar> identityBarMap = new LinkedHashMap<>();

		identityBarMap.put("key1", bar);
		identityBarMap.put("key2", bar);

		Map<String, Object> identityMixMap = new LinkedHashMap<>();

		identityMixMap.put("key1", foo);
		identityMixMap.put("key2", bar);

		model = new PropertyIdentitySerialization();

		model.setId("property-identity-serialization");
		model.setValueIdentityFoo(foo);
		model.setValueIdentityBar(bar);
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
		String expected = readString(path);

		Assertions.assertEquals(expected, actual);
	}

	@Test
	public void testRead() throws Exception {

		XmlSerializationHandler handler = new XmlSerializationHandler();

		PropertyIdentitySerialization actual = XmlUtil.read(handler, PropertyIdentitySerialization.class,
				readString(path));
		PropertyIdentitySerialization expected = model;

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

		Iterator<IdentityFoo> valueIdentityFooListIterator = actual.getValueIdentityFooList().iterator();

		Assertions.assertTrue(valueIdentityFooListIterator.next() == actual.getValueIdentityFoo());
		Assertions.assertTrue(valueIdentityFooListIterator.next() == actual.getValueIdentityFoo());

		Iterator<IdentityBar> valueIdentityBarListIterator = actual.getValueIdentityBarList().iterator();

		Assertions.assertTrue(valueIdentityBarListIterator.next() == actual.getValueIdentityBar());
		Assertions.assertTrue(valueIdentityBarListIterator.next() == actual.getValueIdentityBar());

		Iterator<Object> valueIdentityMixListIterator = actual.getValueIdentityMixList().iterator();

		Assertions.assertTrue(valueIdentityMixListIterator.next() == actual.getValueIdentityFoo());
		Assertions.assertTrue(valueIdentityMixListIterator.next() == actual.getValueIdentityBar());

		//////////////////////////////////////////////////////////////////
		// Check all sets on object raw equal
		//////////////////////////////////////////////////////////////////

		Iterator<IdentityFoo> valueIdentityFooSetIterator = actual.getValueIdentityFooSet().iterator();

		Assertions.assertTrue(valueIdentityFooSetIterator.next() == actual.getValueIdentityFoo());

		Iterator<IdentityBar> valueIdentityBarSetIterator = actual.getValueIdentityBarSet().iterator();

		Assertions.assertTrue(valueIdentityBarSetIterator.next() == actual.getValueIdentityBar());

		Iterator<Object> valueIdentityMixSetIterator = actual.getValueIdentityMixSet().iterator();

		Assertions.assertTrue(valueIdentityMixSetIterator.next() == actual.getValueIdentityFoo());
		Assertions.assertTrue(valueIdentityMixSetIterator.next() == actual.getValueIdentityBar());

		//////////////////////////////////////////////////////////////////
		// Check all maps on object raw equal
		//////////////////////////////////////////////////////////////////

		Iterator<Entry<String, IdentityFoo>> valueIdentityFooMapIterator = actual.getValueIdentityFooMap().entrySet()
				.iterator();

		Assertions.assertTrue(valueIdentityFooMapIterator.next().getValue() == actual.getValueIdentityFoo());
		Assertions.assertTrue(valueIdentityFooMapIterator.next().getValue() == actual.getValueIdentityFoo());

		Iterator<Entry<String, IdentityBar>> valueIdentityBarMapIterator = actual.getValueIdentityBarMap().entrySet()
				.iterator();

		Assertions.assertTrue(valueIdentityBarMapIterator.next().getValue() == actual.getValueIdentityBar());
		Assertions.assertTrue(valueIdentityBarMapIterator.next().getValue() == actual.getValueIdentityBar());

		Iterator<Entry<String, Object>> valueIdentityMixMapIterator = actual.getValueIdentityMixMap().entrySet()
				.iterator();

		Assertions.assertTrue(valueIdentityMixMapIterator.next().getValue() == actual.getValueIdentityFoo());
		Assertions.assertTrue(valueIdentityMixMapIterator.next().getValue() == actual.getValueIdentityBar());
	}
}
