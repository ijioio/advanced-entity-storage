package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.BaseEntity;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;

public abstract class BasePropertyCreatePersistenceTest<I extends EntityIndex<?>, V> extends BasePersistenceTest {

	public static class Some extends BaseEntity {

		public static final String NAME = "com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertyCreatePersistenceTest.Some";
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

	protected JdbcPersistenceHandler handler;

	protected I index;

	@BeforeEach
	public void before() throws Exception {

		handler = new JdbcPersistenceHandler();

		executeSql(connection, Paths.get(getClass().getClassLoader()
				.getResource(String.format("persistence/index/property/%s", getSqlScriptFileName())).toURI()));

		index = createIndex();
	}

	@Test
	public void testCreate() throws Exception {

		handler.create(JdbcPersistenceContext.of(connection), index);

		try (PreparedStatement statement = connection
				.prepareStatement(String.format("select * from %s", getTableName()))) {

			try (ResultSet resultSet = statement.executeQuery()) {

				Assertions.assertTrue(resultSet.next());

				Assertions.assertEquals(index.getId(), resultSet.getString("id"));
				Assertions.assertEquals(index.getSource().getId(), resultSet.getString("sourceId"));
				Assertions.assertEquals(index.getSource().getType().getName(), resultSet.getString("sourceType"));

				checkPropertyValue(getPropertyValue(index), resultSet);

				Assertions.assertTrue(resultSet.isLast());
			}
		}
	}

	@Test
	public void testCreateNull() throws Exception {

		if (!isFinal()) {

			setPropertyValue(index, null);

			handler.create(JdbcPersistenceContext.of(connection), index);

			try (PreparedStatement statement = connection
					.prepareStatement(String.format("select * from %s", getTableName()))) {

				try (ResultSet resultSet = statement.executeQuery()) {

					Assertions.assertTrue(resultSet.next());

					Assertions.assertEquals(index.getId(), resultSet.getString("id"));
					Assertions.assertEquals(index.getSource().getId(), resultSet.getString("sourceId"));
					Assertions.assertEquals(index.getSource().getType().getName(), resultSet.getString("sourceType"));

					checkPropertyValue(getPropertyValue(index), resultSet);

					Assertions.assertTrue(resultSet.isLast());
				}
			}
		}
	}

	protected abstract String getSqlScriptFileName() throws Exception;

	protected abstract boolean isFinal();

	protected abstract String getTableName();

	protected abstract I createIndex();

	protected abstract V getPropertyValue(I index);

	protected abstract void setPropertyValue(I index, V value);

	protected abstract void checkPropertyValue(V value, ResultSet resultSet) throws Exception;
}
