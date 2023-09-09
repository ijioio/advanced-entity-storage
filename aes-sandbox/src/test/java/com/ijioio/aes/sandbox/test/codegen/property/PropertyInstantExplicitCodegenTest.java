package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyInstantExplicitCodegen;

public class PropertyInstantExplicitCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyInstantExplicitCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueInstant", type = "java.time.Instant") //
			} //
	)
	public static interface PropertyInstantExplicitCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyInstantExplicitCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyInstantExplicitCodegen> type = PropertyInstantExplicitCodegen.class;

		checkFieldExists(type, "valueInstant", Modifier.PRIVATE, Instant.class);

		checkMethodExists(type, "setValueInstant", Arrays.asList(Instant.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueInstant", Collections.emptyList(), Modifier.PUBLIC, Instant.class);
	}
}
