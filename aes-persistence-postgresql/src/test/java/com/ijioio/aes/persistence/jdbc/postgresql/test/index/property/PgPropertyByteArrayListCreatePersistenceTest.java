package com.ijioio.aes.persistence.jdbc.postgresql.test.index.property;

import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.persistence.jdbc.postgresql.test.PgDataSourceProvider;
import com.ijioio.aes.persistence.jdbc.postgresql.test.PgHandlerProvider;
import com.ijioio.aes.persistence.jdbc.postgresql.test.PgPersistenceTestUtil;
import com.ijioio.aes.persistence.test.fixture.jdbc.PersistenceTestUtil;
import com.ijioio.aes.persistence.test.fixture.jdbc.PersistenceTestUtil.ByteArray;
import com.ijioio.aes.persistence.test.fixture.jdbc.index.property.BasePropertyByteArrayListCreatePersistenceTest;

public class PgPropertyByteArrayListCreatePersistenceTest extends BasePropertyByteArrayListCreatePersistenceTest
		implements PgDataSourceProvider, PgHandlerProvider {

	@Override
	protected String getSqlScriptPath() {
		return "persistence/index/property/property-byte-array-list-create-persistence.sql";
	}

	@Override
	protected void checkPropertyValue(List<byte[]> value, ResultSet resultSet) throws Exception {
		Assertions.assertEquals(
				Optional.ofNullable(value)
						.map(item -> item.stream().map(element -> ByteArray.of(element)).collect(Collectors.toList()))
						.orElse(null),
				Optional.ofNullable(PersistenceTestUtil.getList(resultSet, "valueByteArrayList"))
						.map(item -> item.stream()
								.map(element -> ByteArray.of(PgPersistenceTestUtil.getBytes(resultSet, (Long) element)))
								.collect(Collectors.toList()))
						.orElse(null));
	}
}
