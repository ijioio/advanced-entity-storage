package com.ijioio.aes.core.entity.storage.standard;

import com.ijioio.aes.core.entity.storage.StorageException;
import com.ijioio.aes.core.entity.storage.Transaction;
import com.ijioio.aes.core.entity.storage.logical.LogicalTransaction;

public class StandardTransaction implements Transaction {

	private final LogicalTransaction transaction;

	public StandardTransaction(LogicalTransaction transaction) {
		this.transaction = transaction;
	}

	@Override
	public void begin() throws StorageException {
		transaction.begin();
	}

	@Override
	public void commit() throws StorageException {
		transaction.commit();
	}

	@Override
	public void rollback() throws StorageException {
		transaction.rollback();
	}
}
