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
import com.ijioio.test.model.LocalTimeCodegen;

public class LocalTimeCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = LocalTimeCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalTime", type = @Type(name = Type.LOCAL_TIME)) //
			} //
	)
	public static interface LocalTimeCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.LocalTimeCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<LocalTimeCodegen> type = LocalTimeCodegen.class;

		checkFieldExists(type, "valueLocalTime", Modifier.PRIVATE, LocalTime.class);

		checkMethodExists(type, "setValueLocalTime", Arrays.asList(LocalTime.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueLocalTime", Collections.emptyList(), Modifier.PUBLIC, LocalTime.class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
