package com.ijioio.aes.core.entity.storage.standard;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityContainer;
import com.ijioio.aes.core.entity.storage.EntityData;
import com.ijioio.aes.core.entity.storage.EntityStorage;
import com.ijioio.aes.core.entity.storage.StorageException;
import com.ijioio.aes.core.entity.storage.Transaction;
import com.ijioio.aes.core.persistence.PersistenceHandler;
import com.ijioio.aes.core.serialization.SerializationHandler;

public class StandardEntityStorage2 implements EntityStorage {

	private final DataSource dataSource;

	private final PersistenceHandler persistenceHandler;

	private final SerializationHandler serializationHandler;

	public StandardEntityStorage2(DataSource dataSource, PersistenceHandler persistenceHandler,
			SerializationHandler serializationHandler) {

		this.dataSource = dataSource;
		this.persistenceHandler = persistenceHandler;
		this.serializationHandler = serializationHandler;
	}

	@Override
	public <E extends Entity> EntityContainer<E> save(EntityContainer<E> entityContainer) throws StorageException {

		Transaction transaction = StandardTransaction2.exists() ? StandardTransaction2.get() : createTransaction();

		try {

			transaction.begin();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			serializationHandler.write(entityContainer.getEntity(), baos);

			byte[] data = baos.toByteArray();

			System.out.println("data -> \n" + new String(data, StandardCharsets.UTF_8));

			EntityData entityData = new EntityData();

			entityData.setId(entityContainer.getId());
			entityData.setEntityType(entityContainer.getEntityType().getName());
			entityData.setData(data);

//			persistenceHandler.save(entityData, ((StandardLogicalTransaction) transaction).getTransaction());

			transaction.commit();

			return entityContainer;

		} catch (Throwable e) {

			transaction.rollback();

			throw new StorageException("save failed", e);
		}
	}

	@Override
	public Transaction createTransaction() throws StorageException {

		try {
			return StandardTransaction2.create(dataSource.getConnection());
		} catch (SQLException e) {
			throw new StorageException("transaction create failed", e);
		}
	}
}
