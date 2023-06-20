package com.ijioio.aes.core.persistence;

@FunctionalInterface
public interface PersistenceWriter {

	public void write() throws PersistenceException;
}
