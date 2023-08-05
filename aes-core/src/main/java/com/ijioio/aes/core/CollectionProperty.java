package com.ijioio.aes.core;

import java.util.Collection;
import java.util.Objects;

public class CollectionProperty<T extends Collection<?>, E> extends Property<T> {

	@SuppressWarnings("unchecked")
	public static <T extends Collection<?>, E, A extends T, B extends E> CollectionProperty<A, B> of(String name,
			Class<T> type, Class<E> elementType) {
		return (CollectionProperty<A, B>) new CollectionProperty<>(name, type, elementType);
	}

	protected final Class<E> elementType;

	private CollectionProperty(String name, Class<T> type, Class<E> elementType) {

		super(name, type);
		this.elementType = elementType;
	}

	public Class<E> getElementType() {
		return elementType;
	}

	@Override
	public int hashCode() {

		final int prime = 31;

		int result = super.hashCode();

		result = prime * result + Objects.hash(elementType);

		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!super.equals(obj)) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		CollectionProperty<?, ?> other = (CollectionProperty<?, ?>) obj;

		return Objects.equals(elementType, other.elementType);
	}

	@Override
	public String toString() {
		return "CollectionProperty [name=" + name + ", type=" + type + ", elementType=" + elementType + "]";
	}
}
