package com.ijioio.aes.persistence.jdbc.value.handler;

import java.sql.Array;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.persistence.PersistenceException;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;

public class JdbcCharacterPersistenceValueHandler extends BaseJdbcPersistenceValueHandler<Character> {

	@Override
	public Class<Character> getType() {
		return Character.class;
	}

	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<Character> type, boolean search) {
		return Collections.singletonList(name);
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Character> type,
			Character value, boolean search) throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {
			statement.setObject(context.getNextIndex(), value != null ? String.valueOf(value) : null, Types.CHAR);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Character>> type, Collection<Character> values, boolean search)
			throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {

			Array array = values != null
					? statement.getConnection().createArrayOf(JDBCType.valueOf(Types.CHAR).getName(),
							values.stream().map(item -> item != null ? String.valueOf(item) : null).toArray())
					: null;

			statement.setObject(context.getNextIndex(), array, Types.ARRAY);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Character read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Character> type,
			Character value) throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {
			return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), String.class))
					.map(item -> item.length() > 0 ? item.charAt(0) : null).orElse(null);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Collection<Character> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Character>> type, Collection<Character> values)
			throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {

			Array array = resultSet.getObject(context.getNextIndex(), Array.class);

			if (array != null) {

				Collection<Character> collection = getCollection(type, values);

				collection.clear();
				collection.addAll(Arrays.stream((Object[]) array.getArray()).map(item -> (String) item)
						.map(item -> item.length() > 0 ? item.charAt(0) : null).collect(Collectors.toList()));

				return collection;

			} else {

				if (values != null) {
					values.clear();
				}

				return null;
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
}
