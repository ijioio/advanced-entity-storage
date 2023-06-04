package com.ijioio.aes.core.entity.storage.physical;

import com.ijioio.aes.core.entity.storage.EntityData;
import com.ijioio.aes.core.entity.storage.StorageException;

public interface PhysicalEntityStorage {

	public void save(EntityData entityData, PhysicalTransaction transaction) throws StorageException;

	public PhysicalTransaction createTransaction() throws StorageException;
}
