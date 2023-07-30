package com.ijioio.aes.core;

import java.util.Objects;

public class Property<T> {

	@SuppressWarnings("unchecked")
	public static <T, A extends T> Property<A> of(String name, Class<T> type) {
		return (Property<A>) new Property<>(name, type);
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

	@Override
	public int hashCode() {
		return Objects.hash(name, type);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		Property<?> other = (Property<?>) obj;

		return Objects.equals(name, other.name) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "Property [name=" + name + ", type=" + type + "]";
	}
}
