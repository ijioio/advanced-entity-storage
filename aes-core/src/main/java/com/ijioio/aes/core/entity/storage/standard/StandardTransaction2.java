package com.ijioio.aes.core.entity.storage.standard;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

import com.ijioio.aes.core.entity.storage.StorageException;
import com.ijioio.aes.core.entity.storage.Transaction;

public class StandardTransaction2 implements Transaction {

	private static final ThreadLocal<Deque<Transaction>> transactions = ThreadLocal
			.withInitial(() -> new ArrayDeque<>());

	public static boolean exists() {
		return !transactions.get().isEmpty();
	}

	public static Transaction get() {
		return transactions.get().getLast();
	}

	public static Transaction create(Connection connection) {
		return new StandardTransaction2(connection);
	}

	private final Connection connection;

	private int count = 0;

	private StandardTransaction2(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	@Override
	public void begin() throws StorageException {

		try {

			if (count == 0) {

				connection.setAutoCommit(false);

				transactions.get().addLast(this);
			}

			count++;

		} catch (SQLException e) {
			throw new StorageException("begin failed", e);
		}
	}

	@Override
	public void commit() throws StorageException {

		try {

			// TODO: check if is it already 0!
			count--;

			if (count == 0) {

				connection.commit();

				transactions.get().removeLast();
			}

		} catch (SQLException e) {
			throw new StorageException("commit failed", e);
		}
	}

	@Override
	public void rollback() throws StorageException {

		try {

			// TODO: check if is it already 0!
			count--;

			if (count == 0) {

				connection.rollback();

				transactions.get().removeLast();
			}

		} catch (SQLException e) {
			throw new StorageException("rollback failed", e);
		}
	}
}
