package com.ijioio.aes.persistence.jdbc.h2.test.index.property;

import com.ijioio.aes.persistence.jdbc.h2.test.H2DataSourceProvider;
import com.ijioio.aes.persistence.jdbc.h2.test.H2HandlerProvider;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyByteArrayCreatePersistenceTest;

public class H2PropertyByteArrayCreatePersistenceTest extends BasePropertyByteArrayCreatePersistenceTest
		implements H2DataSourceProvider, H2HandlerProvider {

	@Override
	protected String getSqlScriptPath() throws Exception {
		return "persistence/index/property/property-byte-array-create-persistence.sql";
	}
}
