package com.ijioio.aes.core.persistence;

import java.util.List;

import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.SearchQuery;

public interface PersistenceHandler<C extends PersistenceContext> {

	public void create(C context, EntityIndex<?> index) throws PersistenceException;

	public <I extends EntityIndex<?>> void delete(C context, SearchQuery<I> query) throws PersistenceException;

	public <I extends EntityIndex<?>> List<I> search(C context, SearchQuery<I> query) throws PersistenceException;
}
