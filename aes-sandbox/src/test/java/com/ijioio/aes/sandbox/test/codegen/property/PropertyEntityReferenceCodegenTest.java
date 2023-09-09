package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyEntityReferenceCodegen;

public class PropertyEntityReferenceCodegenTest extends BaseCodegenTest {

	public static class Some extends BaseEntity {
		// Empty
	}

	@Entity( //
			name = PropertyEntityReferenceCodegenPrototype.NAME, //
			types = { //
					@Type(name = "EntityReference<Some>", type = Type.ENTITY_REFERENCE, parameters = "com.ijioio.aes.sandbox.test.codegen.property.PropertyEntityReferenceCodegenTest.Some") //
			}, //
			properties = { //
					@EntityProperty(name = "valueEntityReference", type = "EntityReference<Some>") //
			} //
	)
	public static interface PropertyEntityReferenceCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyEntityReferenceCodegen> type = PropertyEntityReferenceCodegen.class;

		checkFieldExists(type, "valueEntityReference", Modifier.PRIVATE, EntityReference.class);

		checkMethodExists(type, "setValueEntityReference", Arrays.asList(EntityReference.class), Modifier.PUBLIC,
				void.class);
		checkMethodExists(type, "getValueEntityReference", Collections.emptyList(), Modifier.PUBLIC,
				EntityReference.class);
	}
}
