package com.ijioio.aes.core.persistence;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ijioio.aes.core.Property;

public interface PersistenceHandler {

	public <T> List<String> getColumns(PersistenceContext context, String name, Class<T> type)
			throws PersistenceException;

	public <T> void write(PersistenceContext context, String name, T value, Class<T> type) throws PersistenceException;

	public <T> T read(PersistenceContext context, String name, T value, Class<T> type) throws PersistenceException;

	public void insert(PersistenceContext context, String table, Collection<Property<?>> properties,
			Map<String, PersistenceColumnProvider> columnProviders, Map<String, PersistenceWriter> writers)
			throws PersistenceException;
}
