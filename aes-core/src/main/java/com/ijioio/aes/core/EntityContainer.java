package com.ijioio.aes.core;

import java.time.LocalDateTime;

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

	/**
	 * Creates entity container having indicated {@code id} and holding indicated
	 * {@code entity}.
	 *
	 * @param id     of the container
	 * @param entity to be place to the container
	 * @return entity container holding entity
	 */
	@SuppressWarnings("unchecked")
	public static final <E extends Entity> EntityContainer<E> of(String id, E entity) {

		EntityContainer<E> container = new EntityContainer<>();

		container.setId(id);
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
	public String toString() {
		return "EntityContainer [id=" + getId() + ", entityType=" + entityType + ", entity=" + entity + ", createdAt="
				+ createdAt + ", modifiedAt=" + modifiedAt + "]";
	}
}
