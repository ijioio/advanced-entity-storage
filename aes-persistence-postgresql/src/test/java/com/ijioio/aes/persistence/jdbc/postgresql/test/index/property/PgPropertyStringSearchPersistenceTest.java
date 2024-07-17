package com.ijioio.aes.persistence.jdbc.postgresql.test.index.property;

import com.ijioio.aes.persistence.jdbc.postgresql.test.PgDataSourceProvider;
import com.ijioio.aes.persistence.jdbc.postgresql.test.PgHandlerProvider;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyStringSearchPersistenceTest;

public class PgPropertyStringSearchPersistenceTest extends BasePropertyStringSearchPersistenceTest
		implements PgDataSourceProvider, PgHandlerProvider {

	@Override
	protected String getSqlScriptPath() {
		return "persistence/index/property/property-string-search-persistence.sql";
	}
}
