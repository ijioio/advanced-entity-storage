package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyCollectionExplicitCodegen;

public class PropertyCollectionExplicitCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyCollectionExplicitCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = @Type(name = "java.util.List"), parameters = @Type(name = Type.STRING)), //
					@EntityProperty(name = "valueEnumList", type = @Type(name = "java.util.List"), parameters = @Type(name = "java.time.Month")), //
					@EntityProperty(name = "valueObjectList", type = @Type(name = "java.util.List"), parameters = @Type(name = "java.lang.Object")), //
					@EntityProperty(name = "valueStringSet", type = @Type(name = "java.util.Set"), parameters = @Type(name = Type.STRING)), //
					@EntityProperty(name = "valueEnumSet", type = @Type(name = "java.util.Set"), parameters = @Type(name = "java.time.Month")), //
					@EntityProperty(name = "valueObjectSet", type = @Type(name = "java.util.Set"), parameters = @Type(name = "java.lang.Object")) //
			} //
	)
	public static interface PropertyCollectionExplicitCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyCollectionExplicitCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyCollectionExplicitCodegen> type = PropertyCollectionExplicitCodegen.class;

		checkFieldExists(type, "valueStringList", Modifier.PRIVATE, List.class);
		checkFieldExists(type, "valueEnumList", Modifier.PRIVATE, List.class);
		checkFieldExists(type, "valueObjectList", Modifier.PRIVATE, List.class);
		checkFieldExists(type, "valueStringSet", Modifier.PRIVATE, Set.class);
		checkFieldExists(type, "valueEnumSet", Modifier.PRIVATE, Set.class);
		checkFieldExists(type, "valueObjectSet", Modifier.PRIVATE, Set.class);

		checkMethodExists(type, "setValueStringList", Arrays.asList(List.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueStringList", Collections.emptyList(), Modifier.PUBLIC, List.class);
		checkMethodExists(type, "setValueEnumList", Arrays.asList(List.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueEnumList", Collections.emptyList(), Modifier.PUBLIC, List.class);
		checkMethodExists(type, "setValueObjectList", Arrays.asList(List.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueObjectList", Collections.emptyList(), Modifier.PUBLIC, List.class);
		checkMethodExists(type, "setValueStringSet", Arrays.asList(Set.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueStringSet", Collections.emptyList(), Modifier.PUBLIC, Set.class);
		checkMethodExists(type, "setValueEnumSet", Arrays.asList(Set.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueEnumSet", Collections.emptyList(), Modifier.PUBLIC, Set.class);
		checkMethodExists(type, "setValueObjectSet", Arrays.asList(Set.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueObjectSet", Collections.emptyList(), Modifier.PUBLIC, Set.class);
	}
}
