package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyCollectionCodegen;

public class PropertyCollectionCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyCollectionCodegenPrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = Type.LIST, parameters = @Parameter(name = Type.STRING)), //
					@Type(name = "List<Month>", type = Type.LIST, parameters = @Parameter(name = "java.time.Month")), //
					@Type(name = "List<Object>", type = Type.LIST, parameters = @Parameter(name = "java.lang.Object")), //
					@Type(name = "Set<String>", type = Type.SET, parameters = @Parameter(name = Type.STRING)), //
					@Type(name = "Set<Month>", type = Type.SET, parameters = @Parameter(name = "java.time.Month")), //
					@Type(name = "Set<Object>", type = Type.SET, parameters = @Parameter(name = "java.lang.Object")) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = "List<String>"), //
					@EntityProperty(name = "valueEnumList", type = "List<Month>"), //
					@EntityProperty(name = "valueObjectList", type = "List<Object>"), //
					@EntityProperty(name = "valueStringSet", type = "Set<String>"), //
					@EntityProperty(name = "valueEnumSet", type = "Set<Month>"), //
					@EntityProperty(name = "valueObjectSet", type = "Set<Object>") //
			} //
	)
	public static interface PropertyCollectionCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyCollectionCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyCollectionCodegen> type = PropertyCollectionCodegen.class;

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
