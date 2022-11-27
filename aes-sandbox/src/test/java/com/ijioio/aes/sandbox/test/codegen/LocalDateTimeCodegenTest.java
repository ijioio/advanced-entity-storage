package com.ijioio.aes.sandbox.test.codegen;

import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.test.model.LocalDateTimeCodegen;

public class LocalDateTimeCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = LocalDateTimeCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDateTime", type = @Type(name = Type.LOCAL_DATE_TIME)) //
			} //
	)
	public static interface LocalDateTimeCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.LocalDateTimeCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<LocalDateTimeCodegen> type = LocalDateTimeCodegen.class;

		checkFieldExists(type, "valueLocalDateTime", Modifier.PRIVATE, LocalDateTime.class);

		checkMethodExists(type, "setValueLocalDateTime", Arrays.asList(LocalDateTime.class), Modifier.PUBLIC,
				void.class);
		checkMethodExists(type, "getValueLocalDateTime", Collections.emptyList(), Modifier.PUBLIC, LocalDateTime.class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
