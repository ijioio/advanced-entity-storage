package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.BaseEntityIndex;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.Order;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;
import com.ijioio.test.model.PropertyEntityReferenceSearchPersistence;
import com.ijioio.test.model.PropertyEntityReferenceSearchPersistenceIndex;

public class PropertyEntityReferenceSearchPersistenceTest extends BasePersistenceTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.persistence.index.property.PropertyEntityReferenceSearchPersistenceTest.Some";
	}

	@Entity( //
			name = PropertyEntityReferenceSearchPersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueEntityReference", type = @Type(name = Type.ENTITY_REFERENCE), parameters = @Type(name = Some.NAME)) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyEntityReferenceSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueEntityReference", type = @Type(name = Type.ENTITY_REFERENCE), parameters = @Type(name = Some.NAME)) //
							} //
					) //
			} //
	)
	public static interface PropertyEntityReferenceSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyEntityReferenceSearchPersistenceIndex";
	}

	private Path path;

	private List<PropertyEntityReferenceSearchPersistenceIndex> indexes;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-entity-reference-search-persistence.sql").toURI());

		executeSql(connection, path);

		int count = random.nextInt(10) + 1;

		indexes = new ArrayList<>();

		for (int i = 0; i < count; i++) {

			PropertyEntityReferenceSearchPersistenceIndex index = new PropertyEntityReferenceSearchPersistenceIndex();

			index.setId(String.format("property-entity-reference-search-persistence-index-%s", i));
			index.setSource(EntityReference.of(String.format("property-entity-reference-search-persistence-%s", i),
					PropertyEntityReferenceSearchPersistence.class));
			index.setValueEntityReference(EntityReference.of(String.format("some-%s", i), Some.class));

			indexes.add(index);
		}
	}

	@Test
	public void testSearch() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		for (PropertyEntityReferenceSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyEntityReferenceSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyEntityReferenceSearchPersistenceIndex.class)
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyEntityReferenceSearchPersistenceIndex> expectedIndexes = indexes;
		List<PropertyEntityReferenceSearchPersistenceIndex> actualIndexes = handler
				.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchEquals() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		for (PropertyEntityReferenceSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		PropertyEntityReferenceSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		SearchQuery<PropertyEntityReferenceSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyEntityReferenceSearchPersistenceIndex.class)
				.eq(PropertyEntityReferenceSearchPersistenceIndex.Properties.valueEntityReference,
						selectedIndex.getValueEntityReference())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyEntityReferenceSearchPersistenceIndex> expectedIndexes = Collections.singletonList(selectedIndex);
		List<PropertyEntityReferenceSearchPersistenceIndex> actualIndexes = handler
				.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	private void check(List<PropertyEntityReferenceSearchPersistenceIndex> expectedIndexes,
			List<PropertyEntityReferenceSearchPersistenceIndex> actualIndexes) {

		Assertions.assertEquals(expectedIndexes.size(), actualIndexes.size());

		for (int i = 0; i < expectedIndexes.size(); i++) {

			PropertyEntityReferenceSearchPersistenceIndex expectedIndex = expectedIndexes.get(i);
			PropertyEntityReferenceSearchPersistenceIndex actualIndex = actualIndexes.get(i);

			Assertions.assertEquals(expectedIndex.getId(), actualIndex.getId());
			Assertions.assertEquals(expectedIndex.getSource().getId(), actualIndex.getSource().getId());
			Assertions.assertEquals(expectedIndex.getSource().getType().getName(),
					actualIndex.getSource().getType().getName());
			Assertions.assertEquals(expectedIndex.getValueEntityReference().getId(),
					actualIndex.getValueEntityReference().getId());
			Assertions.assertEquals(expectedIndex.getValueEntityReference().getType().getName(),
					actualIndex.getValueEntityReference().getType().getName());
		}
	}
}
