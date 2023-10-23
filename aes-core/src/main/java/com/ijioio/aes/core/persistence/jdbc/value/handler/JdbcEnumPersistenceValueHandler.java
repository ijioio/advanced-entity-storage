package com.ijioio.aes.core.persistence.jdbc.value.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;

@SuppressWarnings("rawtypes")
public class JdbcEnumPersistenceValueHandler extends BaseJdbcPersistenceValueHandler<Enum> {

	protected final TypeReference<String> nameType = TypeReference.of(String.class);

	protected final TypeReference<List<String>> nameListType = new TypeReference<List<String>>() {
	};

	@Override
	public Class<Enum> getType() {
		return Enum.class;
	}

	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<Enum> type, boolean search) throws PersistenceException {
		return handler.getColumns(context, name, nameType, search);
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Enum> type,
			Enum value, boolean search) throws PersistenceException {
		handler.write(context, nameType, value != null ? value.name() : null, search);
	}

	@Override
	public void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Enum>> type, Collection<Enum> values, boolean search)
			throws PersistenceException {

		if (values != null) {

			List<String> nameValues = new ArrayList<>();

			for (Enum value : values) {
				nameValues.add(value != null ? value.name() : null);
			}

			handler.write(context, nameListType, nameValues, search);

		} else {

			handler.write(context, nameListType, null, search);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enum read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Enum> type,
			Enum value) throws PersistenceException {

		String nameValue = handler.read(context, nameType, null);

		try {
			return nameValue != null ? Enum.valueOf(type.getRawType(), nameValue) : null;
		} catch (IllegalArgumentException | NullPointerException e) {
			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Enum> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Enum>> type, Collection<Enum> values) throws PersistenceException {

		List<String> nameValues = handler.read(context, nameListType, null);

		if (nameValues != null) {

			Class<Enum> enumType = type.getParameterTypes()[0].getRawType();

			Collection<Enum> collection = getCollection(type, values);

			collection.clear();

			for (String nameValue : nameValues) {

				try {
					collection.add(nameValue != null ? Enum.valueOf(enumType, nameValue) : null);
				} catch (IllegalArgumentException | NullPointerException e) {
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
