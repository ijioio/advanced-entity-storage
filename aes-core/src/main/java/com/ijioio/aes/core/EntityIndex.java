package com.ijioio.aes.core;

import java.util.Collection;

/**
 * Interface defining index of the entity. Entity index contains some entity
 * properties.
 */
public interface EntityIndex<E extends Entity> extends Identity, Introspectable {

	public EntityReference<E> getSource();

	public Collection<Property<?>> getProperties();
}
