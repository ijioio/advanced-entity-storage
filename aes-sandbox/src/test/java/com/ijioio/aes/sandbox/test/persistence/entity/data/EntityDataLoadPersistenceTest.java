package com.ijioio.aes.sandbox.test.persistence.entity.data;

import java.nio.charset.StandardCharsets;

import com.ijioio.aes.core.entity.storage.EntityData;
import com.ijioio.aes.sandbox.test.persistence.entity.data.BaseEntityDataLoadPersistenceTest.Some;

public class EntityDataLoadPersistenceTest extends BaseEntityDataLoadPersistenceTest<Some> {

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "entity-data-load-persistence.sql";
	}

	@Override
	protected EntityData<Some> createEntityData() {

		EntityData<Some> entityData = new EntityData<>();

		entityData.setId("entity-data-load-persistence-entity-data");
		entityData.setEntityType(Some.class);
		entityData.setData("data".getBytes(StandardCharsets.UTF_8));

		return entityData;
	}
}
