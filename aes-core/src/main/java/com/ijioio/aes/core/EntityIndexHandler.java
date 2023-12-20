package com.ijioio.aes.core;

import java.util.Collection;

public interface EntityIndexHandler<E extends Entity, I extends EntityIndex<E>> {

	public Class<E> getEntityType();

	public Class<I> getEntityIndexType();

	public Collection<I> create(EntityContainer<E> entityContainer) throws Exception;
}
