package com.ijioio.aes.sandbox.test.persistence.entity.data;

import java.nio.file.Paths;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityData;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;

public abstract class BaseEntityDataLoadPersistenceTest<E extends Entity> extends BasePersistenceTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.persistence.entity.data.BaseEntityDataLoadPersistenceTest.Some";
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

		EntityData<E> expectedEntityData = entityData;
		EntityData<E> actualEntityData = handler
				.load(EntityReference.of(entityData.getId(), entityData.getEntityType()));

		check(expectedEntityData, actualEntityData);
	}

	private void check(EntityData<E> expectedEntityData, EntityData<E> actualEntityData) {

		Assertions.assertEquals(expectedEntityData.getId(), actualEntityData.getId());
		Assertions.assertEquals(expectedEntityData.getEntityType(), actualEntityData.getEntityType());
		Assertions.assertArrayEquals(expectedEntityData.getData(), actualEntityData.getData());
	}

	protected abstract String getSqlScriptFileName() throws Exception;

	protected abstract EntityData<E> createEntityData();
}
