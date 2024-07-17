package com.ijioio.aes.persistence.jdbc.postgresql.test;

import javax.sql.DataSource;

import com.ijioio.aes.persistence.test.fixture.jdbc.DataSourceProvider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public interface PgDataSourceProvider extends DataSourceProvider {

	@Override
	public default DataSource getDataSource() throws Exception {

		HikariConfig config = new HikariConfig();

		config.setJdbcUrl(System.getProperty("db"));
		config.setUsername(System.getProperty("user"));
		config.setPassword(System.getProperty("password"));

		return new HikariDataSource(config);
	}
}
