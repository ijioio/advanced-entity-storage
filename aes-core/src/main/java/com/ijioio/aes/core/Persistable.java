package com.ijioio.aes.core;

import com.ijioio.aes.core.persistence.PersistenceContext;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.PersistenceHandler;

public interface Persistable {

	public void insert(PersistenceHandler handler, PersistenceContext context) throws PersistenceException;

	public void update(PersistenceHandler handler, PersistenceContext context) throws PersistenceException;

	public void delete(PersistenceHandler handler, PersistenceContext context) throws PersistenceException;
}
