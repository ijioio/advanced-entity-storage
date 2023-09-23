package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;
import com.ijioio.test.model.PropertyStringSearchPersistence;
import com.ijioio.test.model.PropertyStringSearchPersistenceIndex;

public class PropertyStringSearchPersistenceTest extends BasePersistenceTest {

	@Entity( //
			name = PropertyStringSearchPersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueString", type = Type.STRING) //
							} //
					) //
			} //
	)
	public static interface PropertyStringSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringSearchPersistenceIndex";
	}

	private JdbcPersistenceHandler handler;

	private Path path;

	private List<PropertyStringSearchPersistenceIndex> indexes;

	@BeforeEach
	public void before() throws Exception {

		handler = new JdbcPersistenceHandler();

		path = Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-string-search-persistence.sql").toURI());

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
	public void testSearchNull() throws Exception {

		for (PropertyStringSearchPersistenceIndex index : indexes) {

			index.setValueString(null);

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

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.eq(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringSearchPersistenceIndex> expectedIndexes = Collections.singletonList(selectedIndex);
		List<PropertyStringSearchPersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchEqualsNull() throws Exception {

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {

			if (index == selectedIndex) {
				index.setValueString(null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.eq(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringSearchPersistenceIndex> expectedIndexes = Collections.singletonList(selectedIndex);
		List<PropertyStringSearchPersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchNotEquals() throws Exception {

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.ne(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringSearchPersistenceIndex> expectedIndexes = indexes.stream()
				.filter(item -> item != selectedIndex).collect(Collectors.toList());
		List<PropertyStringSearchPersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchNotEqualsNull() throws Exception {

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {

			if (index == selectedIndex) {
				index.setValueString(null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.ne(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringSearchPersistenceIndex> expectedIndexes = indexes.stream()
				.filter(item -> item != selectedIndex).collect(Collectors.toList());
		List<PropertyStringSearchPersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchGreater() throws Exception {

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

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
	public void testSearchGreaterNull() throws Exception {

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {

			if (index == selectedIndex) {
				index.setValueString(null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.gt(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation GREATER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchGreaterOrEquals() throws Exception {

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

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
	public void testSearchGreaterOrEqualsNull() throws Exception {

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {

			if (index == selectedIndex) {
				index.setValueString(null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.ge(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation GREATER_OR_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchLower() throws Exception {

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

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
	public void testSearchLowerNull() throws Exception {

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {

			if (index == selectedIndex) {
				index.setValueString(null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.lt(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation LOWER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchLowerOrEquals() throws Exception {

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

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

	@Test
	public void testSearchLowerOrEqualsNull() throws Exception {

		PropertyStringSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (PropertyStringSearchPersistenceIndex index : indexes) {

			if (index == selectedIndex) {
				index.setValueString(null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyStringSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringSearchPersistenceIndex.class)
				.le(PropertyStringSearchPersistenceIndex.Properties.valueString, selectedIndex.getValueString())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation LOWER_OR_EQUALS for value null is not supported", exception.getMessage());
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
