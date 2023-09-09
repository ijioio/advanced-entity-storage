package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyLocalDateExplicitCodegen;

public class PropertyLocalDateExplicitCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyLocalDateExplicitCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalDate", type = "java.time.LocalDate") //
			} //
	)
	public static interface PropertyLocalDateExplicitCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLocalDateExplicitCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyLocalDateExplicitCodegen> type = PropertyLocalDateExplicitCodegen.class;

		checkFieldExists(type, "valueLocalDate", Modifier.PRIVATE, LocalDate.class);

		checkMethodExists(type, "setValueLocalDate", Arrays.asList(LocalDate.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueLocalDate", Collections.emptyList(), Modifier.PUBLIC, LocalDate.class);
	}
}
