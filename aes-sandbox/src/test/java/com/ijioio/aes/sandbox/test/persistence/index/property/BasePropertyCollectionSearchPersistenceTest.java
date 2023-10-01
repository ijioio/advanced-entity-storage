package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
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

public abstract class BasePropertyCollectionSearchPersistenceTest<I extends EntityIndex<?>, V extends Collection<E>, E>
		extends BasePersistenceTest {

	protected static int INDEX_MAX_COUNT = 9;

	protected static int VALUE_MAX_COUNT = 3;

	protected JdbcPersistenceHandler handler;

	protected List<I> indexes = new ArrayList<>();

	@BeforeEach
	public void before() throws Exception {

		handler = new JdbcPersistenceHandler();

		executeSql(connection, Paths.get(getClass().getClassLoader()
				.getResource(String.format("persistence/index/property/%s", getSqlScriptFileName())).toURI()));

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
	public void testSearchEmpty() throws Exception {

		for (I index : indexes) {

			setPropertyValue(index, createEmptyPropertyValue());

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

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) == 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.eq(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) == 0)
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

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) == 0)
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

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) != 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchNotEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.ne(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) != 0)
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

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) != 0)
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

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) > 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchGreaterEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.gt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) > 0)
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
	public void testSearchGreaterOrEqual() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.ge(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) >= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchGreaterOrEqualEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.ge(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) >= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchGreaterOrEqualNull() throws Exception {

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

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) < 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchLowerEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.lt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) < 0)
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
	public void testSearchLowerOrEqual() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.le(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) <= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchLowerOrEqualEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.le(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) <= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchLowerOrEqualNull() throws Exception {

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
	public void testSearchAnyEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyeq(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) == 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAnyEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyeq(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ANY_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchAnyNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllSamePropertyValue(count));
			}

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyne(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) != 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAnyNotEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyne(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ANY_NOT_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchAnyGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anygt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) > 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAnyGreaterNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anygt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ANY_GREATER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchAnyGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyge(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) >= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAnyGreaterOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyge(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ANY_GREATER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testSearchAnyLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anylt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) < 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAnyLowerNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anylt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ANY_LOWER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchAnyLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyle(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) <= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAnyLowerOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyle(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ANY_LOWER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testSearchAllEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.alleq(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) == 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAllEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.alleq(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ALL_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchAllNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allne(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) != 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAllNotEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allne(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ALL_NOT_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchAllGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allgt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) > 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAllGreaterNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allgt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ALL_GREATER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchAllGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allge(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) >= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAllGreaterOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allge(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ALL_GREATER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testSearchAllLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.alllt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) < 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAllLowerNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.alllt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ALL_LOWER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchAllLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allle(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) <= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchAllLowerOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(JdbcPersistenceContext.of(connection), index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allle(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(JdbcPersistenceContext.of(connection), query));

		Assertions.assertEquals("operation ALL_LOWER_OR_EQUALS for value null is not supported",
				exception.getMessage());
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

	protected abstract String getSqlScriptFileName() throws Exception;

	protected abstract Class<I> getIndexClass();

	protected abstract List<I> createIndexes();

	protected abstract V createEmptyPropertyValue();

	protected abstract V createAllNullPropertyValue();

	protected abstract V createAllSamePropertyValue(int i);

	protected abstract Property<V> getProperty();

	protected abstract V getPropertyValue(I index);

	protected abstract void setPropertyValue(I index, V value);

	protected abstract int comparePropertyValue(V value1, V value2);

	protected abstract int comparePropertyValueElement(E element1, E element2);

	protected abstract void checkPropertyValue(V expectedValue, V actualValue);
}
