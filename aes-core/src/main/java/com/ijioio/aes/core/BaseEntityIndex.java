package com.ijioio.aes.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for all entity indexes.
 */
public abstract class BaseEntityIndex<E extends Entity> extends BaseIdentity implements EntityIndex<E> {

	public static class Properties {

		public static final Property<String> id = Property.of("id", String.class);

		public static final Property<EntityReference<?>> source = Property.of("source", EntityReference.class);

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

	@Override
	public Map<Property<?>, PropertyWriter<?>> getWriters() {

		Map<Property<?>, PropertyWriter<?>> writers = new LinkedHashMap<>();

		writers.put(Properties.id, (String value) -> setId(value));
		writers.put(Properties.source, (EntityReference<E> value) -> setSource(value));

		return writers;
	}

	@Override
	public Map<Property<?>, PropertyReader<?>> getReaders() {

		Map<Property<?>, PropertyReader<?>> readers = new LinkedHashMap<>();

		readers.put(Properties.id, () -> getId());
		readers.put(Properties.source, () -> getSource());

		return readers;
	}
}
