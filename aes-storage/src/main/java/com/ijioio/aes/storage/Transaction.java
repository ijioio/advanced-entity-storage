package com.ijioio.aes.storage;

public interface Transaction {

	public void begin() throws StorageException;

	public void commit() throws StorageException;

	public void rollback() throws StorageException;
}
