package com.ijioio.aes.persistence.jdbc.postgresql.test.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.ijioio.aes.persistence.jdbc.postgresql.test.PgDataSourceProvider;
import com.ijioio.aes.persistence.jdbc.postgresql.test.PgHandlerProvider;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyStringListCreatePersistenceTest;

public class PgPropertyStringListCreatePersistenceTest extends BasePropertyStringListCreatePersistenceTest
		implements PgDataSourceProvider, PgHandlerProvider {

	@Override
	protected Path getSqlScriptPath() throws Exception {
		return Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-string-list-create-persistence.sql").toURI());
	}
}
