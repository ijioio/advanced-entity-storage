package com.ijioio.aes.persistence.jdbc.h2.test.index.property;

import com.ijioio.aes.persistence.jdbc.h2.test.H2DataSourceProvider;
import com.ijioio.aes.persistence.jdbc.h2.test.H2HandlerProvider;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyStringSearchPersistenceTest;

public class H2PropertyStringSearchPersistenceTest extends BasePropertyStringSearchPersistenceTest
		implements H2DataSourceProvider, H2HandlerProvider {

	@Override
	protected String getSqlScriptPath() {
		return "persistence/index/property/property-string-search-persistence.sql";
	}
}
