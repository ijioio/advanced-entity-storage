package com.ijioio.aes.sandbox.test.persistence.entity.data;

import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityData;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.persistence.jdbc.h2.H2PersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;

public abstract class BaseEntityDataDeletePersistenceTest<E extends Entity> extends BasePersistenceTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.persistence.entity.data.BaseEntityDataDeletePersistenceTest.Some";
	}

	protected JdbcPersistenceHandler handler;

	protected EntityData<E> entityData;

	@BeforeEach
	public void before() throws Exception {

		handler = new H2PersistenceHandler(dataSource);

		executeSql(connection, Paths.get(getClass().getClassLoader()
				.getResource(String.format("persistence/entity/data/%s", getSqlScriptFileName())).toURI()));

		entityData = createEntityData();
	}

	@Test
	public void testCreate() throws Exception {

		handler.create(entityData);
		handler.delete(entityData);

		try (PreparedStatement statement = connection
				.prepareStatement(String.format("select * from %s", EntityData.class.getSimpleName()))) {

			try (ResultSet resultSet = statement.executeQuery()) {

				Assertions.assertFalse(resultSet.next());
			}
		}
	}

	protected abstract String getSqlScriptFileName() throws Exception;

	protected abstract EntityData<E> createEntityData();
}
