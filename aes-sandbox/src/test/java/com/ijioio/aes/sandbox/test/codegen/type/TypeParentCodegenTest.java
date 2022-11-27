package com.ijioio.aes.sandbox.test.codegen.type;

import java.lang.reflect.Modifier;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.TypeParentCodegen;

public class TypeParentCodegenTest extends BaseCodegenTest {

	public static class Parent extends BaseEntity {
		// Empty
	}

	@Entity( //
			name = TypeParentCodegenPrototype.NAME, //
			parent = "com.ijioio.aes.sandbox.test.codegen.type.TypeParentCodegenTest.Parent" //
	)
	public static interface TypeParentCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.TypeParentCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<TypeParentCodegen> type = TypeParentCodegen.class;

		checkType(type, Modifier.PUBLIC, TypeParentCodegenPrototype.NAME, Parent.class, Collections.emptyList());
	}
}
