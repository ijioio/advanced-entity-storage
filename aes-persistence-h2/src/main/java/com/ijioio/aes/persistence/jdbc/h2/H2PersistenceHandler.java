package com.ijioio.aes.persistence.jdbc.h2;

import javax.sql.DataSource;

import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;

public class H2PersistenceHandler extends JdbcPersistenceHandler {

	public H2PersistenceHandler(DataSource dataSource) {
		super(dataSource);
	}
}
