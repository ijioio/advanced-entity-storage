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
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;

public abstract class BaseEntityDataUpdatePersistenceTest<E extends Entity> extends BasePersistenceTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.persistence.entity.data.BaseEntityDataUpdatePersistenceTest.Some";
	}

	protected JdbcPersistenceHandler handler;

	protected EntityData<E> entityData;

	@BeforeEach
	public void before() throws Exception {

		handler = new JdbcPersistenceHandler(dataSource);

		executeSql(connection, Paths.get(getClass().getClassLoader()
				.getResource(String.format("persistence/entity/data/%s", getSqlScriptFileName())).toURI()));

		entityData = createEntityData();
	}

	@Test
	public void testCreate() throws Exception {

		handler.create(entityData);

		entityData.setData(createUpdatedDataValue());

		handler.update(entityData);

		try (PreparedStatement statement = connection
				.prepareStatement(String.format("select * from %s", EntityData.class.getSimpleName()))) {

			try (ResultSet resultSet = statement.executeQuery()) {

				Assertions.assertTrue(resultSet.next());

				Assertions.assertEquals(entityData.getId(), resultSet.getString("id"));
				Assertions.assertEquals(entityData.getEntityType().getName(), resultSet.getString("entityType"));
				Assertions.assertArrayEquals(entityData.getData(), getBytes(resultSet.getBlob("data")));

				Assertions.assertTrue(resultSet.isLast());
			}
		}
	}

	protected abstract String getSqlScriptFileName() throws Exception;

	protected abstract EntityData<E> createEntityData();

	protected abstract byte[] createUpdatedDataValue();
}
