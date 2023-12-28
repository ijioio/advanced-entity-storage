package com.ijioio.aes.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.EntityIndexHandler;

public class EntityStorageRegistry {

	private final Map<Class<?>, Collection<EntityIndexHandler<?, ?>>> handlers = new HashMap<>();

	public void register(EntityIndexHandler<?, ?> handler) {
		handlers.computeIfAbsent(handler.getEntityType(), key -> new ArrayList<>()).add(handler);
	}

	@SuppressWarnings("unchecked")
	public <E extends Entity, I extends EntityIndex<E>> Collection<EntityIndexHandler<E, I>> getHandlers(
			Class<E> type) {
		return (Collection<EntityIndexHandler<E, I>>) (Collection<?>) Optional.ofNullable(handlers.get(type))
				.orElse(Collections.emptyList());
	}
}
