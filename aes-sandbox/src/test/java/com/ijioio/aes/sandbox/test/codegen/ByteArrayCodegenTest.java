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
import com.ijioio.test.model.ByteArrayCodegen;

public class ByteArrayCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = ByteArrayCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByteArray", type = @Type(name = Type.BYTE_ARRAY)) //
			} //
	)
	public static interface ByteArrayCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.ByteArrayCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<ByteArrayCodegen> type = ByteArrayCodegen.class;

		checkFieldExists(type, "valueByteArray", Modifier.PRIVATE, byte[].class);

		checkMethodExists(type, "setValueByteArray", Arrays.asList(byte[].class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueByteArray", Collections.emptyList(), Modifier.PUBLIC, byte[].class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
