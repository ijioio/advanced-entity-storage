package com.ijioio.aes.core.entity.storage.logical.standard;

import java.util.ArrayList;
import java.util.List;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityContainer;
import com.ijioio.aes.core.entity.storage.EntityData;
import com.ijioio.aes.core.entity.storage.StorageException;
import com.ijioio.aes.core.entity.storage.logical.LogicalEntityStorage;
import com.ijioio.aes.core.entity.storage.logical.LogicalTransaction;
import com.ijioio.aes.core.entity.storage.physical.PhysicalEntityStorage;
import com.ijioio.aes.core.entity.storage.physical.PhysicalTransaction;
import com.ijioio.aes.core.serialization.SerializationManager;

public class StandardLogicalEntityStorage implements LogicalEntityStorage {

	private final ThreadLocal<List<LogicalTransaction>> transactions = ThreadLocal.withInitial(() -> new ArrayList<>());

	private final SerializationManager serializationManager;

	private final PhysicalEntityStorage physicalEntityStorage;

	public StandardLogicalEntityStorage(SerializationManager serializationManager,
			PhysicalEntityStorage physicalEntityStorage) {

		this.serializationManager = serializationManager;
		this.physicalEntityStorage = physicalEntityStorage;
	}

	@Override
	public <E extends Entity> EntityContainer<E> save(EntityContainer<E> entityContainer) throws StorageException {

		LogicalTransaction transaction = transactions.get().size() > 0
				? transactions.get().get(transactions.get().size() - 1)
				: createTransaction();

		try {

			transaction.begin();

			byte[] data = serializationManager.write(entityContainer.getEntity());

			EntityData entityData = new EntityData();

			entityData.setId(entityContainer.getId());
			entityData.setEntityType(entityContainer.getEntityType().getName());
			entityData.setData(data);

			physicalEntityStorage.save(entityData, ((StandardLogicalTransaction) transaction).getTransaction());

			transaction.commit();

			return entityContainer;

		} catch (Throwable e) {

			transaction.rollback();

			throw new StorageException("save failed", e);
		}
	}

	@Override
	public LogicalTransaction createTransaction() throws StorageException {

		PhysicalTransaction transaction = physicalEntityStorage.createTransaction();

		return new StandardLogicalTransaction(transaction);
	}
}
