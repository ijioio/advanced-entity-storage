package com.ijioio.aes.persistence.jdbc.postgresql.value.handler;

import java.sql.Array;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.postgresql.PGConnection;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.BaseJdbcPersistenceValueHandler;

public class PgByteArrayPersistenceValueHandler extends BaseJdbcPersistenceValueHandler<byte[]> {

	protected final TypeReference<Long> oidType = TypeReference.of(Long.class);

	protected final TypeReference<List<Long>> oidListType = new TypeReference<List<Long>>() {
	};

	@Override
	public Class<byte[]> getType() {
		return byte[].class;
	}

	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<byte[]> type, boolean search) {
		return Collections.singletonList(name);
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<byte[]> type,
			byte[] value, boolean search) throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {

			if (value != null) {

				LargeObjectManager manager = statement.getConnection().unwrap(PGConnection.class).getLargeObjectAPI();

				Long oid = manager.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);

				try (LargeObject blob = manager.open(oid, LargeObjectManager.WRITE)) {
					blob.write(value);
				}

				handler.getValueHandler(oidType.getRawType()).write(context, handler, oidType,
						value != null ? oid : null, search);

			} else {

				handler.getValueHandler(oidType.getRawType()).write(context, handler, oidType, null, search);
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<byte[]>> type, Collection<byte[]> values, boolean search)
			throws PersistenceException {

		PreparedStatement statement = context.getStatement();

		try {

			if (values != null) {

				List<Long> oidValues = new ArrayList<>();

				for (byte[] value : values) {

					if (value != null) {

						LargeObjectManager manager = statement.getConnection().unwrap(PGConnection.class)
								.getLargeObjectAPI();

						Long oid = manager.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);

						try (LargeObject blob = manager.open(oid, LargeObjectManager.WRITE)) {
							blob.write(value);
						}

						oidValues.add(oid);

					} else {

						oidValues.add(null);
					}
				}

				handler.getValueHandler(oidListType.getRawType()).write(context, handler, oidListType, oidValues,
						search);

			} else {

				handler.getValueHandler(oidListType.getRawType()).write(context, handler, oidListType, null, search);
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public byte[] read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<byte[]> type,
			byte[] value) throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {

			Blob blob = resultSet.getObject(context.getNextIndex(), Blob.class);

			if (blob != null) {

				return blob.getBytes(1, (int) blob.length());

			} else {

				if (value != null) {
					Arrays.fill(value, (byte) 0);
				}

				return null;
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public Collection<byte[]> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<byte[]>> type, Collection<byte[]> values) throws PersistenceException {

		ResultSet resultSet = context.getResultSet();

		try {

			Array array = resultSet.getObject(context.getNextIndex(), Array.class);

			if (array != null) {

				Collection<byte[]> collection = getCollection(type, values);

				collection.clear();

				for (Object object : (Object[]) array.getArray()) {
					collection.add(((Blob) object).getBytes(1, (int) ((Blob) object).length()));
				}

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
