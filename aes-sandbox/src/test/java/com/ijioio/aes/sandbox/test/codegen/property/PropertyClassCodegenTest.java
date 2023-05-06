package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyClassCodegen;

public class PropertyClassCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyClassCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueClass", type = @Type(name = Type.CLASS), parameters = @Type(name = Type.STRING)) //
			} //
	)
	public static interface PropertyClassCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyClassCodegen> type = PropertyClassCodegen.class;

		checkFieldExists(type, "valueClass", Modifier.PRIVATE, Class.class);

		checkMethodExists(type, "setValueClass", Arrays.asList(Class.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueClass", Collections.emptyList(), Modifier.PUBLIC, Class.class);
	}
}
