package com.ijioio.aes.persistence.jdbc.h2;

import javax.sql.DataSource;

import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandlerProvider;

public class H2PersistenceHanderProvider implements JdbcPersistenceHandlerProvider {

	@Override
	public boolean accept(String productName) {
		return "H2".equals(productName);
	}

	@Override
	public JdbcPersistenceHandler create(DataSource dataSource) {
		return new H2PersistenceHandler(dataSource);
	}
}
