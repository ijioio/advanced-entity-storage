package com.ijioio.aes.persistence.jdbc.postgresql.test.index.property;

import com.ijioio.aes.persistence.jdbc.postgresql.test.PgDataSourceProvider;
import com.ijioio.aes.persistence.jdbc.postgresql.test.PgHandlerProvider;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyStringListCreatePersistenceTest;

public class PgPropertyStringListCreatePersistenceTest extends BasePropertyStringListCreatePersistenceTest
		implements PgDataSourceProvider, PgHandlerProvider {

	@Override
	protected String getSqlScriptPath() throws Exception {
		return "persistence/index/property/property-string-list-create-persistence.sql";
	}
}
