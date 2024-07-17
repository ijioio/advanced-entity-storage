package com.ijioio.aes.storage;

import java.util.List;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityContainer;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.SearchQuery;

public interface EntityStorage {

	public <E extends Entity> EntityContainer<E> load(EntityReference<E> entityReference) throws StorageException;

	public <E extends Entity> EntityContainer<E> save(EntityContainer<E> entityContainer) throws StorageException;

	public <E extends Entity> void delete(EntityContainer<E> entityContainer) throws StorageException;

	public <I extends EntityIndex<?>> List<I> search(SearchQuery<I> query) throws StorageException;

	public Transaction createTransaction() throws StorageException;
}
