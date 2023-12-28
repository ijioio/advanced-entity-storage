package com.ijioio.aes.persistence.jdbc;

import javax.sql.DataSource;

public interface JdbcPersistenceHandlerProvider {

	public boolean accept(String productName);

	public JdbcPersistenceHandler create(DataSource dataSource);
}
