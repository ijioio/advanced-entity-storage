package com.ijioio.aes.core.persistence.jdbc;

import java.util.List;

import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.persistence.PersistenceException;

public interface JdbcPersistenceValueHandler<T> {

	public Class<T> getType();

	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			Property<T> property);

	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<T> property, T value)
			throws PersistenceException;

	public T read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<T> property, T value)
			throws PersistenceException;
}
