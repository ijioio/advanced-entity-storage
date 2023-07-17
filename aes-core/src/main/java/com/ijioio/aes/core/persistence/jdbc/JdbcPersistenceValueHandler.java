package com.ijioio.aes.core.persistence.jdbc;

import java.util.List;

import com.ijioio.aes.core.persistence.PersistenceException;

public interface JdbcPersistenceValueHandler<T> {

	public Class<T> getType();

//	public Collection<Property<?>> getProperties(Property<?> property);

	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String property);

	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, T value)
			throws PersistenceException;

	public T read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, T value, Class<T> type)
			throws PersistenceException;
}
