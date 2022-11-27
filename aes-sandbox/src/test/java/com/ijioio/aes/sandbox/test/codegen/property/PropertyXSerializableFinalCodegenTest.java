package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.XSerializable;
import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationException;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyXSerializableFinalCodegen;

public class PropertyXSerializableFinalCodegenTest extends BaseCodegenTest {

	public static class XSerializableObject implements XSerializable {

		private String valueString;

		public String getValueString() {
			return valueString;
		}

		public void setValueString(String valueString) {
			this.valueString = valueString;
		}

		@Override
		public void write(SerializationContext context, SerializationHandler handler) throws SerializationException {
			handler.write(context,
					Collections.singletonMap("valueString", () -> handler.write(context, "valueString", valueString)));
		}

		@Override
		public void read(SerializationContext context, SerializationHandler handler) throws SerializationException {
			handler.read(context,
					Collections.singletonMap("valueString", () -> valueString = handler.read(context, valueString)));
		}
	}

	@Entity( //
			name = PropertyXSerializableFinalCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueXSerializableObject", type = @Type(name = "com.ijioio.aes.sandbox.test.codegen.property.PropertyXSerializableFinalCodegenTest.XSerializableObject"), attributes = Attribute.FINAL) //
			} //
	)
	public static interface PropertyXSerializableFinalCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyXSerializableFinalCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyXSerializableFinalCodegen> type = PropertyXSerializableFinalCodegen.class;

		checkFieldExists(type, "valueXSerializableObject", Modifier.PRIVATE | Modifier.FINAL,
				XSerializableObject.class);

		checkMethodNotExists(type, "setValueXSerializableObject", Arrays.asList(XSerializableObject.class));
		checkMethodExists(type, "getValueXSerializableObject", Collections.emptyList(), Modifier.PUBLIC,
				XSerializableObject.class);
	}
}
