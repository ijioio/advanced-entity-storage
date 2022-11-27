package com.ijioio.aes.sandbox.test.codegen;

import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.test.model.LocalDateCodegen;

public class LocalDateCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = LocalDateCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDate", type = @Type(name = Type.LOCAL_DATE)) //
			} //
	)
	public static interface LocalDateCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.LocalDateCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<LocalDateCodegen> type = LocalDateCodegen.class;

		checkFieldExists(type, "valueLocalDate", Modifier.PRIVATE, LocalDate.class);

		checkMethodExists(type, "setValueLocalDate", Arrays.asList(LocalDate.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueLocalDate", Collections.emptyList(), Modifier.PUBLIC, LocalDate.class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
