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
import java.util.stream.Collectors;

import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.persistence.PersistenceException;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;

public class JdbcStringPersistenceValueHandler extends BaseJdbcPersistenceValueHandler<String> {

	@Override
	public Class<String> getType() {
		return String.class;
	}

	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<String> type, boolean search) {
		return Collections.singletonList(name);
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<String> type,
			String value, boolean search) throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {
			statement.setObject(context.getNextIndex(), value, Types.VARCHAR);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<String>> type, Collection<String> values, boolean search)
			throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {

			Array array = values != null
					? statement.getConnection().createArrayOf(JDBCType.valueOf(Types.VARCHAR).getName(),
							values.toArray())
					: null;

			statement.setObject(context.getNextIndex(), array, Types.ARRAY);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public String read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<String> type,
			String value) throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {
			return resultSet.getObject(context.getNextIndex(), String.class);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Collection<String> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<String>> type, Collection<String> values) throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {

			Array array = resultSet.getObject(context.getNextIndex(), Array.class);

			if (array != null) {

				Collection<String> collection = getCollection(type, values);

				collection.clear();
				collection.addAll(Arrays.stream((Object[]) array.getArray()).map(item -> (String) item)
						.collect(Collectors.toList()));

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
