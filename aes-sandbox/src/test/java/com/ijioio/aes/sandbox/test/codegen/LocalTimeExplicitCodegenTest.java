package com.ijioio.aes.sandbox.test.codegen;

import java.lang.reflect.Modifier;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.test.model.LocalTimeExplicitCodegen;

public class LocalTimeExplicitCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = LocalTimeExplicitCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalTime", type = @Type(name = "java.time.LocalTime")) //
			} //
	)
	public static interface LocalTimeExplicitCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.LocalTimeExplicitCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<LocalTimeExplicitCodegen> type = LocalTimeExplicitCodegen.class;

		checkFieldExists(type, "valueLocalTime", Modifier.PRIVATE, LocalTime.class);

		checkMethodExists(type, "setValueLocalTime", Arrays.asList(LocalTime.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueLocalTime", Collections.emptyList(), Modifier.PUBLIC, LocalTime.class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
