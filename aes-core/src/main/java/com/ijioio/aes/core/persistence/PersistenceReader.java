package com.ijioio.aes.core.persistence;

@FunctionalInterface
public interface PersistenceReader {

	public void read() throws PersistenceException;
}
