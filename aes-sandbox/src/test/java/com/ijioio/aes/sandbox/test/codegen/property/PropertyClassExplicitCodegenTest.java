package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyClassExplicitCodegen;

public class PropertyClassExplicitCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyClassExplicitCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueClass", type = @Type(name = "java.lang.Class"), parameters = @Type(name = Type.STRING)) //
			} //
	)
	public static interface PropertyClassExplicitCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassExplicitCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyClassExplicitCodegen> type = PropertyClassExplicitCodegen.class;

		checkFieldExists(type, "valueClass", Modifier.PRIVATE, Class.class);

		checkMethodExists(type, "setValueClass", Arrays.asList(Class.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueClass", Collections.emptyList(), Modifier.PUBLIC, Class.class);
	}
}
