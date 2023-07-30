package com.ijioio.aes.sandbox.test.persistence.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.BaseEntityIndex;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.Order;
import com.ijioio.aes.core.SearchCriterion.SimpleSearchCriterion;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;
import com.ijioio.test.model.PropertyStringSearchPersistence;
import com.ijioio.test.model.PropertyStringSearchPersistenceIndex;

public class PropertyStringSearchPersistenceTest extends BasePersistenceTest {

	@Entity( //
			name = PropertyStringSearchPersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = @Type(name = Type.STRING)) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueString", type = @Type(name = Type.STRING)) //
							} //
					) //
			} //
	)
	public static interface PropertyStringSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringSearchPersistenceIndex";
	}

	private Path path;

	private List<PropertyStringSearchPersistenceIndex> indexes;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(
				getClass().getClassLoader().getResource("persistence/property-string-search-persistence.sql").toURI());

		executeSql(connection, path);

		int count = random.nextInt(10) + 1;

		indexes = new ArrayList<>();

		for (int i = 0; i < count; i++) {

			PropertyStringSearchPersistenceIndex index = new PropertyStringSearchPersistenceIndex();

			index.setId(String.format("property-string-search-persistence-index-%s", i));
			index.setSource(EntityReference.of(String.format("property-string-search-persistence-%s", i),
					PropertyStringSearchPersistence.class));
			index.setValueString(String.format("value-%s", i));

			indexes.add(index);
		}
	}

	@Test
	public void testSearch() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class).sorting(BaseEntityIndex.Properties.id, Order.ASC)
				.build();

		List<PropertyStringSearchPersistenceIndex> expectedIndexes = indexes;
		List<PropertyStringSearchPersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchEquals() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		List<PropertyStringSearchPersistenceIndex> selectedIndexes = random
				.ints(random.nextInt(indexes.size()) + 1, 0, indexes.size()).distinct().sorted()
				.mapToObj(item -> indexes.get(item)).collect(Collectors.toList());

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.or(selectedIndexes.stream()
						.map(item -> SimpleSearchCriterion
								.eq(PropertyStringSearchPersistenceIndex.Properties.valueString, item.getValueString()))
						.collect(Collectors.toList()))
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringSearchPersistenceIndex> expectedIndexes = selectedIndexes;
		List<PropertyStringSearchPersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchNotEquals() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		List<PropertyStringSearchPersistenceIndex> selectedIndexes = random
				.ints(random.nextInt(indexes.size()) + 1, 0, indexes.size()).distinct().sorted()
				.mapToObj(item -> indexes.get(item)).collect(Collectors.toList());

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.and(selectedIndexes.stream()
						.map(item -> SimpleSearchCriterion
								.ne(PropertyStringSearchPersistenceIndex.Properties.valueString, item.getValueString()))
						.collect(Collectors.toList()))
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringSearchPersistenceIndex> expectedIndexes = indexes.stream()
				.filter(item -> !selectedIndexes.contains(item)).collect(Collectors.toList());
		List<PropertyStringSearchPersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchGreater() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.gt(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringSearchPersistenceIndex> expectedIndexes = indexes.stream()
				.filter(item -> item.getValueString().compareTo(selectedIndex.getValueString()) > 0)
				.collect(Collectors.toList());
		List<PropertyStringSearchPersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchGreaterOrEquals() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.ge(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringSearchPersistenceIndex> expectedIndexes = indexes.stream()
				.filter(item -> item.getValueString().compareTo(selectedIndex.getValueString()) >= 0)
				.collect(Collectors.toList());
		List<PropertyStringSearchPersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchLower() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.lt(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringSearchPersistenceIndex> expectedIndexes = indexes.stream()
				.filter(item -> item.getValueString().compareTo(selectedIndex.getValueString()) < 0)
				.collect(Collectors.toList());
		List<PropertyStringSearchPersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchLowerOrEquals() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.le(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringSearchPersistenceIndex> expectedIndexes = indexes.stream()
				.filter(item -> item.getValueString().compareTo(selectedIndex.getValueString()) <= 0)
				.collect(Collectors.toList());
		List<PropertyStringSearchPersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				query);

		check(expectedIndexes, actualIndexes);
	}

	private void check(List<PropertyStringSearchPersistenceIndex> expectedIndexes,
			List<PropertyStringSearchPersistenceIndex> actualIndexes) {

		Assertions.assertEquals(expectedIndexes.size(), actualIndexes.size());

		for (int i = 0; i < expectedIndexes.size(); i++) {

			PropertyStringSearchPersistenceIndex expectedIndex = expectedIndexes.get(i);
			PropertyStringSearchPersistenceIndex actualIndex = actualIndexes.get(i);

			Assertions.assertEquals(expectedIndex.getId(), actualIndex.getId());
			Assertions.assertEquals(expectedIndex.getSource().getId(), actualIndex.getSource().getId());
			Assertions.assertEquals(expectedIndex.getSource().getType().getName(),
					actualIndex.getSource().getType().getName());
			Assertions.assertEquals(expectedIndex.getValueString(), actualIndex.getValueString());
		}
	}
}
