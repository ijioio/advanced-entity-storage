package com.ijioio.aes.persistence.jdbc.h2.test;

import javax.sql.DataSource;

import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.persistence.jdbc.h2.H2PersistenceHandler;
import com.ijioio.aes.persistence.test.fixture.jdbc.HandlerProvider;

public interface H2HandlerProvider extends HandlerProvider {

	@Override
	public default JdbcPersistenceHandler createHandler(DataSource dataSource) throws Exception {
		return new H2PersistenceHandler(dataSource);
	}
}
