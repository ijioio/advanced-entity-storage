package com.ijioio.aes.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Convenient class for holding reference data for the entity.
 */
public final class EntityReference<E extends Entity> implements Introspectable {

	public static class Properties {

		public static final Property<String> id = Property.of("id", new TypeReference<String>() {
		});

		public static final Property<Class<? extends Entity>> type = Property.of("type",
				new TypeReference<Class<? extends Entity>>() {
				});

		private static final List<Property<?>> values = new ArrayList<>();

		static {

			values.add(id);
			values.add(type);
		}
	}

	/**
	 * Creates entity reference for entity with indicated {@code id} and
	 * {@code type}.
	 *
	 * @param id   of the entity
	 * @param type of the entity
	 * @return entity reference for the entity
	 */
	public static <E extends Entity> EntityReference<E> of(String id, Class<E> type) {

		EntityReference<E> entityReference = new EntityReference<>();

		entityReference.setId(id);
		entityReference.setType(type);

		return entityReference;
	}

	private String id;

	private Class<E> type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Class<E> getType() {
		return type;
	}

	public void setType(Class<E> type) {
		this.type = type;
	}

	@Override
	public Collection<Property<?>> getProperties() {
		return Properties.values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T read(Property<T> property) throws IntrospectionException {

		if (Properties.id.equals(property)) {
			return (T) id;
		} else if (Properties.type.equals(property)) {
			return (T) type;
		} else {
			throw new IntrospectionException(String.format("property %s is not supported", property));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void write(Property<T> property, T value) throws IntrospectionException {

		if (Properties.id.equals(property)) {
			id = (String) value;
		} else if (Properties.type.equals(property)) {
			type = (Class<E>) value;
		} else {
			throw new IntrospectionException(String.format("property %s is not supported", property));
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, type);
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

		EntityReference<?> other = (EntityReference<?>) obj;

		return Objects.equals(id, other.id) && Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "EntityReference [id=" + id + ", type=" + type + "]";
	}
}
