package com.ijioio.aes.core.persistence;

import java.util.List;

@FunctionalInterface
public interface PersistenceColumnProvider {

	public List<String> getColumns() throws PersistenceException;
}
