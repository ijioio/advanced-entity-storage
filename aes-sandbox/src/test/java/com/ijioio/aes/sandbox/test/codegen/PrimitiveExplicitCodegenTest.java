package com.ijioio.aes.sandbox.test.codegen;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.test.model.PrimitiveExplicitCodegen;

public class PrimitiveExplicitCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PrimitiveExplicitCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueBoolean", type = @Type(name = "boolean")), //
					@EntityProperty(name = "valueChar", type = @Type(name = "char")), //
					@EntityProperty(name = "valueByte", type = @Type(name = "byte")), //
					@EntityProperty(name = "valueShort", type = @Type(name = "short")), //
					@EntityProperty(name = "valueInt", type = @Type(name = "int")), //
					@EntityProperty(name = "valueLong", type = @Type(name = "long")), //
					@EntityProperty(name = "valueFloat", type = @Type(name = "float")), //
					@EntityProperty(name = "valueDouble", type = @Type(name = "double")) //
			} //
	)
	public static interface PrimitiveExplicitCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PrimitiveExplicitCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PrimitiveExplicitCodegen> type = PrimitiveExplicitCodegen.class;

		checkFieldExists(type, "valueBoolean", Modifier.PRIVATE, boolean.class);
		checkFieldExists(type, "valueChar", Modifier.PRIVATE, char.class);
		checkFieldExists(type, "valueByte", Modifier.PRIVATE, byte.class);
		checkFieldExists(type, "valueShort", Modifier.PRIVATE, short.class);
		checkFieldExists(type, "valueInt", Modifier.PRIVATE, int.class);
		checkFieldExists(type, "valueLong", Modifier.PRIVATE, long.class);
		checkFieldExists(type, "valueFloat", Modifier.PRIVATE, float.class);
		checkFieldExists(type, "valueDouble", Modifier.PRIVATE, double.class);

		checkMethodExists(type, "setValueBoolean", Arrays.asList(boolean.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "isValueBoolean", Collections.emptyList(), Modifier.PUBLIC, boolean.class);
		checkMethodExists(type, "setValueChar", Arrays.asList(char.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueChar", Collections.emptyList(), Modifier.PUBLIC, char.class);
		checkMethodExists(type, "setValueByte", Arrays.asList(byte.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueByte", Collections.emptyList(), Modifier.PUBLIC, byte.class);
		checkMethodExists(type, "setValueShort", Arrays.asList(short.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueShort", Collections.emptyList(), Modifier.PUBLIC, short.class);
		checkMethodExists(type, "setValueInt", Arrays.asList(int.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueInt", Collections.emptyList(), Modifier.PUBLIC, int.class);
		checkMethodExists(type, "setValueLong", Arrays.asList(long.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueLong", Collections.emptyList(), Modifier.PUBLIC, long.class);
		checkMethodExists(type, "setValueFloat", Arrays.asList(float.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueFloat", Collections.emptyList(), Modifier.PUBLIC, float.class);
		checkMethodExists(type, "setValueDouble", Arrays.asList(double.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueDouble", Collections.emptyList(), Modifier.PUBLIC, double.class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
