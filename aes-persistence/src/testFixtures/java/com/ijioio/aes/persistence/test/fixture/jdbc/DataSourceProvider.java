package com.ijioio.aes.persistence.test.fixture.jdbc;

import javax.sql.DataSource;

public interface DataSourceProvider {

	public DataSource getDataSource() throws Exception;
}
