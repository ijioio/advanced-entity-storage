package com.ijioio.aes.sandbox.test.codegen.serialization;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.SerializationCodegen;

public class SerializationCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = SerializationCodegenPrototype.NAME //
	)
	public static interface SerializationCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.SerializationCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<SerializationCodegen> type = SerializationCodegen.class;

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
