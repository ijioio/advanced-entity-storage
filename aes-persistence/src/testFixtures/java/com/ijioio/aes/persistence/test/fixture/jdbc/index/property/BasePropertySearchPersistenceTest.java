package com.ijioio.aes.persistence.test.fixture.jdbc.index.property;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIf;

import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.BaseEntityIndex;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.Order;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.persistence.PersistenceException;
import com.ijioio.aes.persistence.test.fixture.jdbc.BasePersistenceTest;
import com.ijioio.aes.persistence.test.fixture.jdbc.PersistenceTestUtil;

public abstract class BasePropertySearchPersistenceTest<I extends EntityIndex<?>, V> extends BasePersistenceTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.persistence.jdbc.test.index.property.BasePropertySearchPersistenceTest.Some";
	}

	public static class Some1 extends Some {
		// Empty
	}

	public static class Some2 extends Some {
		// Empty
	}

	public static class Some3 extends Some {
		// Empty
	}

	public static class Some4 extends Some {
		// Empty
	}

	public static class Some5 extends Some {
		// Empty
	}

	public static class Some6 extends Some {
		// Empty
	}

	public static class Some7 extends Some {
		// Empty
	}

	public static class Some8 extends Some {
		// Empty
	}

	public static class Some9 extends Some {
		// Empty
	}

	protected static List<Character> characters = new ArrayList<>();

	protected static List<Class<? extends Some>> types = new ArrayList<>();

	static {

		characters.add(Character.valueOf('a'));
		characters.add(Character.valueOf('b'));
		characters.add(Character.valueOf('c'));
		characters.add(Character.valueOf('d'));
		characters.add(Character.valueOf('e'));
		characters.add(Character.valueOf('f'));
		characters.add(Character.valueOf('g'));
		characters.add(Character.valueOf('h'));
		characters.add(Character.valueOf('i'));

		types.add(Some1.class);
		types.add(Some2.class);
		types.add(Some3.class);
		types.add(Some4.class);
		types.add(Some5.class);
		types.add(Some6.class);
		types.add(Some7.class);
		types.add(Some8.class);
		types.add(Some9.class);
	}

	protected static int INDEX_MAX_COUNT = 9;

	protected List<I> indexes = new ArrayList<>();

	@BeforeEach
	public void before() throws Exception {

		PersistenceTestUtil.execute(dataSource,
				readString(Paths.get(getClass().getClassLoader().getResource(getSqlScriptPath()).toURI())));

		indexes.clear();
		indexes.addAll(createIndexes());
	}

	@Test
	public void testSearch() throws Exception {

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes;
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@EnabledIf("isNullPropertyValueAllowed")
	@Test
	public void testSearchNull() throws Exception {

		for (I index : indexes) {

			setPropertyValue(index, null);

			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes;
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.eq(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) == 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@EnabledIf("isNullPropertyValueAllowed")
	@Test
	public void testSearchEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.eq(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) == 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.ne(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) != 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@EnabledIf("isNullPropertyValueAllowed")
	@Test
	public void testSearchNotEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.ne(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) != 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.gt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) > 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@EnabledIf("isNullPropertyValueAllowed")
	@Test
	public void testSearchGreaterNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.gt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation GREATER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.ge(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) >= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@EnabledIf("isNullPropertyValueAllowed")
	@Test
	public void testSearchGreaterOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.ge(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation GREATER_OR_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.lt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) < 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@EnabledIf("isNullPropertyValueAllowed")
	@Test
	public void testSearchLowerNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.lt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation LOWER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.le(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) <= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@EnabledIf("isNullPropertyValueAllowed")
	@Test
	public void testSearchLowerOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.le(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation LOWER_OR_EQUALS for value null is not supported", exception.getMessage());
	}

	private void check(List<I> expectedIndexes, List<I> actualIndexes) {

		Assertions.assertEquals(expectedIndexes.size(), actualIndexes.size());

		for (int i = 0; i < expectedIndexes.size(); i++) {

			I expectedIndex = expectedIndexes.get(i);
			I actualIndex = actualIndexes.get(i);

			Assertions.assertEquals(expectedIndex.getId(), actualIndex.getId());
			Assertions.assertEquals(expectedIndex.getSource().getId(), actualIndex.getSource().getId());
			Assertions.assertEquals(expectedIndex.getSource().getType().getName(),
					actualIndex.getSource().getType().getName());

			checkPropertyValue(getPropertyValue(expectedIndex), getPropertyValue(actualIndex));
		}
	}

	protected abstract String getSqlScriptPath();

	protected abstract Class<I> getIndexClass();

	protected abstract List<I> createIndexes();

	protected abstract Property<V> getProperty();

	protected abstract boolean isNullPropertyValueAllowed();

	protected abstract V getPropertyValue(I index);

	protected abstract void setPropertyValue(I index, V value);

	protected abstract int comparePropertyValue(V value1, V value2);

	protected abstract void checkPropertyValue(V expectedValue, V actualValue);
}
