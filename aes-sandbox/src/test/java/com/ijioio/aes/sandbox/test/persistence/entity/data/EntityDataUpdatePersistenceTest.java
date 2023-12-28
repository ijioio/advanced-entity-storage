package com.ijioio.aes.sandbox.test.persistence.entity.data;

import java.nio.charset.StandardCharsets;

import com.ijioio.aes.core.EntityData;
import com.ijioio.aes.sandbox.test.persistence.entity.data.BaseEntityDataUpdatePersistenceTest.Some;

public class EntityDataUpdatePersistenceTest extends BaseEntityDataUpdatePersistenceTest<Some> {

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "entity-data-update-persistence.sql";
	}

	@Override
	protected EntityData<Some> createEntityData() {

		EntityData<Some> entityData = new EntityData<>();

		entityData.setId("entity-data-update-persistence-entity-data");
		entityData.setEntityType(Some.class);
		entityData.setData("data1".getBytes(StandardCharsets.UTF_8));

		return entityData;
	}

	@Override
	protected byte[] createUpdatedDataValue() {
		return "data2".getBytes(StandardCharsets.UTF_8);
	}
}
