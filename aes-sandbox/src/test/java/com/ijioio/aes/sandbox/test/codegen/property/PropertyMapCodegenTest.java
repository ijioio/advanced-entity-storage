package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyMapCodegen;

public class PropertyMapCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyMapCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueStringMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = Type.STRING), @Type(name = Type.STRING) }), //
					@EntityProperty(name = "valueEnumMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = "java.time.Month"), @Type(name = "java.time.Month") }), //
					@EntityProperty(name = "valueObjectMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = "java.lang.Object"), @Type(name = "java.lang.Object") }) //
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

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
