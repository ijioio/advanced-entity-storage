package com.ijioio.aes.persistence.jdbc.postgresql;

import javax.sql.DataSource;

import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.persistence.jdbc.postgresql.value.handler.PgByteArrayPersistenceValueHandler;

public class PgPersistenceHandler extends JdbcPersistenceHandler {

	public PgPersistenceHandler(DataSource dataSource) {

		super(dataSource);

		registerValueHandler(new PgByteArrayPersistenceValueHandler());
	}
}
