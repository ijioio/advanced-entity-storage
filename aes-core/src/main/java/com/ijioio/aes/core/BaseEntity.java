package com.ijioio.aes.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Base class for all entities.
 */
public abstract class BaseEntity extends BaseIdentity implements Entity {

	public static class Properties {

		public static final Property<String> id = Property.of("id", new TypeReference<String>() {
		});

		private static final List<Property<?>> values = new ArrayList<>();

		static {

			values.add(id);
		}
	}

	@Override
	public Collection<Property<?>> getProperties() {
		return Properties.values;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T read(Property<T> property) throws IntrospectionException {

		if (Properties.id.equals(property)) {
			return (T) getId();
		} else {
			throw new IntrospectionException(String.format("property %s is not supported", property));
		}
	}

	@Override
	public <T> void write(Property<T> property, T value) throws IntrospectionException {

		if (Properties.id.equals(property)) {
			setId((String) value);
		} else {
			throw new IntrospectionException(String.format("property %s is not supported", property));
		}
	}

	@Override
	public String toString() {
		return "BaseEntity [id=" + getId() + ", type=" + getClass().getName() + "]";
	}
}
