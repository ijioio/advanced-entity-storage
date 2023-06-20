package com.ijioio.aes.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ijioio.aes.core.persistence.PersistenceColumnProvider;
import com.ijioio.aes.core.persistence.PersistenceContext;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.PersistenceHandler;
import com.ijioio.aes.core.persistence.PersistenceReader;
import com.ijioio.aes.core.persistence.PersistenceWriter;

/**
 * Base class for all entity indexes.
 */
public abstract class BaseEntityIndex<E extends Entity> extends BaseIdentity implements EntityIndex<E> {

	public static class Properties {

		public static final Property<String> id = Property.of("id", String.class);

		@SuppressWarnings("rawtypes")
		public static final Property<EntityReference> source = Property.of("source", EntityReference.class);

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

	public Collection<Property<?>> getProperties() {
		return Properties.values;
	}

	@Override
	public void insert(PersistenceContext context, PersistenceHandler handler) throws PersistenceException {
		handler.insert(context, getClass().getSimpleName(), getProperties(), getColumnProviders(context, handler),
				getWriters(context, handler));
	}

	public Map<String, PersistenceColumnProvider> getColumnProviders(PersistenceContext context,
			PersistenceHandler handler) {

		Map<String, PersistenceColumnProvider> columnProviders = new LinkedHashMap<>();

		columnProviders.put("id", () -> handler.getColumns(context, "id", String.class));
		columnProviders.put("source", () -> handler.getColumns(context, "source", EntityReference.class));

		return columnProviders;
	}

	public Map<String, PersistenceWriter> getWriters(PersistenceContext context, PersistenceHandler handler) {

		Map<String, PersistenceWriter> writers = new LinkedHashMap<>();

		writers.put("id", () -> handler.write(context, "id", getId(), String.class));
		writers.put("source", () -> handler.write(context, "source", getSource(), EntityReference.class));

		return writers;
	}

	@SuppressWarnings("unchecked")
	public Map<String, PersistenceReader> getReaders(PersistenceContext context, PersistenceHandler handler) {

		Map<String, PersistenceReader> readers = new LinkedHashMap<>();

		readers.put("id", () -> setId(handler.read(context, "id", getId(), String.class)));
		readers.put("source", () -> setSource(handler.read(context, "source", getSource(), EntityReference.class)));

		return readers;
	}
}
