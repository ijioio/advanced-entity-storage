package com.ijioio.aes.core.entity.storage.standard;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import javax.sql.DataSource;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityContainer;
import com.ijioio.aes.core.entity.storage.EntityStorage;
import com.ijioio.aes.core.entity.storage.StorageException;
import com.ijioio.aes.core.entity.storage.Transaction;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.PersistenceHandler;
import com.ijioio.aes.core.persistence.PersistenceTransaction;
import com.ijioio.aes.core.serialization.SerializationHandler;

public class StandardEntityStorage implements EntityStorage {

	private final PersistenceHandler persistenceHandler;

	private final SerializationHandler serializationHandler;

	public StandardEntityStorage(DataSource dataSource, PersistenceHandler persistenceHandler,
			SerializationHandler serializationHandler) {

		this.persistenceHandler = persistenceHandler;
		this.serializationHandler = serializationHandler;
	}

	@Override
	public <E extends Entity> EntityContainer<E> save(EntityContainer<E> entityContainer) throws StorageException {

		try {

			PersistenceTransaction transaction = persistenceHandler.obtainTransaction();

			try {

				transaction.begin();

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				serializationHandler.write(entityContainer.getEntity(), baos);

				byte[] data = baos.toByteArray();

				System.out.println("data -> \n" + new String(data, StandardCharsets.UTF_8));

//				EntityData entityData = new EntityData();
//
//				entityData.setId(entityContainer.getId());
//				entityData.setEntityType(entityContainer.getEntityType().getName());
//				entityData.setData(data);

//			persistenceHandler.save(entityData, ((StandardLogicalTransaction) transaction).getTransaction());

				transaction.commit();

				return entityContainer;

			} catch (Throwable e) {

				transaction.rollback();

				throw new StorageException("save failed", e);
			}

		} catch (PersistenceException e) {
			throw new StorageException("save failed", e);
		}
	}

	@Override
	public Transaction createTransaction() throws StorageException {

		try {

			PersistenceTransaction transaction = persistenceHandler.createTransaction();

			return new Transaction() {

				@Override
				public void begin() throws StorageException {

					try {
						transaction.begin();
					} catch (PersistenceException e) {
						throw new StorageException("begin failed", e);
					}
				}

				@Override
				public void commit() throws StorageException {

					try {
						transaction.commit();
					} catch (PersistenceException e) {
						throw new StorageException("commit failed", e);
					}
				}

				@Override
				public void rollback() throws StorageException {

					try {
						transaction.rollback();
					} catch (PersistenceException e) {
						throw new StorageException("rollback failed", e);
					}
				}
			};

		} catch (PersistenceException e) {
			throw new StorageException("transaction create failed", e);
		}
	}
}
