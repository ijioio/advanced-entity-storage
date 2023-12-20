package com.ijioio.aes.core.persistence;

import java.util.List;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.entity.storage.EntityData;

public interface PersistenceHandler {

	public PersistenceTransaction createTransaction() throws PersistenceException;

	public PersistenceTransaction obtainTransaction() throws PersistenceException;

	public <E extends Entity> void create(EntityData<E> data) throws PersistenceException;

	public <E extends Entity> void update(EntityData<E> data) throws PersistenceException;

	public <E extends Entity> void delete(EntityData<E> data) throws PersistenceException;

	public <E extends Entity> EntityData<E> load(EntityReference<E> reference) throws PersistenceException;

	public void create(EntityIndex<?> index) throws PersistenceException;

	public <I extends EntityIndex<?>> void delete(SearchQuery<I> query) throws PersistenceException;

	public <I extends EntityIndex<?>> List<I> search(SearchQuery<I> query) throws PersistenceException;
}
