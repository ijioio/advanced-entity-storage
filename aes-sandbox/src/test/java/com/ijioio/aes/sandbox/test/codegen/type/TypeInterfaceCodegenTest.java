package com.ijioio.aes.sandbox.test.codegen.type;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.TypeInterfaceCodegen;

public class TypeInterfaceCodegenTest extends BaseCodegenTest {

	public static interface Foo {
		// Empty
	}

	public static interface Bar {
		// Empty
	}

	@Entity( //
			name = TypeInterfaceCodegenPrototype.NAME, //
			attributes = Attribute.FINAL, //
			interfaces = { Foo.class, Bar.class } //
	)
	public static interface TypeInterfaceCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.TypeInterfaceCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<TypeInterfaceCodegen> type = TypeInterfaceCodegen.class;

		checkType(type, Modifier.PUBLIC + Modifier.FINAL, TypeInterfaceCodegenPrototype.NAME, BaseEntity.class,
				Arrays.asList(Foo.class, Bar.class));
	}
}
