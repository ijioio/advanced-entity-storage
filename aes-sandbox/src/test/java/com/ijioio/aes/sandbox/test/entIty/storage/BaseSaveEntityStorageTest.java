package com.ijioio.aes.sandbox.test.entIty.storage;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.BaseEntityIndex;
import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityContainer;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.EntityIndexHandler;
import com.ijioio.aes.core.Order;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.storage.EntityStorage;
import com.ijioio.aes.storage.EntityStorageRegistry;
import com.ijioio.aes.storage.standard.StandardEntityStorage;

public abstract class BaseSaveEntityStorageTest<E extends Entity, I extends EntityIndex<E>>
		extends BaseEntityStorageTest {

	protected static int CONTAINERS_MAX_COUNT = 3;

	protected static int HANDLERS_MAX_COUNT = 3;

	protected static int INDEX_MAX_COUNT = 9;

	protected JdbcPersistenceHandler persistenceHandler;

	protected SerializationHandler serializationHandler;

	protected EntityStorageRegistry entityStorageRegistry;

	protected EntityStorage entityStorage;

	protected final List<EntityContainer<E>> entityContainers = new ArrayList<>();

	protected final List<EntityIndexHandler<E, I>> indexHandlers = new ArrayList<>();

	@BeforeEach
	public void before() throws Exception {

		persistenceHandler = new JdbcPersistenceHandler(dataSource);
		serializationHandler = new XmlSerializationHandler();
		entityStorageRegistry = new EntityStorageRegistry();

		entityStorage = new StandardEntityStorage(dataSource, persistenceHandler, serializationHandler,
				entityStorageRegistry);

		executeSql(connection, Paths.get(getClass().getClassLoader()
				.getResource(String.format("entity/storage/%s", getSqlScriptFileName())).toURI()));

		entityContainers.clear();
		entityContainers.addAll(createEntityContainers());

		indexHandlers.clear();
		indexHandlers.addAll(createIndexHandlers());

		for (EntityIndexHandler<E, I> indexHandler : indexHandlers) {
			entityStorageRegistry.register(indexHandler);
		}
	}

	@Test
	public void testSave() throws Exception {

		for (int i = 0; i < entityContainers.size(); i++) {

			EntityContainer<E> entityContainer = entityContainers.get(i);

			EntityContainer<E> expectedEntityContainer = entityStorage.save(entityContainer);
			EntityContainer<E> actualEntityContainer = entityStorage.load(entityContainer.toReference());

			check(expectedEntityContainer, actualEntityContainer);

			List<I> expectedIndexes = createIndexes(entityContainer);
			List<I> actualIndexes = entityStorage.search(SearchQueryBuilder.of(getIndexClass())
					.eq(BaseEntityIndex.Properties.source, entityContainer.toReference())
					.sorting(BaseEntityIndex.Properties.id, Order.ASC).build());

			check(expectedIndexes, actualIndexes);

			expectedIndexes = createIndexes(entityContainers.subList(0, i + 1));
			actualIndexes = entityStorage.search(
					SearchQueryBuilder.of(getIndexClass()).sorting(BaseEntityIndex.Properties.id, Order.ASC).build());

			check(expectedIndexes, actualIndexes);
		}

		for (int i = 0; i < entityContainers.size(); i++) {

			EntityContainer<E> entityContainer = entityContainers.get(i);

			updateEntity(entityContainer.getEntity());

			EntityContainer<E> expectedEntityContainer = entityStorage.save(entityContainer);
			EntityContainer<E> actualEntityContainer = entityStorage.load(entityContainer.toReference());

			check(expectedEntityContainer, actualEntityContainer);

			List<I> expectedIndexes = createIndexes(entityContainer);
			List<I> actualIndexes = entityStorage.search(SearchQueryBuilder.of(getIndexClass())
					.eq(BaseEntityIndex.Properties.source, entityContainer.toReference())
					.sorting(BaseEntityIndex.Properties.id, Order.ASC).build());

			check(expectedIndexes, actualIndexes);

			expectedIndexes = createIndexes(entityContainers);
			actualIndexes = entityStorage.search(
					SearchQueryBuilder.of(getIndexClass()).sorting(BaseEntityIndex.Properties.id, Order.ASC).build());

			check(expectedIndexes, actualIndexes);
		}
	}

	private List<I> createIndexes(EntityContainer<E> entityContainer) throws Exception {

		List<I> indexes = new ArrayList<>();

		for (EntityIndexHandler<E, I> indexHandler : indexHandlers) {
			indexes.addAll(indexHandler.create(entityContainer));
		}

		return indexes.stream().sorted(Comparator.comparing(item -> item.getId())).collect(Collectors.toList());
	}

	private List<I> createIndexes(List<EntityContainer<E>> entityContainers) throws Exception {

		List<I> indexes = new ArrayList<>();

		for (EntityContainer<E> entityContainer : entityContainers) {
			indexes.addAll(createIndexes(entityContainer));
		}

		return indexes.stream().sorted(Comparator.comparing(item -> item.getId())).collect(Collectors.toList());
	}

	private void check(EntityContainer<E> expectedEntityContainer, EntityContainer<E> actualEntityContainer) {

		Assertions.assertEquals(expectedEntityContainer.getId(), actualEntityContainer.getId());
		Assertions.assertEquals(expectedEntityContainer.getEntityType(), actualEntityContainer.getEntityType());

		checkEntity(expectedEntityContainer.getEntity(), actualEntityContainer.getEntity());
	}

	private void check(List<I> expectedIndexes, List<I> actualIndexes) {

		Assertions.assertEquals(expectedIndexes.size(), actualIndexes.size());

		for (int i = 0; i < expectedIndexes.size(); i++) {

			I expectedIndex = expectedIndexes.get(i);
			I actualIndex = actualIndexes.get(i);

			Assertions.assertEquals(expectedIndex.getId(), actualIndex.getId());
			Assertions.assertEquals(expectedIndex.getSource(), actualIndex.getSource());

			checkIndex(expectedIndex, actualIndex);
		}
	}

	protected abstract String getSqlScriptFileName() throws Exception;

	protected abstract Class<I> getIndexClass();

	protected abstract List<EntityContainer<E>> createEntityContainers();

	protected abstract void updateEntity(E entity);

	protected abstract List<EntityIndexHandler<E, I>> createIndexHandlers();

	protected abstract void checkEntity(E expectedEntity, E actualEntity);

	protected abstract void checkIndex(I expectedIndex, I actualIndex);

}
