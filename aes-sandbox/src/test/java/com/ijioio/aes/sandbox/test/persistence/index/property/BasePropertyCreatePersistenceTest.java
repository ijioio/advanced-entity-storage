package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;

public abstract class BasePropertyCreatePersistenceTest<I extends EntityIndex<?>, V> extends BasePersistenceTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertyCreatePersistenceTest.Some";
	}

	protected JdbcPersistenceHandler handler;

	protected I index;

	@BeforeEach
	public void before() throws Exception {

		handler = new JdbcPersistenceHandler();

		executeSql(connection, Paths.get(getClass().getClassLoader()
				.getResource(String.format("persistence/index/property/%s", getSqlScriptFileName())).toURI()));

		index = createIndex();
	}

	@Test
	public void testCreate() throws Exception {

		handler.create(JdbcPersistenceContext.of(connection), index);

		try (PreparedStatement statement = connection
				.prepareStatement(String.format("select * from %s", getTableName()))) {

			try (ResultSet resultSet = statement.executeQuery()) {

				Assertions.assertTrue(resultSet.next());

				Assertions.assertEquals(index.getId(), resultSet.getString("id"));
				Assertions.assertEquals(index.getSource().getId(), resultSet.getString("sourceId"));
				Assertions.assertEquals(index.getSource().getType().getName(), resultSet.getString("sourceType"));

				checkPropertyValue(index, resultSet);

				Assertions.assertTrue(resultSet.isLast());
			}
		}
	}

	@Test
	public void testCreateNull() throws Exception {

		setPropertyValue(index, null);

		handler.create(JdbcPersistenceContext.of(connection), index);

		try (PreparedStatement statement = connection
				.prepareStatement(String.format("select * from %s", getTableName()))) {

			try (ResultSet resultSet = statement.executeQuery()) {

				Assertions.assertTrue(resultSet.next());

				Assertions.assertEquals(index.getId(), resultSet.getString("id"));
				Assertions.assertEquals(index.getSource().getId(), resultSet.getString("sourceId"));
				Assertions.assertEquals(index.getSource().getType().getName(), resultSet.getString("sourceType"));

				checkPropertyValue(index, resultSet);

				Assertions.assertTrue(resultSet.isLast());
			}
		}
	}

	protected abstract String getSqlScriptFileName() throws Exception;

	protected abstract String getTableName();

	protected abstract I createIndex();

	protected abstract V getPropertyValue(I index);

	protected abstract void setPropertyValue(I index, V value);

	protected abstract void checkPropertyValue(I index, ResultSet resultSet) throws Exception;
}
