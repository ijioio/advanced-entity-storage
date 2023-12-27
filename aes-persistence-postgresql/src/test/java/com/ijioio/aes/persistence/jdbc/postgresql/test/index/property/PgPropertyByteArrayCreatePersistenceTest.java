package com.ijioio.aes.persistence.jdbc.postgresql.test.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.persistence.jdbc.postgresql.test.PgDataSourceProvider;
import com.ijioio.aes.persistence.jdbc.postgresql.test.PgHandlerProvider;
import com.ijioio.aes.persistence.jdbc.postgresql.test.PgPersistenceTestUtil;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyByteArrayCreatePersistenceTest;

public class PgPropertyByteArrayCreatePersistenceTest extends BasePropertyByteArrayCreatePersistenceTest
		implements PgDataSourceProvider, PgHandlerProvider {

	@Override
	protected Path getSqlScriptPath() throws Exception {
		return Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-byte-array-create-persistence.sql").toURI());
	}

	@Override
	protected void checkPropertyValue(byte[] value, ResultSet resultSet) throws Exception {
		Assertions.assertArrayEquals(value, PgPersistenceTestUtil.getBytes(resultSet, "valueByteArray"));
	}
}
