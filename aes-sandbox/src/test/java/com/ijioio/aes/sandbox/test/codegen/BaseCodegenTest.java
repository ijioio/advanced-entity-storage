package com.ijioio.aes.sandbox.test.codegen;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;

public class BaseCodegenTest {

	protected void checkType(Class<?> type, int expectedModifiers, String expectedCanonicalName,
			Class<?> expectedSuperClass, Collection<Class<?>> expectedInterfaces) {

		Assertions.assertEquals(expectedModifiers, type.getModifiers());
		Assertions.assertEquals(expectedCanonicalName, type.getCanonicalName());
		Assertions.assertEquals(expectedSuperClass, type.getSuperclass());
		Assertions.assertArrayEquals(expectedInterfaces.toArray(new Class<?>[expectedInterfaces.size()]),
				type.getInterfaces());
	}

	protected void checkFieldExists(Class<?> type, String expectedName, int expectedModifiers, Class<?> expectedType)
			throws NoSuchFieldException {

		Field field = type.getDeclaredField(expectedName);

		Assertions.assertEquals(expectedModifiers, field.getModifiers());
		Assertions.assertEquals(expectedType, field.getType());
	}

	protected void checkFieldNotExists(Class<?> type, String expectedName) throws NoSuchFieldException {

		Assertions.assertThrows(NoSuchFieldException.class, () -> type.getDeclaredField(expectedName));
	}

	protected void checkMethodExists(Class<?> type, String expectedName, Collection<Class<?>> expectedParameterTypes,
			int expectedModifiers, Class<?> expectedReturnType) throws NoSuchMethodException {

		Method method = type.getDeclaredMethod(expectedName,
				expectedParameterTypes.toArray(new Class<?>[expectedParameterTypes.size()]));

		Assertions.assertEquals(expectedModifiers, method.getModifiers());
		Assertions.assertEquals(expectedReturnType, method.getReturnType());
	}

	protected void checkMethodNotExists(Class<?> type, String expectedName, Collection<Class<?>> expectedParameterTypes)
			throws NoSuchMethodException {

		Assertions.assertThrows(NoSuchMethodException.class, () -> type.getDeclaredMethod(expectedName,
				expectedParameterTypes.toArray(new Class<?>[expectedParameterTypes.size()])));
	}
}
