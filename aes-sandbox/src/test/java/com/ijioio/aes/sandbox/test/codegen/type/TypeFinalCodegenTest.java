package com.ijioio.aes.sandbox.test.codegen.type;

import java.lang.reflect.Modifier;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.TypeFinalCodegen;

public class TypeFinalCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = TypeFinalCodegenPrototype.NAME, //
			attributes = Attribute.FINAL //
	)
	public static interface TypeFinalCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.TypeFinalCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<TypeFinalCodegen> type = TypeFinalCodegen.class;

		checkType(type, Modifier.PUBLIC + Modifier.FINAL, TypeFinalCodegenPrototype.NAME, BaseEntity.class,
				Collections.emptyList());
	}
}
