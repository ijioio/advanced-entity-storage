package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyLocalTimeExplicitCodegen;

public class PropertyLocalTimeExplicitCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyLocalTimeExplicitCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalTime", type = "java.time.LocalTime") //
			} //
	)
	public static interface PropertyLocalTimeExplicitCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLocalTimeExplicitCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyLocalTimeExplicitCodegen> type = PropertyLocalTimeExplicitCodegen.class;

		checkFieldExists(type, "valueLocalTime", Modifier.PRIVATE, LocalTime.class);

		checkMethodExists(type, "setValueLocalTime", Arrays.asList(LocalTime.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueLocalTime", Collections.emptyList(), Modifier.PUBLIC, LocalTime.class);
	}
}
