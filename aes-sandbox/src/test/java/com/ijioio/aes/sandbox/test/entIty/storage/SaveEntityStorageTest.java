package com.ijioio.aes.sandbox.test.entIty.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityContainer;
import com.ijioio.aes.core.EntityIndexHandler;
import com.ijioio.test.model.SaveEntityStorage;
import com.ijioio.test.model.SaveEntityStorageIndex;

public class SaveEntityStorageTest extends BaseSaveEntityStorageTest<SaveEntityStorage, SaveEntityStorageIndex> {

	@Entity( //
			name = SaveEntityStoragePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "number", type = Type.INT) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = SaveEntityStoragePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "number", type = Type.INT) //
							} //
					) //
			} //
	)
	public static interface SaveEntityStoragePrototype {

		public static final String NAME = "com.ijioio.test.model.SaveEntityStorage";

		public static final String INDEX_NAME = "com.ijioio.test.model.SaveEntityStorageIndex";
	}

	public static class SaveEntityStorageIndexHandler
			implements EntityIndexHandler<SaveEntityStorage, SaveEntityStorageIndex> {

		private final int i;

		private final int count;

		public SaveEntityStorageIndexHandler(int i, int count) {

			this.i = i;
			this.count = count;
		}

		@Override
		public Class<SaveEntityStorage> getEntityType() {
			return SaveEntityStorage.class;
		}

		@Override
		public Class<SaveEntityStorageIndex> getEntityIndexType() {
			return SaveEntityStorageIndex.class;
		}

		@Override
		public Collection<SaveEntityStorageIndex> create(EntityContainer<SaveEntityStorage> entityContainer)
				throws Exception {

			SaveEntityStorage entity = entityContainer.getEntity();

			List<SaveEntityStorageIndex> indexes = new ArrayList<>();

			for (int j = 0; j < count; j++) {

				SaveEntityStorageIndex index = new SaveEntityStorageIndex();

				index.setId(String.format("save-entity-storage-index-%s-%s-%s", entity.getNumber(), i + 1, j + 1));
				index.setSource(entityContainer.toReference());
				index.setNumber(Integer.parseInt(String.format("%s%s%s", entity.getNumber(), i + 1, j + 1)));

				indexes.add(index);
			}

			return indexes;
		}
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "save-entity-storage.sql";
	}

	@Override
	protected Class<SaveEntityStorageIndex> getIndexClass() {
		return SaveEntityStorageIndex.class;
	}

	@Override
	protected List<EntityContainer<SaveEntityStorage>> createEntityContainers() {

		List<EntityContainer<SaveEntityStorage>> entityContainers = new ArrayList<>();

		int count = random.nextInt(CONTAINERS_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			SaveEntityStorage entity = new SaveEntityStorage();

			entity.setId(String.format("save-entity-storage-entity-%s", i + 1));
			entity.setNumber(i + 1);

			entityContainers
					.add(EntityContainer.of(String.format("save-entity-storage-entity-container-%s", i + 1), entity));
		}

		return entityContainers;
	}

	@Override
	protected void updateEntity(SaveEntityStorage entity) {
		entity.setNumber(entity.getNumber() + CONTAINERS_MAX_COUNT);
	}

	@Override
	protected List<EntityIndexHandler<SaveEntityStorage, SaveEntityStorageIndex>> createIndexHandlers() {

		List<EntityIndexHandler<SaveEntityStorage, SaveEntityStorageIndex>> handlers = new ArrayList<>();

		int count = random.nextInt(HANDLERS_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {
			handlers.add(new SaveEntityStorageIndexHandler(i, random.nextInt(INDEX_MAX_COUNT) + 1));
		}

		return handlers;
	}

	@Override
	protected void checkEntity(SaveEntityStorage expectedEntity, SaveEntityStorage actualEntity) {
		Assertions.assertEquals(expectedEntity.getNumber(), actualEntity.getNumber());
	}

	@Override
	protected void checkIndex(SaveEntityStorageIndex expectedIndex, SaveEntityStorageIndex actualIndex) {
		Assertions.assertEquals(expectedIndex.getNumber(), actualIndex.getNumber());
	}
}
