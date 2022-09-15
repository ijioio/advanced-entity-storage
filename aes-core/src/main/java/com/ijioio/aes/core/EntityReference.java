package com.ijioio.aes.core;

/**
 * Convenient class for holding reference data for the entity.
 */
public final class EntityReference<E extends Entity> {

	/**
	 * Creates entity reference for entity with indicated {@code id} and
	 * {@code type}.
	 *
	 * @param id
	 *            of the entity
	 * @param type
	 *            of the entity
	 * @return entity reference for the entity
	 */
	public static <E extends Entity> EntityReference<E> of(String id,
			Class<E> type) {
		return new EntityReference<>(id, type);
	}

	private final String id;

	private final Class<E> type;

	private EntityReference(String id, Class<E> type) {

		this.id = id;
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public Class<E> getType() {
		return type;
	}

	@Override
	public int hashCode() {

		final int prime = 31;

		int result = 1;

		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		EntityReference<?> other = (EntityReference<?>) obj;

		if (id == null) {

			if (other.id != null) {
				return false;
			}

		} else if (!id.equals(other.id)) {
			return false;
		}

		if (type == null) {

			if (other.type != null) {
				return false;
			}

		} else if (!type.equals(other.type)) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		return "EntityReference [id=" + id + ", type=" + type + "]";
	}
}
