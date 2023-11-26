package com.ijioio.aes.core.persistence;

public interface PersistenceTransaction {

	public void begin() throws PersistenceException;

	public void commit() throws PersistenceException;

	public void rollback() throws PersistenceException;
}
