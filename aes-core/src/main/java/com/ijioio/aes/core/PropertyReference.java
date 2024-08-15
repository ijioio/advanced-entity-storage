package com.ijioio.aes.core;

import java.util.Objects;

public class PropertyReference<T> {

	public static <T> PropertyReference<T> of(Property<T> property) {
		return new PropertyReference<T>(null, property);
	}

	public static <T> PropertyReference<T> of(String namespace, Property<T> property) {
		return new PropertyReference<T>(namespace, property);
	}

	private final String namespace;

	private final Property<T> property;

	private PropertyReference(String namespace, Property<T> property) {

		this.namespace = namespace;
		this.property = property;
	}

	public String getNamespace() {
		return namespace;
	}

	public Property<T> getProperty() {
		return property;
	}

	@Override
	public int hashCode() {
		return Objects.hash(namespace, property);
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

		PropertyReference<?> other = (PropertyReference<?>) obj;

		return Objects.equals(namespace, other.namespace) && Objects.equals(property, other.property);
	}
}
