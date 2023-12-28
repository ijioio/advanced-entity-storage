package com.ijioio.aes.persistence.jdbc.postgresql.test;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.postgresql.PGConnection;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;

public final class PgPersistenceTestUtil {

	public static byte[] getBytes(ResultSet resultSet, String name) {

		try {

			Long oid = resultSet.getObject(name, Long.class);

			return getBytes(resultSet, oid);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] getBytes(ResultSet resultSet, Long oid) {

		try {

			if (oid != null) {

				LargeObjectManager manager = resultSet.getStatement().getConnection().unwrap(PGConnection.class)
						.getLargeObjectAPI();

				try (LargeObject blob = manager.open(oid, LargeObjectManager.READ)) {
					return blob.read(blob.size());
				}
			}

			return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
