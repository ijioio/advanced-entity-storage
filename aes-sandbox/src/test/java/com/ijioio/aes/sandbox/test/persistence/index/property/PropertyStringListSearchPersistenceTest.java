package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.BaseEntityIndex;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.Order;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;
import com.ijioio.test.model.PropertyStringListSearchPersistence;
import com.ijioio.test.model.PropertyStringListSearchPersistenceIndex;

public class PropertyStringListSearchPersistenceTest extends BasePersistenceTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.persistence.index.property.PropertyStringListSearchPersistenceTest.Some";
	}

	@Entity( //
			name = PropertyStringListSearchPersistencePrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = Type.LIST, parameters = Type.STRING) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = "List<String>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringListSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueStringList", type = "List<String>") //
							} //
					) //
			} //
	)
	public static interface PropertyStringListSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringListSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringListSearchPersistenceIndex";
	}

	private JdbcPersistenceHandler handler;

	private Path path;

	private List<PropertyStringListSearchPersistenceIndex> indexes;

	@BeforeEach
	public void before() throws Exception {

		handler = new JdbcPersistenceHandler();

		path = Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-string-list-search-persistence.sql").toURI());

		executeSql(connection, path);

		int count = random.nextInt(10) + 1;

		indexes = new ArrayList<>();

		for (int i = 0; i < count; i++) {

			PropertyStringListSearchPersistenceIndex index = new PropertyStringListSearchPersistenceIndex();

			index.setId(String.format("property-string-list-search-persistence-index-%s", i));
			index.setSource(EntityReference.of(String.format("property-string-list-search-persistence-%s", i),
					PropertyStringListSearchPersistence.class));
			index.setValueStringList(Arrays.asList(String.format("value%s1", i), String.format("value%s2", i),
					String.format("value%s3", i)));

			indexes.add(index);
		}
	}

	@Test
	public void testSearch() throws Exception {

		for (PropertyStringListSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyStringListSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringListSearchPersistenceIndex.class).sorting(BaseEntityIndex.Properties.id, Order.ASC)
				.build();

		List<PropertyStringListSearchPersistenceIndex> expectedIndexes = indexes;
		List<PropertyStringListSearchPersistenceIndex> actualIndexes = handler
				.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchEquals() throws Exception {

		for (PropertyStringListSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		PropertyStringListSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		SearchQuery<PropertyStringListSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringListSearchPersistenceIndex.class)
				.eq(PropertyStringListSearchPersistenceIndex.Properties.valueStringList,
						selectedIndex.getValueStringList())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringListSearchPersistenceIndex> expectedIndexes = Collections.singletonList(selectedIndex);
		List<PropertyStringListSearchPersistenceIndex> actualIndexes = handler
				.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchNotEquals() throws Exception {

		for (PropertyStringListSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		PropertyStringListSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		SearchQuery<PropertyStringListSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringListSearchPersistenceIndex.class)
				.ne(PropertyStringListSearchPersistenceIndex.Properties.valueStringList,
						selectedIndex.getValueStringList())
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringListSearchPersistenceIndex> expectedIndexes = indexes.stream()
				.filter(item -> !selectedIndex.equals(item)).collect(Collectors.toList());
		List<PropertyStringListSearchPersistenceIndex> actualIndexes = handler
				.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAnyEquals() throws Exception {

		for (PropertyStringListSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		PropertyStringListSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		String selectedValue = selectedIndex.getValueStringList()
				.get(random.nextInt(selectedIndex.getValueStringList().size()));

		SearchQuery<PropertyStringListSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringListSearchPersistenceIndex.class)
				.anyeq(PropertyStringListSearchPersistenceIndex.Properties.valueStringList, selectedValue)
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringListSearchPersistenceIndex> expectedIndexes = Collections.singletonList(selectedIndex);
		List<PropertyStringListSearchPersistenceIndex> actualIndexes = handler
				.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAnyNotEquals() throws Exception {

		// We should update collection with identical values
		// to make sure operation will result in a visible effect
		for (int i = 0; i < indexes.size(); i++) {
			indexes.get(i).setValueStringList(Arrays.asList(String.format("value%s", i), String.format("value%s", i),
					String.format("value%s", i)));
		}

		for (PropertyStringListSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		PropertyStringListSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		String selectedValue = selectedIndex.getValueStringList()
				.get(random.nextInt(selectedIndex.getValueStringList().size()));

		SearchQuery<PropertyStringListSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringListSearchPersistenceIndex.class)
				.anyne(PropertyStringListSearchPersistenceIndex.Properties.valueStringList, selectedValue)
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringListSearchPersistenceIndex> expectedIndexes = indexes.stream()
				.filter(item -> !selectedIndex.equals(item)).collect(Collectors.toList());
		Collections.singletonList(selectedIndex);
		List<PropertyStringListSearchPersistenceIndex> actualIndexes = handler
				.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAllEquals() throws Exception {

		// We should update collection with identical values
		// to make sure operation will result in a visible effect
		for (int i = 0; i < indexes.size(); i++) {
			indexes.get(i).setValueStringList(Arrays.asList(String.format("value%s", i), String.format("value%s", i),
					String.format("value%s", i)));
		}

		for (PropertyStringListSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		PropertyStringListSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		String selectedValue = selectedIndex.getValueStringList()
				.get(random.nextInt(selectedIndex.getValueStringList().size()));

		SearchQuery<PropertyStringListSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringListSearchPersistenceIndex.class)
				.alleq(PropertyStringListSearchPersistenceIndex.Properties.valueStringList, selectedValue)
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringListSearchPersistenceIndex> expectedIndexes = Collections.singletonList(selectedIndex);
		List<PropertyStringListSearchPersistenceIndex> actualIndexes = handler
				.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAllNotEquals() throws Exception {

		for (PropertyStringListSearchPersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		PropertyStringListSearchPersistenceIndex selectedIndex = indexes.get(random.nextInt(indexes.size()));

		String selectedValue = selectedIndex.getValueStringList()
				.get(random.nextInt(selectedIndex.getValueStringList().size()));

		SearchQuery<PropertyStringListSearchPersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringListSearchPersistenceIndex.class)
				.allne(PropertyStringListSearchPersistenceIndex.Properties.valueStringList, selectedValue)
				.sorting(BaseEntityIndex.Properties.id, Order.ASC).build();

		List<PropertyStringListSearchPersistenceIndex> expectedIndexes = indexes.stream()
				.filter(item -> !selectedIndex.equals(item)).collect(Collectors.toList());
		Collections.singletonList(selectedIndex);
		List<PropertyStringListSearchPersistenceIndex> actualIndexes = handler
				.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	private void check(List<PropertyStringListSearchPersistenceIndex> expectedIndexes,
			List<PropertyStringListSearchPersistenceIndex> actualIndexes) {

		Assertions.assertEquals(expectedIndexes.size(), actualIndexes.size());

		for (int i = 0; i < expectedIndexes.size(); i++) {

			PropertyStringListSearchPersistenceIndex expectedIndex = expectedIndexes.get(i);
			PropertyStringListSearchPersistenceIndex actualIndex = actualIndexes.get(i);

			Assertions.assertEquals(expectedIndex.getId(), actualIndex.getId());
			Assertions.assertEquals(expectedIndex.getSource().getId(), actualIndex.getSource().getId());
			Assertions.assertEquals(expectedIndex.getSource().getType().getName(),
					actualIndex.getSource().getType().getName());
			Assertions.assertEquals(expectedIndex.getValueStringList(), actualIndex.getValueStringList());

		}
	}
}
