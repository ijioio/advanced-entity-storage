package com.ijioio.aes.persistence.jdbc.h2.test.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.ijioio.aes.persistence.jdbc.h2.test.H2DataSourceProvider;
import com.ijioio.aes.persistence.jdbc.h2.test.H2HandlerProvider;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyStringCreatePersistenceTest;

public class H2PropertyStringCreatePersistenceTest extends BasePropertyStringCreatePersistenceTest
		implements H2DataSourceProvider, H2HandlerProvider {

	@Override
	protected Path getSqlScriptPath() throws Exception {
		return Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-string-create-persistence.sql").toURI());
	}
}
