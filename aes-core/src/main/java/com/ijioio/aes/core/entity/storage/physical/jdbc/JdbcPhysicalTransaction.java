package com.ijioio.aes.core.entity.storage.physical.jdbc;

import java.sql.Connection;

import com.ijioio.aes.core.entity.storage.StorageException;
import com.ijioio.aes.core.entity.storage.physical.PhysicalTransaction;

public class JdbcPhysicalTransaction implements PhysicalTransaction {

	private final Connection connection;

	private boolean autoCommit = true;

	public JdbcPhysicalTransaction(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	public void begin() throws StorageException {

		try {

			autoCommit = connection.getAutoCommit();

			connection.setAutoCommit(false);

		} catch (Exception e) {
			throw new StorageException("begin failed", e);
		}
	}

	@Override
	public void commit() throws StorageException {

		try {

			connection.commit();

			connection.setAutoCommit(autoCommit);

		} catch (Exception e) {
			throw new StorageException("commit failed", e);
		}
	}

	@Override
	public void rollback() throws StorageException {

		try {

			connection.rollback();

			connection.setAutoCommit(autoCommit);

		} catch (Exception e) {
			throw new StorageException("rollback failed", e);
		}
	}
}
