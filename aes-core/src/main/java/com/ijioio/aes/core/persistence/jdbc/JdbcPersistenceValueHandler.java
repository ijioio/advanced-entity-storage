package com.ijioio.aes.core.persistence.jdbc;

import java.util.Collection;
import java.util.List;

import com.ijioio.aes.core.CollectionProperty;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.persistence.PersistenceException;

public interface JdbcPersistenceValueHandler<T> {

	public Class<T> getType();

	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			Property<T> property);

	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<T> property, T value)
			throws PersistenceException;

	public default void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			CollectionProperty<? extends Collection<T>, T> property, Collection<T> value) throws PersistenceException {
		throw new UnsupportedOperationException();
	}

	public T read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<T> property, T value)
			throws PersistenceException;

	public default Collection<T> read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			CollectionProperty<? extends Collection<T>, T> property, Collection<T> value) throws PersistenceException {
		throw new UnsupportedOperationException();
	}
}
