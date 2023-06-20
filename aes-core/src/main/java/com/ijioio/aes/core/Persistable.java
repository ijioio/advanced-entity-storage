package com.ijioio.aes.core;

import com.ijioio.aes.core.persistence.PersistenceContext;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.PersistenceHandler;

public interface Persistable {

	public void insert(PersistenceContext context, PersistenceHandler handler) throws PersistenceException;
}
