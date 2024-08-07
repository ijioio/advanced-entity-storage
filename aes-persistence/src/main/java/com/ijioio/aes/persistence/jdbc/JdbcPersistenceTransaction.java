package com.ijioio.aes.persistence.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.ijioio.aes.persistence.PersistenceException;
import com.ijioio.aes.persistence.PersistenceTransaction;

public class JdbcPersistenceTransaction implements PersistenceTransaction {

	private final JdbcPersistenceHandler handler;

	private int count = 0;

	private Connection connection;

	public JdbcPersistenceTransaction(JdbcPersistenceHandler handler) {
		this.handler = handler;
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	public void begin() throws PersistenceException {

		try {

			if (count == 0) {

				connection = handler.dataSource.getConnection();
				connection.setAutoCommit(false);

				handler.transactions.get().addLast(this);
			}

			count++;

		} catch (SQLException e) {
			throw new PersistenceException("begin failed", e);
		}
	}

	@Override
	public void commit() throws PersistenceException {

		try {

			count--;

			if (count == 0) {

				connection.commit();
				connection.close();

				handler.transactions.get().removeLast();
			}

		} catch (SQLException e) {
			throw new PersistenceException("commit failed", e);
		}
	}

	@Override
	public void rollback() throws PersistenceException {

		try {

			count--;

			if (count == 0) {

				connection.rollback();
				connection.close();

				handler.transactions.get().removeLast();
			}

		} catch (SQLException e) {
			throw new PersistenceException("rollback failed", e);
		}
	}
}
