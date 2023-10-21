package com.ijioio.aes.core.persistence.jdbc.value.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.core.persistence.PersistenceException;

public abstract class JdbcBasePersistenceValueHandler<T> implements JdbcPersistenceValueHandler<T> {

	protected Collection<T> getCollection(TypeReference<? extends Collection<T>> type, Collection<T> values)
			throws PersistenceException {

		try {

			if (values != null) {
				return values;
			}

			Class<? extends Collection<T>> collectionType = type.getRawType();

			if (Collection.class.isAssignableFrom(collectionType)) {
				return new ArrayList<>();
			}

			if (List.class.isAssignableFrom(collectionType)) {
				return new ArrayList<>();
			}

			if (Set.class.isAssignableFrom(collectionType)) {
				return new LinkedHashSet<>();
			}

			return collectionType.newInstance();

		} catch (IllegalAccessException | InstantiationException e) {
			throw new PersistenceException(e);
		}
	}
}
