package com.ijioio.aes.core.persistence;

import java.util.List;

import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.SearchQuery;

public interface PersistenceHandler {

	public PersistenceTransaction createTransaction() throws PersistenceException;

	public void create(EntityIndex<?> index) throws PersistenceException;

	public <I extends EntityIndex<?>> void delete(SearchQuery<I> query) throws PersistenceException;

	public <I extends EntityIndex<?>> List<I> search(SearchQuery<I> query) throws PersistenceException;
}
