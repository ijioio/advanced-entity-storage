package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyMapCodegen;

public class PropertyMapCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyMapCodegenPrototype.NAME, //
			types = { //
					@Type(name = "Map<String, String>", type = Type.MAP, parameters = { Type.STRING, Type.STRING }), //
					@Type(name = "Map<Month, Month>", type = Type.MAP, parameters = { "java.time.Month",
							"java.time.Month" }), //
					@Type(name = "Map<Object, Object>", type = Type.MAP, parameters = { "java.lang.Object",
							"java.lang.Object" }) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringMap", type = "Map<String, String>"), //
					@EntityProperty(name = "valueEnumMap", type = "Map<Month, Month>"), //
					@EntityProperty(name = "valueObjectMap", type = "Map<Object, Object>") //
			} //
	)
	public static interface PropertyMapCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyMapCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyMapCodegen> type = PropertyMapCodegen.class;

		checkFieldExists(type, "valueStringMap", Modifier.PRIVATE, Map.class);
		checkFieldExists(type, "valueEnumMap", Modifier.PRIVATE, Map.class);
		checkFieldExists(type, "valueObjectMap", Modifier.PRIVATE, Map.class);

		checkMethodExists(type, "setValueStringMap", Arrays.asList(Map.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueStringMap", Collections.emptyList(), Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "setValueEnumMap", Arrays.asList(Map.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueEnumMap", Collections.emptyList(), Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "setValueObjectMap", Arrays.asList(Map.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueObjectMap", Collections.emptyList(), Modifier.PUBLIC, Map.class);
	}
}
