package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyByteArrayCodegen;

public class PropertyByteArrayCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyByteArrayCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByteArray", type = @Type(name = Type.BYTE_ARRAY)) //
			} //
	)
	public static interface PropertyByteArrayCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteArrayCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyByteArrayCodegen> type = PropertyByteArrayCodegen.class;

		checkFieldExists(type, "valueByteArray", Modifier.PRIVATE, byte[].class);

		checkMethodExists(type, "setValueByteArray", Arrays.asList(byte[].class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueByteArray", Collections.emptyList(), Modifier.PUBLIC, byte[].class);
	}
}
