package com.ijioio.aes.core;

/**
 * Interface defining index of the entity. Entity index contains some entity
 * properties.
 */
public interface EntityIndex<E extends BaseEntity> extends Identity {

	public EntityReference<E> getSource();
}
