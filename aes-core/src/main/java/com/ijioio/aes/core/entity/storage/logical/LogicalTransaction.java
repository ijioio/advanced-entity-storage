package com.ijioio.aes.core.entity.storage.logical;

import com.ijioio.aes.core.entity.storage.StorageException;

public interface LogicalTransaction {

	public void begin() throws StorageException;

	public void commit() throws StorageException;

	public void rollback() throws StorageException;
}
