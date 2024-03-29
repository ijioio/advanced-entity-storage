package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyStringCodegen;

public class PropertyStringCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyStringCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING) //
			} //
	)
	public static interface PropertyStringCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyStringCodegen> type = PropertyStringCodegen.class;

		checkFieldExists(type, "valueString", Modifier.PRIVATE, String.class);

		checkMethodExists(type, "setValueString", Arrays.asList(String.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueString", Collections.emptyList(), Modifier.PUBLIC, String.class);
	}
}
