package com.ijioio.aes.persistence.jdbc.value.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.persistence.PersistenceException;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;

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
		return handler.getValueHandler(nameType.getRawType()).getColumns(context, handler, name, nameType, search);
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Enum> type,
			Enum value, boolean search) throws PersistenceException {
		handler.getValueHandler(nameType.getRawType()).write(context, handler, nameType,
				value != null ? value.name() : null, search);
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

			handler.getValueHandler(nameListType.getRawType()).write(context, handler, nameListType, nameValues,
					search);

		} else {

			handler.getValueHandler(nameListType.getRawType()).write(context, handler, nameListType, null, search);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Enum read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Enum> type,
			Enum value) throws PersistenceException {

		String nameValue = handler.getValueHandler(nameType.getRawType()).read(context, handler, nameType, null);

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

		List<String> nameValues = handler.getValueHandler(nameListType.getRawType()).read(context, handler,
				nameListType, null);

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
