package com.ijioio.aes.persistence.jdbc.h2.test.index.property;

import com.ijioio.aes.persistence.jdbc.h2.test.H2DataSourceProvider;
import com.ijioio.aes.persistence.jdbc.h2.test.H2HandlerProvider;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyStringListCreatePersistenceTest;

public class H2PropertyStringListCreatePersistenceTest extends BasePropertyStringListCreatePersistenceTest
		implements H2DataSourceProvider, H2HandlerProvider {

	@Override
	protected String getSqlScriptPath() throws Exception {
		return "persistence/index/property/property-string-list-create-persistence.sql";
	}
}
