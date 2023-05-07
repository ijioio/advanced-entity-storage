package com.ijioio.aes.core;

/**
 * Base class for all entity indexes.
 */
public abstract class BaseEntityIndex<E extends Entity> implements EntityIndex<E> {

	private String id;

	private EntityReference<E> source;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public EntityReference<E> getSource() {
		return source;
	}

	public void setSource(EntityReference<E> source) {
		this.source = source;
	}
}
