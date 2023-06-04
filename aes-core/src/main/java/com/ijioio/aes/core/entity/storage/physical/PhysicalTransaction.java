package com.ijioio.aes.core.entity.storage.physical;

import com.ijioio.aes.core.entity.storage.StorageException;

public interface PhysicalTransaction {

	public void commit() throws StorageException;

	public void rollback() throws StorageException;
}
