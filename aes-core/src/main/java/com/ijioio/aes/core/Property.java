package com.ijioio.aes.core;

public class Property<T> {

	public static <T> Property<T> of(String name, Class<T> type) {
		return new Property<>(name, type);
	}

	private final String name;

	private final Class<T> type;

	private Property(String name, Class<T> type) {

		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Class<T> getType() {
		return type;
	}
}
