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
import com.ijioio.test.model.DeleteEntityStorage;
import com.ijioio.test.model.DeleteEntityStorageIndex;

public class DeleteEntityStorageTest
		extends BaseDeleteEntityStorageTest<DeleteEntityStorage, DeleteEntityStorageIndex> {

	@Entity( //
			name = DeleteEntityStoragePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "number", type = Type.INT) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = DeleteEntityStoragePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "number", type = Type.INT) //
							} //
					) //
			} //
	)
	public static interface DeleteEntityStoragePrototype {

		public static final String NAME = "com.ijioio.test.model.DeleteEntityStorage";

		public static final String INDEX_NAME = "com.ijioio.test.model.DeleteEntityStorageIndex";
	}

	public static class DeleteEntityStorageIndexHandler
			implements EntityIndexHandler<DeleteEntityStorage, DeleteEntityStorageIndex> {

		private final int i;

		private final int count;

		public DeleteEntityStorageIndexHandler(int i, int count) {

			this.i = i;
			this.count = count;
		}

		@Override
		public Class<DeleteEntityStorage> getEntityType() {
			return DeleteEntityStorage.class;
		}

		@Override
		public Class<DeleteEntityStorageIndex> getEntityIndexType() {
			return DeleteEntityStorageIndex.class;
		}

		@Override
		public Collection<DeleteEntityStorageIndex> create(EntityContainer<DeleteEntityStorage> entityContainer)
				throws Exception {

			DeleteEntityStorage entity = entityContainer.getEntity();

			List<DeleteEntityStorageIndex> indexes = new ArrayList<>();

			for (int j = 0; j < count; j++) {

				DeleteEntityStorageIndex index = new DeleteEntityStorageIndex();

				index.setId(String.format("delete-entity-storage-index-%s-%s-%s", entity.getNumber(), i + 1, j + 1));
				index.setSource(entityContainer.toReference());
				index.setNumber(Integer.parseInt(String.format("%s%s%s", entity.getNumber(), i + 1, j + 1)));

				indexes.add(index);
			}

			return indexes;
		}
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "delete-entity-storage.sql";
	}

	@Override
	protected Class<DeleteEntityStorageIndex> getIndexClass() {
		return DeleteEntityStorageIndex.class;
	}

	@Override
	protected List<EntityContainer<DeleteEntityStorage>> createEntityContainers() {

		List<EntityContainer<DeleteEntityStorage>> entityContainers = new ArrayList<>();

		int count = random.nextInt(CONTAINERS_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			DeleteEntityStorage entity = new DeleteEntityStorage();

			entity.setId(String.format("delete-entity-storage-entity-%s", i + 1));
			entity.setNumber(i + 1);

			entityContainers
					.add(EntityContainer.of(String.format("delete-entity-storage-entity-container-%s", i + 1), entity));
		}

		return entityContainers;
	}

	@Override
	protected List<EntityIndexHandler<DeleteEntityStorage, DeleteEntityStorageIndex>> createIndexHandlers() {

		List<EntityIndexHandler<DeleteEntityStorage, DeleteEntityStorageIndex>> handlers = new ArrayList<>();

		int count = random.nextInt(HANDLERS_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {
			handlers.add(new DeleteEntityStorageIndexHandler(i, random.nextInt(INDEX_MAX_COUNT) + 1));
		}

		return handlers;
	}

	@Override
	protected void checkEntity(DeleteEntityStorage expectedEntity, DeleteEntityStorage actualEntity) {
		Assertions.assertEquals(expectedEntity.getNumber(), actualEntity.getNumber());
	}

	@Override
	protected void checkIndex(DeleteEntityStorageIndex expectedIndex, DeleteEntityStorageIndex actualIndex) {
		Assertions.assertEquals(expectedIndex.getNumber(), actualIndex.getNumber());
	}
}
