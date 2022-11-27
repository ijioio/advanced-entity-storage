package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyPrimitiveCodegen;

public class PropertyPrimitiveCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyPrimitiveCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueBoolean", type = @Type(name = Type.BOOLEAN)), //
					@EntityProperty(name = "valueChar", type = @Type(name = Type.CHAR)), //
					@EntityProperty(name = "valueByte", type = @Type(name = Type.BYTE)), //
					@EntityProperty(name = "valueShort", type = @Type(name = Type.SHORT)), //
					@EntityProperty(name = "valueInt", type = @Type(name = Type.INT)), //
					@EntityProperty(name = "valueLong", type = @Type(name = Type.LONG)), //
					@EntityProperty(name = "valueFloat", type = @Type(name = Type.FLOAT)), //
					@EntityProperty(name = "valueDouble", type = @Type(name = Type.DOUBLE)) //
			} //
	)
	public static interface PropertyPrimitiveCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyPrimitiveCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyPrimitiveCodegen> type = PropertyPrimitiveCodegen.class;

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
	}
}
