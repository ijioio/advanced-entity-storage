package com.ijioio.aes.core.persistence.jdbc.value.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;

@SuppressWarnings("rawtypes")
public class JdbcClassPersistenceValueHandler extends JdbcBasePersistenceValueHandler<Class> {

	protected final TypeReference<String> nameType = TypeReference.of(String.class);

	protected final TypeReference<Collection<String>> nameCollectionType = new TypeReference<Collection<String>>() {
	};

	@Override
	public Class<Class> getType() {
		return Class.class;
	}

	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<Class> type, boolean search) throws PersistenceException {
		return handler.getColumns(context, name, nameType, search);
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Class> type,
			Class value, boolean search) throws PersistenceException {
		handler.write(context, nameType, value != null ? value.getName() : null, search);
	}

	@Override
	public void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Class>> type, Collection<Class> values, boolean search)
			throws PersistenceException {

		if (values != null) {

			List<String> nameValues = new ArrayList<>();

			for (Class value : values) {
				nameValues.add(value != null ? value.getName() : null);
			}

			handler.write(context, nameCollectionType, nameValues, search);

		} else {

			handler.write(context, nameCollectionType, null, search);
		}
	}

	@Override
	public Class read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Class> type,
			Class value) throws PersistenceException {

		String nameValue = handler.read(context, nameType, null);

		try {
			return nameValue != null ? Class.forName(nameValue) : null;
		} catch (ClassNotFoundException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Collection<Class> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Class>> type, Collection<Class> values) throws PersistenceException {

		Collection<String> nameValues = handler.read(context, nameCollectionType, null);

		if (nameValues != null) {

			Collection<Class> collection = getCollection(type, values);

			collection.clear();

			for (String nameValue : nameValues) {

				try {
					collection.add(nameValue != null ? Class.forName(nameValue) : null);
				} catch (ClassNotFoundException e) {
					throw new PersistenceException(e);
				}
			}

			return collection;

		} else {

			if (values != null) {
				values.clear();
			}

			return null;
		}
	}
}
