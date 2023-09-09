package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyMapFinalCodegen;

public class PropertyMapFinalCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyMapFinalCodegenPrototype.NAME, //
			types = { //
					@Type(name = "Map<String, String>", type = Type.MAP, parameters = { Type.STRING, Type.STRING }), //
					@Type(name = "Map<Month, Month>", type = Type.MAP, parameters = { "java.time.Month",
							"java.time.Month" }), //
					@Type(name = "Map<Object, Object>", type = Type.MAP, parameters = { "java.lang.Object",
							"java.lang.Object" }) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringMap", type = "Map<String, String>", attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueEnumMap", type = "Map<Month, Month>", attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueObjectMap", type = "Map<Object, Object>", attributes = Attribute.FINAL) //
			} //
	)
	public static interface PropertyMapFinalCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyMapFinalCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyMapFinalCodegen> type = PropertyMapFinalCodegen.class;

		checkFieldExists(type, "valueStringMap", Modifier.PRIVATE | Modifier.FINAL, Map.class);
		checkFieldExists(type, "valueEnumMap", Modifier.PRIVATE | Modifier.FINAL, Map.class);
		checkFieldExists(type, "valueObjectMap", Modifier.PRIVATE | Modifier.FINAL, Map.class);

		checkMethodNotExists(type, "setValueStringMap", Arrays.asList(Map.class));
		checkMethodExists(type, "getValueStringMap", Collections.emptyList(), Modifier.PUBLIC, Map.class);
		checkMethodNotExists(type, "setValueEnumMap", Arrays.asList(Map.class));
		checkMethodExists(type, "getValueEnumMap", Collections.emptyList(), Modifier.PUBLIC, Map.class);
		checkMethodNotExists(type, "setValueObjectMap", Arrays.asList(Map.class));
		checkMethodExists(type, "getValueObjectMap", Collections.emptyList(), Modifier.PUBLIC, Map.class);
	}
}
