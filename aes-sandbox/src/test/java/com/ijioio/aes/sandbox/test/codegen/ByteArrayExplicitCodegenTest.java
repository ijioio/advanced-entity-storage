package com.ijioio.aes.sandbox.test.codegen;

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
import com.ijioio.test.model.ByteArrayExplicitCodegen;

public class ByteArrayExplicitCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = ByteArrayExplicitCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByteArray", type = @Type(name = "[B")) //
			} //
	)
	public static interface ByteArrayExplicitCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.ByteArrayExplicitCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<ByteArrayExplicitCodegen> type = ByteArrayExplicitCodegen.class;

		checkFieldExists(type, "valueByteArray", Modifier.PRIVATE, byte[].class);

		checkMethodExists(type, "setValueByteArray", Arrays.asList(byte[].class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueByteArray", Collections.emptyList(), Modifier.PUBLIC, byte[].class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
