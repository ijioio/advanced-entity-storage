package com.ijioio.aes.sandbox.test.serialization.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ijioio.aes.core.Entity;

public abstract class BasePropertyMapSerializationTest<E extends Entity, V extends Map<I, I>, I>
		extends BasePropertySerializationTest<E, V> {

	public static class Some1 extends Some {
		// Empty
	}

	public static class Some2 extends Some {
		// Empty
	}

	public static class Some3 extends Some {
		// Empty
	}

	protected static List<Character> characters = new ArrayList<>();

	protected static List<Class<? extends Some>> types = new ArrayList<>();

	static {

		characters.add(Character.valueOf('a'));
		characters.add(Character.valueOf('b'));
		characters.add(Character.valueOf('c'));

		types.add(Some1.class);
		types.add(Some2.class);
		types.add(Some3.class);
	}

	protected final int VALUE_MAX_COUNT = 3;
}
