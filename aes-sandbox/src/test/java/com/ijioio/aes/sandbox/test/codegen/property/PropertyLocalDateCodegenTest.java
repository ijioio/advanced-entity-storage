package com.ijioio.aes.sandbox.test.codegen.property;

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
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyLocalDateCodegen;

public class PropertyLocalDateCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyLocalDateCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDate", type = @Type(name = Type.LOCAL_DATE)) //
			} //
	)
	public static interface PropertyLocalDateCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLocalDateCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyLocalDateCodegen> type = PropertyLocalDateCodegen.class;

		checkFieldExists(type, "valueLocalDate", Modifier.PRIVATE, LocalDate.class);

		checkMethodExists(type, "setValueLocalDate", Arrays.asList(LocalDate.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueLocalDate", Collections.emptyList(), Modifier.PUBLIC, LocalDate.class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
