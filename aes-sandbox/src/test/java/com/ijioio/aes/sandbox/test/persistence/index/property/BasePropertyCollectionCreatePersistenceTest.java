package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.core.EntityIndex;

public abstract class BasePropertyCollectionCreatePersistenceTest<I extends EntityIndex<?>, V extends Collection<E>, E>
		extends BasePropertyCreatePersistenceTest<I, V> {

	protected static int VALUE_MAX_COUNT = 3;

	@Test
	public void testCreateEmpty() throws Exception {

		setPropertyValue(index, createEmptyPropertyValue());

		handler.create(index);

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

	protected abstract V createEmptyPropertyValue();
}
