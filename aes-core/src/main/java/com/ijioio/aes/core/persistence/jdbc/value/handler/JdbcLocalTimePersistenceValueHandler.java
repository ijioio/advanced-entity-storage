package com.ijioio.aes.core.persistence.jdbc.value.handler;

import java.sql.Array;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;

public class JdbcLocalTimePersistenceValueHandler extends BaseJdbcPersistenceValueHandler<LocalTime> {

	@Override
	public Class<LocalTime> getType() {
		return LocalTime.class;
	}

	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<LocalTime> type, boolean search) {
		return Collections.singletonList(name);
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<LocalTime> type,
			LocalTime value, boolean search) throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {
			statement.setObject(context.getNextIndex(), value != null ? Time.valueOf(value) : null, Types.TIME);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<LocalTime>> type, Collection<LocalTime> values, boolean search)
			throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {

			Array array = values != null
					? statement.getConnection().createArrayOf(JDBCType.valueOf(Types.TIME).getName(),
							values.stream().map(item -> item != null ? Time.valueOf(item) : null).toArray())
					: null;

			statement.setObject(context.getNextIndex(), array, Types.ARRAY);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public LocalTime read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<LocalTime> type,
			LocalTime value) throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {
			return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), Time.class))
					.map(item -> item.toLocalTime()).orElse(null);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Collection<LocalTime> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<LocalTime>> type, Collection<LocalTime> values)
			throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {

			Array array = resultSet.getObject(context.getNextIndex(), Array.class);

			if (array != null) {

				Collection<LocalTime> collection = getCollection(type, values);

				collection.clear();
				collection.addAll(Arrays.stream((Object[]) array.getArray())
						.map(item -> item != null ? ((Time) item).toLocalTime() : null).collect(Collectors.toList()));

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
