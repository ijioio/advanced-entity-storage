package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyByteArrayExplicitCodegen;

public class PropertyByteArrayExplicitCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyByteArrayExplicitCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByteArray", type = "[B") //
			} //
	)
	public static interface PropertyByteArrayExplicitCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteArrayExplicitCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyByteArrayExplicitCodegen> type = PropertyByteArrayExplicitCodegen.class;

		checkFieldExists(type, "valueByteArray", Modifier.PRIVATE, byte[].class);

		checkMethodExists(type, "setValueByteArray", Arrays.asList(byte[].class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueByteArray", Collections.emptyList(), Modifier.PUBLIC, byte[].class);
	}
}
