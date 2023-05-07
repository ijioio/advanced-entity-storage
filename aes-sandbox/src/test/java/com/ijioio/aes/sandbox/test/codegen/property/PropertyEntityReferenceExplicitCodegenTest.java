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
import com.ijioio.test.model.PropertyEntityReferenceExplicitCodegen;

public class PropertyEntityReferenceExplicitCodegenTest extends BaseCodegenTest {

	public static class Some extends BaseEntity {
		// Empty
	}

	@Entity( //
			name = PropertyEntityReferenceExplicitCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueEntityReference", type = @Type(name = "com.ijioio.aes.core.EntityReference"), parameters = @Type(name = "com.ijioio.aes.sandbox.test.codegen.property.PropertyEntityReferenceExplicitCodegenTest.Some")) //
			} //
	)
	public static interface PropertyEntityReferenceExplicitCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceExplicitCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyEntityReferenceExplicitCodegen> type = PropertyEntityReferenceExplicitCodegen.class;

		checkFieldExists(type, "valueEntityReference", Modifier.PRIVATE, EntityReference.class);

		checkMethodExists(type, "setValueEntityReference", Arrays.asList(EntityReference.class), Modifier.PUBLIC,
				void.class);
		checkMethodExists(type, "getValueEntityReference", Collections.emptyList(), Modifier.PUBLIC,
				EntityReference.class);
	}
}
