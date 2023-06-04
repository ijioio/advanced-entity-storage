package com.ijioio.aes.core.entity.storage.logical;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityContainer;
import com.ijioio.aes.core.entity.storage.StorageException;

public interface LogicalEntityStorage {

	public <E extends Entity> EntityContainer<E> save(EntityContainer<E> entityContainer) throws StorageException;

	public LogicalTransaction createTransaction() throws StorageException;
}
