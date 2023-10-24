package com.ijioio.aes.core.persistence.jdbc.value.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;

@SuppressWarnings("rawtypes")
public class JdbcClassPersistenceValueHandler extends BaseJdbcPersistenceValueHandler<Class> {

	protected final TypeReference<String> nameType = TypeReference.of(String.class);

	protected final TypeReference<List<String>> nameListType = new TypeReference<List<String>>() {
	};

	@Override
	public Class<Class> getType() {
		return Class.class;
	}

	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<Class> type, boolean search) throws PersistenceException {
		return handler.getValueHandler(nameType.getRawType()).getColumns(context, handler, name, nameType, search);
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Class> type,
			Class value, boolean search) throws PersistenceException {
		handler.getValueHandler(nameType.getRawType()).write(context, handler, nameType,
				value != null ? value.getName() : null, search);
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

			handler.getValueHandler(nameListType.getRawType()).write(context, handler, nameListType, nameValues,
					search);

		} else {

			handler.getValueHandler(nameListType.getRawType()).write(context, handler, nameListType, null, search);
		}
	}

	@Override
	public Class read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Class> type,
			Class value) throws PersistenceException {

		String nameValue = handler.getValueHandler(nameType.getRawType()).read(context, handler, nameType, null);

		try {
			return nameValue != null ? Class.forName(nameValue) : null;
		} catch (ClassNotFoundException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Collection<Class> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Class>> type, Collection<Class> values) throws PersistenceException {

		List<String> nameValues = handler.getValueHandler(nameListType.getRawType()).read(context, handler,
				nameListType, null);

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
