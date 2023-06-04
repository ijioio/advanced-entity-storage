package com.ijioio.aes.core.entity.storage;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityContainer;

public interface EntityStorage {

	public <E extends Entity> EntityContainer<E> save(EntityContainer<E> entityContainer) throws StorageException;

	public Transaction createTransaction() throws StorageException;
}
