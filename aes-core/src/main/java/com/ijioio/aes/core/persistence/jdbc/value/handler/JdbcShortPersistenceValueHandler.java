package com.ijioio.aes.core.persistence.jdbc.value.handler;

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
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;

public class JdbcShortPersistenceValueHandler extends BaseJdbcPersistenceValueHandler<Short> {

	@Override
	public Class<Short> getType() {
		return Short.class;
	}

	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<Short> type, boolean search) {
		return Collections.singletonList(name);
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Short> type,
			Short value, boolean search) throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {
			statement.setObject(context.getNextIndex(), value, Types.SMALLINT);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Short>> type, Collection<Short> values, boolean search)
			throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {

			Array array = values != null
					? statement.getConnection().createArrayOf(JDBCType.valueOf(Types.SMALLINT).getName(),
							values.toArray())
					: null;

			statement.setObject(context.getNextIndex(), array, Types.ARRAY);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Short read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Short> type,
			Short value) throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {
			return resultSet.getObject(context.getNextIndex(), Short.class);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Collection<Short> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Short>> type, Collection<Short> values) throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {

			Array array = resultSet.getObject(context.getNextIndex(), Array.class);

			if (array != null) {

				Collection<Short> collection = getCollection(type, values);

				collection.clear();
				collection.addAll(Arrays.stream((Object[]) array.getArray()).map(item -> (Short) item)
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
