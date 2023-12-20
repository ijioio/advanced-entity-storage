package com.ijioio.aes.core;

/**
 * Interface defining index of the entity. Entity index contains some entity
 * properties.
 */
public interface EntityIndex<E extends Entity> extends Identity, Introspectable {

	public EntityReference<E> getSource();

	public void setSource(EntityReference<E> entityReference);
}
