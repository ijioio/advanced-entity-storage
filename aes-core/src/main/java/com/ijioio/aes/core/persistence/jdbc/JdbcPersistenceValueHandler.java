package com.ijioio.aes.core.persistence.jdbc;

import java.util.Collection;
import java.util.List;

import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.persistence.PersistenceException;

public interface JdbcPersistenceValueHandler<T> {

	public Class<T> getType();

	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<T> property,
			boolean search);

	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<T> property, T value,
			boolean search) throws PersistenceException;

	public default void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			Property<? extends Collection<T>> property, Collection<T> values, boolean search)
			throws PersistenceException {
		throw new UnsupportedOperationException();
	}

	public T read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<T> property, T value)
			throws PersistenceException;

	public default Collection<T> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			Property<? extends Collection<T>> property, Collection<T> values) throws PersistenceException {
		throw new UnsupportedOperationException();
	}
}
