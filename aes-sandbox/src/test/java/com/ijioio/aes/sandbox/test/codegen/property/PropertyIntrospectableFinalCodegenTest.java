package com.ijioio.aes.sandbox.test.codegen.property;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.core.Introspectable;
import com.ijioio.aes.core.IntrospectionException;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.PropertyIntrospectableFinalCodegen;

public class PropertyIntrospectableFinalCodegenTest extends BaseCodegenTest {

	public static class TestIntrospectable implements Introspectable {

		public static final String NAME = "com.ijioio.aes.sandbox.test.codegen.property.PropertyIntrospectableFinalCodegenTest.TestIntrospectable";

		public static class Properties {

			public static final Property<String> value = Property.of("value", new TypeReference<String>() {
			});

			private static final List<Property<?>> values = new ArrayList<>();

			static {
				values.add(value);
			}
		}

		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		@Override
		public Collection<Property<?>> getProperties() {
			return Properties.values;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T read(Property<T> property) throws IntrospectionException {

			if (Properties.value.equals(property)) {
				return (T) this.value;
			} else {
				throw new IntrospectionException(String.format("property %s is not supported", property));
			}
		}

		@Override
		public <T> void write(Property<T> property, T value) throws IntrospectionException {

			if (Properties.value.equals(property)) {
				this.value = (String) value;
			} else {
				throw new IntrospectionException(String.format("property %s is not supported", property));
			}
		}
	}

	@Entity( //
			name = PropertyIntrospectableFinalCodegenPrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueIntrospectable", type = TestIntrospectable.NAME, attributes = Attribute.FINAL) //
			} //
	)
	public static interface PropertyIntrospectableFinalCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyIntrospectableFinalCodegen";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<PropertyIntrospectableFinalCodegen> type = PropertyIntrospectableFinalCodegen.class;

		checkFieldExists(type, "valueIntrospectable", Modifier.PRIVATE | Modifier.FINAL, TestIntrospectable.class);

		checkMethodNotExists(type, "setValueIntrospectable", Arrays.asList(TestIntrospectable.class));
		checkMethodExists(type, "getValueIntrospectable", Collections.emptyList(), Modifier.PUBLIC,
				TestIntrospectable.class);
	}
}
