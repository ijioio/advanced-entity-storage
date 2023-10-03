package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;

public abstract class BasePropertyDeletePersistenceTest<I extends EntityIndex<?>, V> extends BasePersistenceTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertyDeletePersistenceTest.Some";
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

	protected static List<Class<? extends Some>> types = new ArrayList<>();

	static {

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

	private JdbcPersistenceHandler handler;

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
	public void testDelete() throws Exception {

		for (I index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<I> query = SearchQueryBuilder.of(getIndexClass()).build();

		handler.delete(JdbcPersistenceContext.of(connection), query);

		List<I> expectedIndexes = Collections.emptyList();
		List<I> actualIndexes = handler.search(JdbcPersistenceContext.of(connection), query);

		check(expectedIndexes, actualIndexes);
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

	protected abstract Property<V> getProperty();

	protected abstract V getPropertyValue(I index);

	protected abstract void setPropertyValue(I index, V value);

	protected abstract int comparePropertyValue(V value1, V value2);

	protected abstract void checkPropertyValue(V expectedValue, V actualValue);
}
