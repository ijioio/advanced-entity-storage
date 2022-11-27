package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyCollectionFinalCodegen;

public class PropertyCollectionFinalCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyCollectionFinalCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = @Type(name = Type.LIST), parameters = @Type(name = Type.STRING), attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueEnumList", type = @Type(name = Type.LIST), parameters = @Type(name = "java.time.Month"), attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueObjectList", type = @Type(name = Type.LIST), parameters = @Type(name = "java.lang.Object"), attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueStringSet", type = @Type(name = Type.SET), parameters = @Type(name = Type.STRING), attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueEnumSet", type = @Type(name = Type.SET), parameters = @Type(name = "java.time.Month"), attributes = Attribute.FINAL), //
					@EntityProperty(name = "valueObjectSet", type = @Type(name = Type.SET), parameters = @Type(name = "java.lang.Object"), attributes = Attribute.FINAL) //
			} //
	)
	public static interface PropertyCollectionFinalCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyCollectionFinalCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyCollectionFinalCodegen> type = PropertyCollectionFinalCodegen.class;

		checkFieldExists(type, "valueStringList", Modifier.PRIVATE | Modifier.FINAL, List.class);
		checkFieldExists(type, "valueEnumList", Modifier.PRIVATE | Modifier.FINAL, List.class);
		checkFieldExists(type, "valueObjectList", Modifier.PRIVATE | Modifier.FINAL, List.class);
		checkFieldExists(type, "valueStringSet", Modifier.PRIVATE | Modifier.FINAL, Set.class);
		checkFieldExists(type, "valueEnumSet", Modifier.PRIVATE | Modifier.FINAL, Set.class);
		checkFieldExists(type, "valueObjectSet", Modifier.PRIVATE | Modifier.FINAL, Set.class);

		checkMethodNotExists(type, "setValueStringList", Arrays.asList(List.class));
		checkMethodExists(type, "getValueStringList", Collections.emptyList(), Modifier.PUBLIC, List.class);
		checkMethodNotExists(type, "setValueEnumList", Arrays.asList(List.class));
		checkMethodExists(type, "getValueEnumList", Collections.emptyList(), Modifier.PUBLIC, List.class);
		checkMethodNotExists(type, "setValueObjectList", Arrays.asList(List.class));
		checkMethodExists(type, "getValueObjectList", Collections.emptyList(), Modifier.PUBLIC, List.class);
		checkMethodNotExists(type, "setValueStringSet", Arrays.asList(Set.class));
		checkMethodExists(type, "getValueStringSet", Collections.emptyList(), Modifier.PUBLIC, Set.class);
		checkMethodNotExists(type, "setValueEnumSet", Arrays.asList(Set.class));
		checkMethodExists(type, "getValueEnumSet", Collections.emptyList(), Modifier.PUBLIC, Set.class);
		checkMethodNotExists(type, "setValueObjectSet", Arrays.asList(Set.class));
		checkMethodExists(type, "getValueObjectSet", Collections.emptyList(), Modifier.PUBLIC, Set.class);
	}
}
