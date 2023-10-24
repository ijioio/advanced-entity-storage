package com.ijioio.aes.core.persistence.jdbc.value.handler;

import java.sql.Array;
import java.sql.Date;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
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

public class JdbcLocalDatePersistenceValueHandler extends BaseJdbcPersistenceValueHandler<LocalDate> {

	@Override
	public Class<LocalDate> getType() {
		return LocalDate.class;
	}

	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<LocalDate> type, boolean search) {
		return Collections.singletonList(name);
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<LocalDate> type,
			LocalDate value, boolean search) throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {
			statement.setObject(context.getNextIndex(), value != null ? Date.valueOf(value) : null, Types.DATE);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<LocalDate>> type, Collection<LocalDate> values, boolean search)
			throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {

			Array array = values != null
					? statement.getConnection().createArrayOf(JDBCType.valueOf(Types.DATE).getName(),
							values.stream().map(item -> item != null ? Date.valueOf(item) : null).toArray())
					: null;

			statement.setObject(context.getNextIndex(), array, Types.ARRAY);

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public LocalDate read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<LocalDate> type,
			LocalDate value) throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {
			return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), Date.class))
					.map(item -> item.toLocalDate()).orElse(null);
		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Collection<LocalDate> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<LocalDate>> type, Collection<LocalDate> values)
			throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {

			Array array = resultSet.getObject(context.getNextIndex(), Array.class);

			if (array != null) {

				Collection<LocalDate> collection = getCollection(type, values);

				collection.clear();
				collection.addAll(Arrays.stream((Object[]) array.getArray())
						.map(item -> item != null ? ((Date) item).toLocalDate() : null).collect(Collectors.toList()));

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
