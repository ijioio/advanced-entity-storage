package com.ijioio.aes.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Base class for all entity indexes.
 */
public abstract class BaseEntityIndex<E extends Entity> extends BaseIdentity implements EntityIndex<E> {

	public static class Properties {

		public static final Property<String> id = Property.of("id", new TypeReference<String>() {
		});

		public static final Property<EntityReference<? extends Entity>> source = Property.of("source",
				new TypeReference<EntityReference<? extends Entity>>() {
				});

		private static final List<Property<?>> values = new ArrayList<>();

		static {

			values.add(id);
			values.add(source);
		}
	}

	private EntityReference<E> source;

	@Override
	public EntityReference<E> getSource() {
		return source;
	}

	public void setSource(EntityReference<E> source) {
		this.source = source;
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
		} else if (Properties.source.equals(property)) {
			return (T) getSource();
		} else {
			throw new IntrospectionException(String.format("property %s is not supported", property));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void write(Property<T> property, T value) throws IntrospectionException {

		if (Properties.id.equals(property)) {
			setId((String) value);
		} else if (Properties.source.equals(property)) {
			setSource((EntityReference<E>) value);
		} else {
			throw new IntrospectionException(String.format("property %s is not supported", property));
		}
	}
}
