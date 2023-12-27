package com.ijioio.aes.persistence.jdbc.postgresql;

import javax.sql.DataSource;

import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandlerProvider;

public class PgPersistenceHanderProvider implements JdbcPersistenceHandlerProvider {

	@Override
	public boolean accept(String productName) {
		return "PostgreSQL".equals(productName);
	}

	@Override
	public JdbcPersistenceHandler create(DataSource dataSource) {
		return new PgPersistenceHandler(dataSource);
	}
}
