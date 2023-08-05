package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.ijioio.test.model.PropertyEntityReferenceListSearchPersistence;
import com.ijioio.test.model.PropertyEntityReferenceListSearchPersistenceIndex;

public class PropertyEntityReferenceListSearchPersistenceTest extends BasePersistenceTest {

	public static class Some extends BaseEntity {
		// Empty
	}

	@Entity( //
			name = PropertyEntityReferenceListSearchPersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueEntityReferenceList", type = @Type(name = Type.LIST), parameters = @Type(name = "com.ijioio.aes.sandbox.test.persistence.index.property.PropertyEntityReferenceListSearchPersistenceTest.Some", reference = true)) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyEntityReferenceListSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueEntityReferenceList", type = @Type(name = Type.LIST), parameters = @Type(name = "com.ijioio.aes.sandbox.test.persistence.index.property.PropertyEntityReferenceListSearchPersistenceTest.Some", reference = true)) //
							} //
					) //
			} //
	)
	public static interface PropertyEntityReferenceListSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceListSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyEntityReferenceListSearchPersistenceIndex";
	}

	private Path path;

	private List<PropertyEntityReferenceListSearchPersistenceIndex> indexes;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-entity-reference-list-search-persistence.sql")
				.toURI());

		executeSql(connection, path);

		int count = random.nextInt(10) + 1;

		indexes = new ArrayList<>();

		for (int i = 0; i < count; i++) {

			PropertyEntityReferenceListSearchPersistenceIndex index = new PropertyEntityReferenceListSearchPersistenceIndex();

			index.setId(String.format("property-entity-reference-list-search-persistence-index-%s", i));
			index.setSource(EntityReference.of(String.format("property-entity-reference-list-search-persistence-%s", i),
					PropertyEntityReferenceListSearchPersistence.class));
			index.setValueEntityReferenceList(Arrays.asList(EntityReference.of("some-1", Some.class),
					EntityReference.of("some-2", Some.class), EntityReference.of("some-3", Some.class)));

			indexes.add(index);
		}
	}

	@Test
	public void testSearch() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		for (PropertyEntityReferenceListSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyEntityReferenceListSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyEntityReferenceListSearchPersistenceIndex.class)
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyEntityReferenceListSearchPersistenceIndex> expectedIndexes = indexes;
		List<PropertyEntityReferenceListSearchPersistenceIndex> actualIndexes = handler
				.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	private void check(List<PropertyEntityReferenceListSearchPersistenceIndex> expectedIndexes,
			List<PropertyEntityReferenceListSearchPersistenceIndex> actualIndexes) {

		Assertions.assertEquals(expectedIndexes.size(), actualIndexes.size());

		for (int i = 0; i < expectedIndexes.size(); i++) {

			PropertyEntityReferenceListSearchPersistenceIndex expectedIndex = expectedIndexes.get(i);
			PropertyEntityReferenceListSearchPersistenceIndex actualIndex = actualIndexes.get(i);

			Assertions.assertEquals(expectedIndex.getId(), actualIndex.getId());
			Assertions.assertEquals(expectedIndex.getSource().getId(), actualIndex.getSource().getId());
			Assertions.assertEquals(expectedIndex.getSource().getType().getName(),
					actualIndex.getSource().getType().getName());
			Assertions.assertEquals(expectedIndex.getValueEntityReferenceList(),
					actualIndex.getValueEntityReferenceList());
		}
	}
}
