package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyEnumCodegen;

public class PropertyEnumCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyEnumCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueEnum", type = "java.time.Month") //
			} //
	)
	public static interface PropertyEnumCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEnumCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyEnumCodegen> type = PropertyEnumCodegen.class;

		checkFieldExists(type, "valueEnum", Modifier.PRIVATE, Month.class);

		checkMethodExists(type, "setValueEnum", Arrays.asList(Month.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueEnum", Collections.emptyList(), Modifier.PUBLIC, Month.class);
	}
}
