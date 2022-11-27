package com.ijioio.aes.sandbox.test.codegen;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.test.model.MapFinalCodegen;

public class MapFinalCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = MapFinalCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueStringMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = Type.STRING), @Type(name = Type.STRING) }, attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueEnumMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = "java.time.Month"),
							@Type(name = "java.time.Month") }, attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueObjectMap", type = @Type(name = Type.MAP), parameters = {
							@Type(name = "java.lang.Object"),
							@Type(name = "java.lang.Object") }, attributes = Attribute.FINAL) //
			} //
	)
	public static interface MapFinalCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.MapFinalCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<MapFinalCodegen> type = MapFinalCodegen.class;

		checkFieldExists(type, "valueStringMap", Modifier.PRIVATE | Modifier.FINAL, Map.class);
		checkFieldExists(type, "valueEnumMap", Modifier.PRIVATE | Modifier.FINAL, Map.class);
		checkFieldExists(type, "valueObjectMap", Modifier.PRIVATE | Modifier.FINAL, Map.class);

		checkMethodNotExists(type, "setValueStringMap", Arrays.asList(Map.class));
		checkMethodExists(type, "getValueStringMap", Collections.emptyList(), Modifier.PUBLIC, Map.class);
		checkMethodNotExists(type, "setValueEnumMap", Arrays.asList(Map.class));
		checkMethodExists(type, "getValueEnumMap", Collections.emptyList(), Modifier.PUBLIC, Map.class);
		checkMethodNotExists(type, "setValueObjectMap", Arrays.asList(Map.class));
		checkMethodExists(type, "getValueObjectMap", Collections.emptyList(), Modifier.PUBLIC, Map.class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
