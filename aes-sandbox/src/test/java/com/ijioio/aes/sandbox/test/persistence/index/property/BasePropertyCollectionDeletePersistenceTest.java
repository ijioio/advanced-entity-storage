package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.BaseEntityIndex;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.Order;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.core.persistence.PersistenceException;

public abstract class BasePropertyCollectionDeletePersistenceTest<I extends EntityIndex<?>, V extends Collection<E>, E>
		extends BasePropertyDeletePersistenceTest<I, V> {

	public static class Some11 extends Some {
		// Empty
	}

	public static class Some12 extends Some {
		// Empty
	}

	public static class Some13 extends Some {
		// Empty
	}

	public static class Some21 extends Some {
		// Empty
	}

	public static class Some22 extends Some {
		// Empty
	}

	public static class Some23 extends Some {
		// Empty
	}

	public static class Some31 extends Some {
		// Empty
	}

	public static class Some32 extends Some {
		// Empty
	}

	public static class Some33 extends Some {
		// Empty
	}

	public static class Some41 extends Some {
		// Empty
	}

	public static class Some42 extends Some {
		// Empty
	}

	public static class Some43 extends Some {
		// Empty
	}

	public static class Some51 extends Some {
		// Empty
	}

	public static class Some52 extends Some {
		// Empty
	}

	public static class Some53 extends Some {
		// Empty
	}

	public static class Some61 extends Some {
		// Empty
	}

	public static class Some62 extends Some {
		// Empty
	}

	public static class Some63 extends Some {
		// Empty
	}

	public static class Some71 extends Some {
		// Empty
	}

	public static class Some72 extends Some {
		// Empty
	}

	public static class Some73 extends Some {
		// Empty
	}

	public static class Some81 extends Some {
		// Empty
	}

	public static class Some82 extends Some {
		// Empty
	}

	public static class Some83 extends Some {
		// Empty
	}

	public static class Some91 extends Some {
		// Empty
	}

	public static class Some92 extends Some {
		// Empty
	}

	public static class Some93 extends Some {
		// Empty
	}

	protected static Map<Integer, List<Class<? extends Some>>> collectionTypes = new HashMap<>();

	static {

		collectionTypes.put(1, Arrays.asList(Some11.class, Some12.class, Some13.class));
		collectionTypes.put(2, Arrays.asList(Some21.class, Some22.class, Some23.class));
		collectionTypes.put(3, Arrays.asList(Some31.class, Some32.class, Some33.class));
		collectionTypes.put(4, Arrays.asList(Some41.class, Some42.class, Some43.class));
		collectionTypes.put(5, Arrays.asList(Some51.class, Some52.class, Some53.class));
		collectionTypes.put(6, Arrays.asList(Some61.class, Some62.class, Some63.class));
		collectionTypes.put(7, Arrays.asList(Some71.class, Some72.class, Some73.class));
		collectionTypes.put(8, Arrays.asList(Some81.class, Some82.class, Some83.class));
		collectionTypes.put(9, Arrays.asList(Some91.class, Some92.class, Some93.class));
	}

	protected static int VALUE_MAX_COUNT = 3;

	@Test
	public void testDeleteEmpty() throws Exception {

		for (I index : indexes) {

			setPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(index);
		}

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.eq(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) != 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteNotEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(index);
		}

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.ne(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) == 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteGreaterEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(index);
		}

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.gt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) <= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteGreaterOrEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(index);
		}

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.ge(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) < 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteLowerEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(index);
		}

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.lt(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) >= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteLowerOrEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			handler.create(index);
		}

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.le(getProperty(), getPropertyValue(selectedIndex)) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) > 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAnyEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(index);
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anyeq(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) != 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAnyEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anyeq(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

		Assertions.assertEquals("operation ANY_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testDeleteAnyNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllSamePropertyValue(count));
			}

			handler.create(index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anyne(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) == 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAnyNotEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anyne(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

		Assertions.assertEquals("operation ANY_NOT_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testDeleteAnyGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(index);
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anygt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) <= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAnyGreaterNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anygt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

		Assertions.assertEquals("operation ANY_GREATER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testDeleteAnyGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(index);
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anyge(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) < 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAnyGreaterOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anyge(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

		Assertions.assertEquals("operation ANY_GREATER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testDeleteAnyLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(index);
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anylt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) >= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAnyLowerNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anylt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

		Assertions.assertEquals("operation ANY_LOWER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testDeleteAnyLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {
			handler.create(index);
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anyle(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) > 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAnyLowerOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.anyle(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

		Assertions.assertEquals("operation ANY_LOWER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testDeleteAllEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.alleq(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) != 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAllEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.alleq(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

		Assertions.assertEquals("operation ALL_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testDeleteAllNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.allne(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) == 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAllNotEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.allne(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

		Assertions.assertEquals("operation ALL_NOT_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testDeleteAllGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.allgt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) <= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAllGreaterNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.allgt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

		Assertions.assertEquals("operation ALL_GREATER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testDeleteAllGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.allge(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) < 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAllGreaterOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.allge(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

		Assertions.assertEquals("operation ALL_GREATER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testDeleteAllLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.alllt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) >= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAllLowerNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.alllt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

		Assertions.assertEquals("operation ALL_LOWER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testDeleteAllLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			handler.create(index);

			count++;
		}

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.allle(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		handler.delete(deleteQuery);

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) > 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testDeleteAllLowerOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		int count = 0;

		for (I index : indexes) {

			setPropertyValue(index, createAllSamePropertyValue(count));

			if (index == selectedIndex) {
				setPropertyValue(index, createAllNullPropertyValue());
			}

			handler.create(index);

			count++;
		}

		E selectedValue = null;

		SearchQuery<I> deleteQuery = SearchQueryBuilder.of(getIndexClass()) //
				.allle(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.delete(deleteQuery));

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

	protected abstract V createEmptyPropertyValue();

	protected abstract V createAllNullPropertyValue();

	protected abstract V createAllSamePropertyValue(int i);

	protected abstract int comparePropertyValueElement(E element1, E element2);
}
