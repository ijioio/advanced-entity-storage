package com.ijioio.aes.core.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.SearchQuery;

public interface PersistenceHandler {

	public <T> List<String> getColumns(PersistenceContext context, String name, Class<T> type)
			throws PersistenceException;

	public <T> void write(PersistenceContext context, String name, T value, Class<T> type) throws PersistenceException;

	public <T> T read(PersistenceContext context, String name, T value, Class<T> type) throws PersistenceException;

	public void read(PersistenceContext context, Collection<Property<?>> properties,
			Map<String, PersistenceReader> readers) throws PersistenceException;

	public void create(PersistenceContext context, String table, Collection<Property<?>> properties,
			Map<String, PersistenceColumnProvider> columnProviders, Map<String, PersistenceWriter> writers)
			throws PersistenceException;

	public void update(PersistenceContext context, String table, Property<?> idProperty,
			Collection<Property<?>> properties, Map<String, PersistenceColumnProvider> columnProviders,
			Map<String, PersistenceWriter> writers) throws PersistenceException;

	public void delete(PersistenceContext context, String table, Property<?> idProperty,
			Map<String, PersistenceColumnProvider> columnProviders, Map<String, PersistenceWriter> writers)
			throws PersistenceException;

	public <I extends EntityIndex<?>> List<I> search(PersistenceContext context, SearchQuery<I> query)
			throws PersistenceException;
}
