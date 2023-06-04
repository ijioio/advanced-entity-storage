package com.ijioio.aes.core.entity.storage.logical.standard;

import com.ijioio.aes.core.entity.storage.StorageException;
import com.ijioio.aes.core.entity.storage.logical.LogicalTransaction;
import com.ijioio.aes.core.entity.storage.physical.PhysicalTransaction;

public class StandardLogicalTransaction implements LogicalTransaction {

	private final PhysicalTransaction transaction;

	private int count = 0;

	public StandardLogicalTransaction(PhysicalTransaction transaction) {
		this.transaction = transaction;
	}

	public PhysicalTransaction getTransaction() {
		return transaction;
	}

	@Override
	public void begin() throws StorageException {

		if (count == 0) {
			transaction.begin();
		}

		count++;
	}

	@Override
	public void commit() throws StorageException {

		// TODO: check if is it already 0!

		count--;

		if (count == 0) {
			transaction.commit();
		}
	}

	@Override
	public void rollback() throws StorageException {

		// TODO: check if is it already 0!

		count--;

		if (count == 0) {
			transaction.rollback();
		}
	}
}
