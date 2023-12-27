package com.ijioio.aes.persistence.jdbc.postgresql.test.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.ijioio.aes.persistence.jdbc.postgresql.test.PgDataSourceProvider;
import com.ijioio.aes.persistence.jdbc.postgresql.test.PgHandlerProvider;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyStringCreatePersistenceTest;

public class PgPropertyStringCreatePersistenceTest extends BasePropertyStringCreatePersistenceTest
		implements PgDataSourceProvider, PgHandlerProvider {

	@Override
	protected Path getSqlScriptPath() throws Exception {
		return Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-string-create-persistence.sql").toURI());
	}
}
