package com.ijioio.aes.persistence.jdbc.h2.test;

import javax.sql.DataSource;

import com.ijioio.aes.persistence.test.fixture.jdbc.DataSourceProvider;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public interface H2DataSourceProvider extends DataSourceProvider {

	public default DataSource getDataSource() throws Exception {

		HikariConfig config = new HikariConfig();

		config.setJdbcUrl("jdbc:h2:~/test");
		config.setUsername("su");
		config.setPassword("");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		return new HikariDataSource(config);
	}
}
