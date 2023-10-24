package com.ijioio.aes.core.persistence.jdbc.value.handler;

import java.util.Collection;
import java.util.List;

import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;

@SuppressWarnings("rawtypes")
public class JdbcCollectionPersistenceValueHandler extends BaseJdbcPersistenceValueHandler<Collection> {

	@Override
	public Class<Collection> getType() {
		return Collection.class;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<Collection> type, boolean search) throws PersistenceException {

		TypeReference elementType = type.getParameterTypes()[0];

		return handler.getValueHandler(elementType.getRawType()).getColumns(context, handler, name, elementType,
				search);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Collection> type,
			Collection value, boolean search) throws PersistenceException {

		TypeReference elementType = type.getParameterTypes()[0];

		handler.getValueHandler(elementType.getRawType()).writeCollection(context, handler, type, value, search);
	}

	@Override
	public void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Collection>> type, Collection<Collection> values, boolean search)
			throws PersistenceException {
		throw new UnsupportedOperationException("nested collections are not supported");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<Collection> type, Collection value) throws PersistenceException {

		TypeReference elementType = type.getParameterTypes()[0];

		return handler.getValueHandler(elementType.getRawType()).readCollection(context, handler, type, value);
	}

	@Override
	public Collection<Collection> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Collection>> type, Collection<Collection> values)
			throws PersistenceException {
		throw new UnsupportedOperationException("nested collections are not supported");
	}
}
