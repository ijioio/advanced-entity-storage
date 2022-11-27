package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyLocalTimeCodegen;

public class PropertyLocalTimeCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = PropertyLocalTimeCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueLocalTime", type = @Type(name = Type.LOCAL_TIME)) //
			} //
	)
	public static interface PropertyLocalTimeCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyLocalTimeCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyLocalTimeCodegen> type = PropertyLocalTimeCodegen.class;

		checkFieldExists(type, "valueLocalTime", Modifier.PRIVATE, LocalTime.class);

		checkMethodExists(type, "setValueLocalTime", Arrays.asList(LocalTime.class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueLocalTime", Collections.emptyList(), Modifier.PUBLIC, LocalTime.class);

		checkMethodExists(type, "getWriters", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
		checkMethodExists(type, "getReaders", Arrays.asList(SerializationContext.class, SerializationHandler.class),
				Modifier.PUBLIC, Map.class);
	}
}
