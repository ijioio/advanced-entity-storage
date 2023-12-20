package com.ijioio.aes.core.entity.storage.standard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.ijioio.aes.core.BaseEntityIndex;
import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityContainer;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.EntityIndexHandler;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.core.entity.storage.EntityData;
import com.ijioio.aes.core.entity.storage.EntityStorage;
import com.ijioio.aes.core.entity.storage.EntityStorageRegistry;
import com.ijioio.aes.core.entity.storage.StorageException;
import com.ijioio.aes.core.entity.storage.Transaction;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.PersistenceHandler;
import com.ijioio.aes.core.persistence.PersistenceTransaction;
import com.ijioio.aes.core.serialization.SerializationHandler;

public class StandardEntityStorage implements EntityStorage {

	private final PersistenceHandler persistenceHandler;

	private final SerializationHandler serializationHandler;

	private final EntityStorageRegistry entityStorageRegistry;

	public StandardEntityStorage(DataSource dataSource, PersistenceHandler persistenceHandler,
			SerializationHandler serializationHandler, EntityStorageRegistry entityStorageRegistry) {

		this.persistenceHandler = persistenceHandler;
		this.serializationHandler = serializationHandler;
		this.entityStorageRegistry = entityStorageRegistry;
	}

	@Override
	public <E extends Entity> EntityContainer<E> load(EntityReference<E> entityReference) throws StorageException {

		try {

			PersistenceTransaction transaction = persistenceHandler.obtainTransaction();

			try {

				transaction.begin();

				EntityContainer<E> entityContainer = null;

				EntityData<E> entityData = persistenceHandler.load(entityReference);

				if (entityData != null) {

					ByteArrayInputStream bais = new ByteArrayInputStream(entityData.getData());

					entityContainer = EntityContainer.of(entityReference.getId(), serializationHandler.read(bais));
				}

				transaction.commit();

				return entityContainer;

			} catch (Throwable e) {

				transaction.rollback();

				throw new StorageException("load failed", e);
			}

		} catch (PersistenceException e) {
			throw new StorageException("load failed", e);
		}
	}

	@Override
	public <E extends Entity> EntityContainer<E> save(EntityContainer<E> entityContainer) throws StorageException {

		try {

			PersistenceTransaction transaction = persistenceHandler.obtainTransaction();

			try {

				transaction.begin();

				EntityContainer<E> oldEntityContainer = load(entityContainer.toReference());

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				serializationHandler.write(entityContainer.getEntity(), baos);

				EntityData<E> entityData = new EntityData<>();

				entityData.setId(entityContainer.getId());
				entityData.setEntityType(entityContainer.getEntityType());
				entityData.setData(baos.toByteArray());

				if (oldEntityContainer != null) {
					persistenceHandler.update(entityData);
				} else {
					persistenceHandler.create(entityData);
				}

				Map<Class<EntityIndex<E>>, List<EntityIndexHandler<E, EntityIndex<E>>>> handlerGroups = entityStorageRegistry
						.getHandlers(entityContainer.getEntityType()).stream()
						.collect(Collectors.groupingBy(item -> item.getEntityIndexType(), Collectors.toList()));

				for (Entry<Class<EntityIndex<E>>, List<EntityIndexHandler<E, EntityIndex<E>>>> entry : handlerGroups
						.entrySet()) {

					Class<EntityIndex<E>> entityIndexType = entry.getKey();
					List<EntityIndexHandler<E, EntityIndex<E>>> handlers = entry.getValue();

					if (oldEntityContainer != null) {
						persistenceHandler.delete(SearchQueryBuilder.of(entityIndexType)
								.eq(BaseEntityIndex.Properties.source, entityContainer.toReference()).build());
					}

					for (EntityIndexHandler<E, EntityIndex<E>> handler : handlers) {

						Collection<EntityIndex<E>> indexes = handler.create(entityContainer);

						for (EntityIndex<E> index : indexes) {
							persistenceHandler.create(index);
						}
					}
				}

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
	public <E extends Entity> void delete(EntityContainer<E> entityContainer) throws StorageException {

		try {

			PersistenceTransaction transaction = persistenceHandler.obtainTransaction();

			try {

				transaction.begin();

				EntityContainer<E> oldEntityContainer = load(entityContainer.toReference());

				ByteArrayOutputStream baos = new ByteArrayOutputStream();

				serializationHandler.write(entityContainer.getEntity(), baos);

				EntityData<E> entityData = new EntityData<>();

				entityData.setId(entityContainer.getId());
				entityData.setEntityType(entityContainer.getEntityType());
				entityData.setData(baos.toByteArray());

				persistenceHandler.delete(entityData);

				Map<Class<EntityIndex<E>>, List<EntityIndexHandler<E, EntityIndex<E>>>> handlerGroups = entityStorageRegistry
						.getHandlers(entityContainer.getEntityType()).stream()
						.collect(Collectors.groupingBy(item -> item.getEntityIndexType(), Collectors.toList()));

				for (Entry<Class<EntityIndex<E>>, List<EntityIndexHandler<E, EntityIndex<E>>>> entry : handlerGroups
						.entrySet()) {

					Class<EntityIndex<E>> entityIndexType = entry.getKey();

					if (oldEntityContainer != null) {
						persistenceHandler.delete(SearchQueryBuilder.of(entityIndexType)
								.eq(BaseEntityIndex.Properties.source, entityContainer.toReference()).build());
					}
				}

				transaction.commit();

			} catch (Throwable e) {

				transaction.rollback();

				throw new StorageException("delete failed", e);
			}

		} catch (PersistenceException e) {
			throw new StorageException("delete failed", e);
		}
	}

	@Override
	public <I extends EntityIndex<?>> List<I> search(SearchQuery<I> query) throws StorageException {

		try {

			PersistenceTransaction transaction = persistenceHandler.obtainTransaction();

			try {

				transaction.begin();

				List<I> indexes = persistenceHandler.search(query);

				transaction.commit();

				return indexes;

			} catch (Throwable e) {

				transaction.rollback();

				throw new StorageException("search failed", e);
			}

		} catch (PersistenceException e) {
			throw new StorageException("search failed", e);
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
