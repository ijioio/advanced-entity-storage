package com.ijioio.aes.persistence.jdbc.value.handler;

import java.sql.Array;
import java.sql.Blob;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.persistence.PersistenceException;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;

public class JdbcByteArrayPersistenceValueHandler extends BaseJdbcPersistenceValueHandler<byte[]> {

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

			Blob blob = value != null ? statement.getConnection().createBlob() : null;

			if (blob != null) {
				blob.setBytes(1, value);
			}

			statement.setObject(context.getNextIndex(), blob, Types.BLOB);

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

			Array array = null;

			if (values != null) {

				List<Blob> blobs = new ArrayList<>();

				for (byte[] value : values) {

					Blob blob = value != null ? statement.getConnection().createBlob() : null;

					if (blob != null) {
						blob.setBytes(1, value);
					}

					blobs.add(blob);
				}

				array = statement.getConnection().createArrayOf(JDBCType.valueOf(Types.BLOB).getName(),
						blobs.toArray());
			}

			statement.setObject(context.getNextIndex(), array, Types.ARRAY);

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
