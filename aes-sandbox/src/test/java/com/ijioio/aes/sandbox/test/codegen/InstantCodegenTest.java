package com.ijioio.aes.sandbox.test.codegen;

import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.test.model.InstantCodegen;

public class InstantCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = InstantCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueInstant", type = @Type(name = Type.INSTANT)) //
			} //
	)
	public static interface InstantCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.InstantCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<InstantCodegen> type = InstantCodegen.class;

		checkFieldExists(type, "valueInstant", Modifier.PRIVATE, Instant.class);

		checkMethodExists(type, "setValueInstant", Arrays.asList(Instant.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueInstant", Collections.emptyList(), Modifier.PUBLIC, Instant.class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
