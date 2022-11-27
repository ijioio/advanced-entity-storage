package com.ijioio.aes.sandbox.test.codegen;

import java.lang.reflect.Modifier;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.test.model.EnumCodegen;

public class EnumCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = EnumCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueEnum", type = @Type(name = "java.time.Month")) //
			} //
	)
	public static interface EnumCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.EnumCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<EnumCodegen> type = EnumCodegen.class;

		checkFieldExists(type, "valueEnum", Modifier.PRIVATE, Month.class);

		checkMethodExists(type, "setValueEnum", Arrays.asList(Month.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueEnum", Collections.emptyList(), Modifier.PUBLIC, Month.class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
