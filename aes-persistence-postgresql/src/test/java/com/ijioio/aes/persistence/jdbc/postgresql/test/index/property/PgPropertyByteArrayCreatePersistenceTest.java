package com.ijioio.aes.persistence.jdbc.postgresql.test.index.property;

import java.sql.ResultSet;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.persistence.jdbc.postgresql.test.PgDataSourceProvider;
import com.ijioio.aes.persistence.jdbc.postgresql.test.PgHandlerProvider;
import com.ijioio.aes.persistence.jdbc.postgresql.test.PgPersistenceTestUtil;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyByteArrayCreatePersistenceTest;

public class PgPropertyByteArrayCreatePersistenceTest extends BasePropertyByteArrayCreatePersistenceTest
		implements PgDataSourceProvider, PgHandlerProvider {

	@Override
	protected String getSqlScriptPath() {
		return "persistence/index/property/property-byte-array-create-persistence.sql";
	}

	@Override
	protected void checkPropertyValue(byte[] value, ResultSet resultSet) throws Exception {
		Assertions.assertArrayEquals(value, PgPersistenceTestUtil.getBytes(resultSet, "valueByteArray"));
	}
}
