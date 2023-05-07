package com.ijioio.aes.core;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ijioio.aes.core.serialization.SerializationContext;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.SerializationReader;
import com.ijioio.aes.core.serialization.SerializationWriter;

/**
 * Convenient class for holding reference data for the entity.
 */
public final class EntityReference<E extends Entity> extends BaseEntity {

	/**
	 * Creates entity reference for entity with indicated {@code id} and
	 * {@code type}.
	 *
	 * @param id of the entity
	 * @param type of the entity
	 * @return entity reference for the entity
	 */
	public static <E extends Entity> EntityReference<E> of(String id, Class<E> type) {

		EntityReference<E> entityReference = new EntityReference<>();

		entityReference.setId(id);
		entityReference.setType(type);

		return entityReference;
	}

	private Class<E> type;

	public Class<E> getType() {
		return type;
	}

	public void setType(Class<E> type) {
		this.type = type;
	}

	@Override
	public int hashCode() {

		final int prime = 31;

		int result = super.hashCode();

		result = prime * result + ((type == null) ? 0 : type.hashCode());

		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (!super.equals(obj)) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		EntityReference<?> other = (EntityReference<?>) obj;

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
	public Map<String, SerializationWriter> getWriters(SerializationContext context, SerializationHandler handler) {

		Map<String, SerializationWriter> writers = new LinkedHashMap<>(super.getWriters(context, handler));

		writers.put("type", () -> handler.write(context, "type", type));

		return writers;
	}

	@Override
	public Map<String, SerializationReader> getReaders(SerializationContext context, SerializationHandler handler) {

		Map<String, SerializationReader> readers = new LinkedHashMap<>(super.getReaders(context, handler));

		readers.put("type", () -> type = handler.read(context, type));

		return readers;
	}

	@Override
	public String toString() {
		return "EntityReference [id=" + getId() + ", type=" + type + "]";
	}
}
