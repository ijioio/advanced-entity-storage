package com.ijioio.aes.persistence.jdbc.value.handler;

import java.sql.Array;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
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

public class JdbcInstantPersistenceValueHandler extends BaseJdbcPersistenceValueHandler<Instant> {

	@Override
	public Class<Instant> getType() {
		return Instant.class;
	}

	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<Instant> type, boolean search) {
		return Collections.singletonList(name);
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Instant> type,
			Instant value, boolean search) throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {
			statement.setObject(context.getNextIndex(), value != null ? Timestamp.from(value) : null, Types.TIMESTAMP);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Instant>> type, Collection<Instant> values, boolean search)
			throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {

			Array array = values != null
					? statement.getConnection().createArrayOf(JDBCType.valueOf(Types.TIMESTAMP).getName(),
							values.stream().map(item -> item != null ? Timestamp.from(item) : null).toArray())
					: null;

			statement.setObject(context.getNextIndex(), array, Types.ARRAY);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Instant read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Instant> type,
			Instant value) throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {
			return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), Timestamp.class))
					.map(item -> item.toInstant()).orElse(null);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Collection<Instant> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<Instant>> type, Collection<Instant> values) throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {

			Array array = resultSet.getObject(context.getNextIndex(), Array.class);

			if (array != null) {

				Collection<Instant> collection = getCollection(type, values);

				collection.clear();
				collection.addAll(Arrays.stream((Object[]) array.getArray())
						.map(item -> item != null ? ((Timestamp) item).toInstant() : null)
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
