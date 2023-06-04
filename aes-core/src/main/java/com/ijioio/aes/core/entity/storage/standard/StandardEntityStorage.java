package com.ijioio.aes.core.entity.storage.standard;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityContainer;
import com.ijioio.aes.core.entity.storage.EntityStorage;
import com.ijioio.aes.core.entity.storage.StorageException;
import com.ijioio.aes.core.entity.storage.Transaction;
import com.ijioio.aes.core.entity.storage.logical.LogicalEntityStorage;
import com.ijioio.aes.core.entity.storage.logical.LogicalTransaction;

public class StandardEntityStorage implements EntityStorage {

	private final LogicalEntityStorage logicalEntityStorage;

	public StandardEntityStorage(LogicalEntityStorage logicalEntityStorage) {
		this.logicalEntityStorage = logicalEntityStorage;
	}

	@Override
	public <E extends Entity> EntityContainer<E> save(EntityContainer<E> entityContainer) throws StorageException {
		return logicalEntityStorage.save(entityContainer);
	}

	@Override
	public Transaction createTransaction() throws StorageException {

		LogicalTransaction transaction = logicalEntityStorage.createTransaction();

		return new StandardTransaction(transaction);
	}
}
