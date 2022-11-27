package com.ijioio.aes.sandbox.test.codegen.type;

import java.lang.reflect.Modifier;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.TypeCodegen;

public class TypeCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = TypeCodegenPrototype.NAME //
	)
	public static interface TypeCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.TypeCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<TypeCodegen> type = TypeCodegen.class;

		checkType(type, Modifier.PUBLIC, TypeCodegenPrototype.NAME, BaseEntity.class, Collections.emptyList());
	}
}
