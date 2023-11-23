package com.ijioio.aes.sandbox.test.entIty.storage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.ijioio.aes.sandbox.test.BaseTest;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class BaseEntityStorageTest extends BaseTest {

	protected HikariDataSource dataSource;

	@BeforeEach
	public void setup() throws Exception {

		HikariConfig config = new HikariConfig();

//		config.setJdbcUrl(System.getProperty("db"));
//		config.setUsername(System.getProperty("user"));
//		config.setPassword(System.getProperty("password"));
		config.setJdbcUrl("jdbc:h2:~/test");
		config.setUsername("su");
		config.setPassword("");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		dataSource = new HikariDataSource(config);
	}

	@AfterEach
	public void shutdown() throws Exception {

		if (dataSource != null) {
			dataSource.close();
		}
	}
}
