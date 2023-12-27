package com.ijioio.aes.persistence.jdbc.postgresql.test;

import javax.sql.DataSource;

import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.persistence.jdbc.postgresql.PgPersistenceHandler;
import com.ijioio.aes.persistence.test.fixture.jdbc.HandlerProvider;

public interface PgHandlerProvider extends HandlerProvider {

	@Override
	public default JdbcPersistenceHandler createHandler(DataSource dataSource) throws Exception {
		return new PgPersistenceHandler(dataSource);
	}
}
