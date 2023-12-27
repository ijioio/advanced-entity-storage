package com.ijioio.aes.persistence.test.fixture.jdbc;

import javax.sql.DataSource;

import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;

public interface HandlerProvider {

	public JdbcPersistenceHandler createHandler(DataSource dataSource) throws Exception;

}
