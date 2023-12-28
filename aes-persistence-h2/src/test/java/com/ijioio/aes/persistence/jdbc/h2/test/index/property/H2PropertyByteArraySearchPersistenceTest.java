package com.ijioio.aes.persistence.jdbc.h2.test.index.property;

import com.ijioio.aes.persistence.jdbc.h2.test.H2DataSourceProvider;
import com.ijioio.aes.persistence.jdbc.h2.test.H2HandlerProvider;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyByteArraySearchPersistenceTest;

public class H2PropertyByteArraySearchPersistenceTest extends BasePropertyByteArraySearchPersistenceTest
		implements H2DataSourceProvider, H2HandlerProvider {

	@Override
	protected String getSqlScriptPath() {
		return "persistence/index/property/property-byte-array-search-persistence.sql";
	}
}
