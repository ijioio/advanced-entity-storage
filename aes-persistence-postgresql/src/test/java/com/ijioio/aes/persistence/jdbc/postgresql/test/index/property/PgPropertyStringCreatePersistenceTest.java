package com.ijioio.aes.persistence.jdbc.postgresql.test.index.property;

import com.ijioio.aes.persistence.jdbc.postgresql.test.PgDataSourceProvider;
import com.ijioio.aes.persistence.jdbc.postgresql.test.PgHandlerProvider;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyStringCreatePersistenceTest;

public class PgPropertyStringCreatePersistenceTest extends BasePropertyStringCreatePersistenceTest
		implements PgDataSourceProvider, PgHandlerProvider {

	@Override
	protected String getSqlScriptPath() {
		return "persistence/index/property/property-string-create-persistence.sql";
	}
}
