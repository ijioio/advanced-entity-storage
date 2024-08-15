package com.ijioio.aes.persistence.test.fixture.jdbc.index.property;

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
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.PropertyReference;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.persistence.PersistenceException;

public abstract class BasePropertyCollectionSearchPersistenceTest<I extends EntityIndex<?>, V extends Collection<E>, E>
		extends BasePropertySearchPersistenceTest<I, V> {

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
	public void testSearchEmpty() throws Exception {

		for (I index : indexes) {

			setPropertyValue(index, createEmptyPropertyValue());

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
	public void testSearchPlainEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

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

	@Test
	public void testSearchPlainNotEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

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

	@Test
	public void testSearchPlainLowerEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

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

	@Test
	public void testSearchPlainLowerOrEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

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

	@Test
	public void testSearchPlainGreaterEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

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

	@Test
	public void testSearchPlainGreaterOrEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

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

	@Test
	public void testSearchPlainAnyEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyeq(getProperty(), selectedValue) //
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
	public void testSearchPlainAnyEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyeq(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainAnyNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllSamePropertyValue(indexes.indexOf(selectedIndex)));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyne(getProperty(), selectedValue) //
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
	public void testSearchPlainAnyNotEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyne(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_NOT_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainAnyLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anylt(getProperty(), selectedValue) //
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
	public void testSearchPlainAnyLowerNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anylt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_LOWER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainAnyLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyle(getProperty(), selectedValue) //
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
	public void testSearchPlainAnyLowerOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyle(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_LOWER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testSearchPlainAnyGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anygt(getProperty(), selectedValue) //
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
	public void testSearchPlainAnyGreaterNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anygt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_GREATER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainAnyGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyge(getProperty(), selectedValue) //
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
	public void testSearchPlainAnyGreaterOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.anyge(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_GREATER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testSearchPlainAllEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllSamePropertyValue(indexes.indexOf(selectedIndex)));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.alleq(getProperty(), selectedValue) //
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
	public void testSearchPlainAllEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.alleq(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainAllNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allne(getProperty(), selectedValue) //
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
	public void testSearchPlainAllNotEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allne(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_NOT_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainAllLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.alllt(getProperty(), selectedValue) //
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
	public void testSearchPlainAllLowerNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.alllt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_LOWER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainAllLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allle(getProperty(), selectedValue) //
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
	public void testSearchPlainAllLowerOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allle(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_LOWER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testSearchPlainAllGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allgt(getProperty(), selectedValue) //
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
	public void testSearchPlainAllGreaterNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allgt(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_GREATER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainAllGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allge(getProperty(), selectedValue) //
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
	public void testSearchPlainAllGreaterOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.allge(getProperty(), selectedValue) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_GREATER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.eq(getProperty(), getPropertyValue(selectedIndex)) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) == 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsNotEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.ne(getProperty(), getPropertyValue(selectedIndex)) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) != 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsLowerEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.lt(getProperty(), getPropertyValue(selectedIndex)) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) < 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsLowerOrEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.le(getProperty(), getPropertyValue(selectedIndex)) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) <= 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsGreaterEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.gt(getProperty(), getPropertyValue(selectedIndex)) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) > 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsGreaterOrEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.ge(getProperty(), getPropertyValue(selectedIndex)) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) >= 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAnyEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anyeq(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) == 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAnyEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anyeq(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsAnyNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllSamePropertyValue(indexes.indexOf(selectedIndex)));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anyne(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) != 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAnyNotEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anyne(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_NOT_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsAnyLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anylt(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) < 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAnyLowerNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anylt(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_LOWER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsAnyLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anyle(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) <= 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAnyLowerOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anyle(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_LOWER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsAnyGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anygt(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) > 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAnyGreaterNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anygt(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_GREATER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsAnyGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anyge(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, selectedValue) >= 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAnyGreaterOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.anyge(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ANY_GREATER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsAllEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllSamePropertyValue(indexes.indexOf(selectedIndex)));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.alleq(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) == 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAllEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.alleq(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsAllNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.allne(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) != 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAllNotEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.allne(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_NOT_EQUALS for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsAllLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.alllt(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) < 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAllLowerNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.alllt(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_LOWER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsAllLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.allle(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) <= 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAllLowerOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.allle(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_LOWER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsAllGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.allgt(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) > 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAllGreaterNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.allgt(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_GREATER for value null is not supported", exception.getMessage());
	}

	@Test
	public void testSearchPlainExistsAllGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.allge(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, selectedValue) >= 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchPlainExistsAllGreaterOrEqualsNull() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllNullPropertyValue());

		E selectedValue = null;

		for (I index : indexes) {
			handler.create(index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass()) //
				.allge(getProperty(), selectedValue) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		PersistenceException exception = Assertions.assertThrows(PersistenceException.class,
				() -> handler.search(query));

		Assertions.assertEquals("operation ALL_GREATER_OR_EQUALS for value null is not supported",
				exception.getMessage());
	}

	@Test
	public void testSearchReferenceEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.eq(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getOtherPropertyValue(item)) == 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceNotEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.ne(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getOtherPropertyValue(item)) != 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceLowerEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.lt(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getOtherPropertyValue(item)) < 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceLowerOrEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.le(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getOtherPropertyValue(item)) <= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceGreaterEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.gt(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getOtherPropertyValue(item)) > 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceGreaterOrEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		for (I index : indexes) {

			if (index == selectedIndex) {
				setPropertyValue(index, createEmptyPropertyValue());
			}

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.ge(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getOtherPropertyValue(item)) >= 0)
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAnyEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.anyeq(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) == 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAnyNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllSamePropertyValue(indexes.indexOf(selectedIndex)));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.anyne(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) != 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAnyLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.anylt(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) < 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAnyLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.anyle(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) <= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAnyGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.anygt(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) > 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAnyGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.anyge(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) >= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAllEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllSamePropertyValue(indexes.indexOf(selectedIndex)));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.alleq(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) == 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAllNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.allne(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) != 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAllLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.alllt(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) < 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAllLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.allle(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) <= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAllGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.allgt(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) > 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceAllGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass(), namespace) //
				.allge(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) >= 0))
				.collect(Collectors.toList());
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.eq(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) == 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsNotEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.ne(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) != 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsLowerEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.lt(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) < 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsLowerOrEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.le(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) <= 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsGreaterEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.gt(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) > 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsGreaterOrEqualsEmpty() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createEmptyPropertyValue());

		for (I index : indexes) {

			setOtherPropertyValue(index, createEmptyPropertyValue());

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.ge(getProperty(), PropertyReference.of(namespace, getOtherProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> comparePropertyValue(getPropertyValue(item), getPropertyValue(selectedIndex)) >= 0)
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAnyEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.anyeq(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) == 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAnyNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllSamePropertyValue(indexes.indexOf(selectedIndex)));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.anyne(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) != 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAnyLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.anylt(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) < 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAnyLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.anyle(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) <= 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAnyGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.anygt(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) > 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAnyGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.anyge(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.anyMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) >= 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAllEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		setPropertyValue(selectedIndex, createAllSamePropertyValue(indexes.indexOf(selectedIndex)));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.alleq(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) == 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAllNotEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.allne(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) != 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAllLower() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.alllt(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) < 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAllLowerOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.allle(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) <= 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAllGreater() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.allgt(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) > 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	@Test
	public void testSearchReferenceExistsAllGreaterOrEquals() throws Exception {

		I selectedIndex = indexes.get(random.nextInt(indexes.size()));

		E selectedValue = getPropertyValue(selectedIndex).stream()
				.skip(random.nextInt(getPropertyValue(selectedIndex).size())).findFirst().get();

		for (I index : indexes) {

			setOtherSinglePropertyValue(index, selectedValue);

			handler.create(index);
		}

		String namespace = random.nextBoolean() ? NAMESPACE : null;

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()) //
				.exists(getIndexClass(), namespace) //
				.allge(getProperty(), PropertyReference.of(namespace, getOtherSingleProperty())) //
				.end() //
				.sorting(BaseEntityIndex.Properties.id, Order.ASC) //
				.build();

		List<I> expectedIndexes = indexes.stream()
				.filter(item -> getPropertyValue(item).stream()
						.allMatch(value -> comparePropertyValueElement(value, getOtherSinglePropertyValue(item)) >= 0))
				.count() > 0 ? indexes : Collections.emptyList();
		List<I> actualIndexes = handler.search(query);

		check(expectedIndexes, actualIndexes);
	}

	protected abstract V createEmptyPropertyValue();

	protected abstract V createAllNullPropertyValue();

	protected abstract V createAllSamePropertyValue(int i);

	protected abstract Property<E> getOtherSingleProperty();

	protected abstract E getOtherSinglePropertyValue(I index);

	protected abstract void setOtherSinglePropertyValue(I index, E value);

	protected abstract int comparePropertyValueElement(E element1, E element2);
}