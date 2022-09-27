package com.ijioio.aes.core;

import java.time.LocalDateTime;
import java.util.Map;

import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.SerializationReader;
import com.ijioio.aes.core.serialization.SerializationWriter;

/**
 * Convenient class for holding entity.
 */
public class EntityContainer<E extends Entity> extends BaseEntity {

	/**
	 * Creates entity container holding indicated {@code entity}.
	 *
	 * @param entity to be place to the container
	 * @return entity container holding entity
	 */
	@SuppressWarnings("unchecked")
	public static final <E extends Entity> EntityContainer<E> of(E entity) {

		EntityContainer<E> container = new EntityContainer<>();

		container.setEntityType((Class<E>) entity.getClass());
		container.setEntity(entity);

		return container;
	}

	private Class<E> entityType;

	private E entity;

	private LocalDateTime createdAt;

	private LocalDateTime modifiedAt;

	public Class<E> getEntityType() {
		return entityType;
	}

	public void setEntityType(Class<E> entityType) {
		this.entityType = entityType;
	}

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public EntityReference<E> toReference() {
		return EntityReference.of(getId(), getEntityType());
	}

	@Override
	public Map<String, SerializationWriter> getWriters(SerializationContext context, SerializationHandler handler) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public Map<String, SerializationReader> getReaders(SerializationContext context, SerializationHandler handler) {
		throw new RuntimeException("not implemented");
	}

	@Override
	public String toString() {
		return "EntityContainer [id=" + getId() + ", entityType=" + entityType + ", entity=" + entity + ", createdAt="
				+ createdAt + ", modifiedAt=" + modifiedAt + "]";
	}
}
