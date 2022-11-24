package com.ijioio.aes.sandbox.test.codegen;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.test.model.StringCodegen;

public class StringCodegenTest {

	@Entity( //
			name = StringCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = @Type(name = Type.STRING)) //
			} //
	)
	public static interface StringCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.StringCodegen";
	}

	private StringCodegen model;

	@BeforeEach
	public void before() throws Exception {

		model = new StringCodegen();
	}

	@Test
	public void testCodegen() throws Exception {

		System.out.println(Arrays.toString(model.getClass().getDeclaredMethods()));

		Assertions.assertEquals(4, model.getClass().getDeclaredMethods().length);

		Method valueStringSetMethod = model.getClass().getDeclaredMethod("setValueString", String.class);

		Assertions.assertNotNull(valueStringSetMethod);
		Assertions.assertEquals(Modifier.PUBLIC, valueStringSetMethod.getModifiers());
		Assertions.assertEquals(void.class, valueStringSetMethod.getReturnType());

		Method valueStringGetMethod = model.getClass().getDeclaredMethod("getValueString");

		Assertions.assertNotNull(valueStringGetMethod);
		Assertions.assertEquals(Modifier.PUBLIC, valueStringGetMethod.getModifiers());
		Assertions.assertEquals(String.class, valueStringGetMethod.getReturnType());

		Method getWritersMethod = model.getClass().getDeclaredMethod("getWriters", SerializationContext.class,
				SerializationHandler.class);

		Assertions.assertNotNull(getWritersMethod);
		Assertions.assertEquals(Modifier.PUBLIC, getWritersMethod.getModifiers());
		Assertions.assertEquals(Map.class, getWritersMethod.getReturnType());

		Method getReadersMethod = model.getClass().getDeclaredMethod("getReaders", SerializationContext.class,
				SerializationHandler.class);

		Assertions.assertNotNull(getReadersMethod);
		Assertions.assertEquals(Modifier.PUBLIC, getReadersMethod.getModifiers());
		Assertions.assertEquals(Map.class, getReadersMethod.getReturnType());
	}
}
