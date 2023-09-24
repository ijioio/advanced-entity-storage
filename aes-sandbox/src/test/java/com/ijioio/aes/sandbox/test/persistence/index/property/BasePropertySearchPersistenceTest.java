package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.BaseEntityIndex;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.Order;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;

public abstract class BasePropertySearchPersistenceTest<I extends EntityIndex<?>, V> extends BasePersistenceTest {

	protected JdbcPersistenceHandler handler;

	protected List<I> indexes = new ArrayList<>();

	@BeforeEach
	public void before() throws Exception {

		handler = new JdbcPersistenceHandler();

		executeSql(connection, Paths.get(getClass().getClassLoader().getResource(getSqlScriptPath()).toURI()));

		indexes.clear();
		indexes.addAll(createIndexes());
	}

	@Test
	public void testSearch() throws Exception {

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes;
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchNull() throws Exception {

		for (I index : indexes) {

			setPropertyValue(index, null);

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes;
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.eq(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream().filter(item -> comparePropertyValue(item, selectedIndex) == 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.eq(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream().filter(item -> comparePropertyValue(item, selectedIndex) == 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.ne(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream().filter(item -> comparePropertyValue(item, selectedIndex) != 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchNotEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.ne(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream().filter(item -> comparePropertyValue(item, selectedIndex) != 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.gt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream().filter(item -> comparePropertyValue(item, selectedIndex) > 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchGreaterNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.gt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation GREATER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.ge(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream().filter(item -> comparePropertyValue(item, selectedIndex) >= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchGreaterOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.ge(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation GREATER_OR_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.lt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream().filter(item -> comparePropertyValue(item, selectedIndex) < 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchLowerNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.lt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation LOWER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.le(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream().filter(item -> comparePropertyValue(item, selectedIndex) <= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchLowerOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, null);
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.le(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

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
			Assertions.assertEquals(getPropertyValue(expectedIndex), getPropertyValue(actualIndex));
		}
	}

	protected abstract String getSqlScriptPath() throws Exception;

	protected abstract String getTableName();

	protected abstract Class<I> getIndexClass();

	protected abstract List<I> createIndexes();

	protected abstract Property<V> getProperty();

	protected abstract V getPropertyValue(I index);

	protected abstract void setPropertyValue(I index, V value);

	protected abstract int comparePropertyValue(I o1, I o2);
}
