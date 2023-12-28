package com.ijioio.aes.sandbox.test.persistence.entity.data;

import java.nio.charset.StandardCharsets;

import com.ijioio.aes.core.EntityData;
import com.ijioio.aes.sandbox.test.persistence.entity.data.BaseEntityDataDeletePersistenceTest.Some;

public class EntityDataDeletePersistenceTest extends BaseEntityDataDeletePersistenceTest<Some> {

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "entity-data-delete-persistence.sql";
	}

	@Override
	protected EntityData<Some> createEntityData() {

		EntityData<Some> entityData = new EntityData<>();

		entityData.setId("entity-data-delete-persistence-entity-data");
		entityData.setEntityType(Some.class);
		entityData.setData("data".getBytes(StandardCharsets.UTF_8));

		return entityData;
	}
}
