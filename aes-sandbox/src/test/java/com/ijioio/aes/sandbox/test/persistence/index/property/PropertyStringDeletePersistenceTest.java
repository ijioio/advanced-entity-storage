package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.SearchQuery.SearchQueryBuilder;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;
import com.ijioio.test.model.PropertyStringDeletePersistence;
import com.ijioio.test.model.PropertyStringDeletePersistenceIndex;

public class PropertyStringDeletePersistenceTest extends BasePersistenceTest {

	@Entity( //
			name = PropertyStringDeletePersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringDeletePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueString", type = Type.STRING) //
							} //
					) //
			} //
	)
	public static interface PropertyStringDeletePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringDeletePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringDeletePersistenceIndex";
	}

	private Path path;

	private List<PropertyStringDeletePersistenceIndex> indexes;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader()
				.getResource("persistence/index/property/property-string-delete-persistence.sql").toURI());

		executeSql(connection, path);

		int count = random.nextInt(10) + 1;

		indexes = new ArrayList<>();

		for (int i = 0; i < count; i++) {

			PropertyStringDeletePersistenceIndex index = new PropertyStringDeletePersistenceIndex();

			index.setId(String.format("property-string-delete-persistence-index-%s", i));
			index.setSource(EntityReference.of(String.format("property-string-delete-persistence-%s", i),
					PropertyStringDeletePersistence.class));
			index.setValueString(String.format("value-%s", i));

			indexes.add(index);
		}
	}

	@Test
	public void testDelete() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

		for (PropertyStringDeletePersistenceIndex index : indexes) {
			handler.create(JdbcPersistenceContext.of(connection), index);
		}

		SearchQuery<PropertyStringDeletePersistenceIndex> query = SearchQueryBuilder
				.of(PropertyStringDeletePersistenceIndex.class).build();

		handler.delete(JdbcPersistenceContext.of(connection), query);

		List<PropertyStringDeletePersistenceIndex> expectedIndexes = Collections.emptyList();
		List<PropertyStringDeletePersistenceIndex> actualIndexes = handler.search(JdbcPersistenceContext.of(connection),
				SearchQueryBuilder.of(PropertyStringDeletePersistenceIndex.class).build());

		check(expectedIndexes, actualIndexes);
	}

	private void check(List<PropertyStringDeletePersistenceIndex> expectedIndexes,
			List<PropertyStringDeletePersistenceIndex> actualIndexes) {

		Assertions.assertEquals(expectedIndexes.size(), actualIndexes.size());

		for (int i = 0; i < expectedIndexes.size(); i++) {

			PropertyStringDeletePersistenceIndex expectedIndex = expectedIndexes.get(i);
			PropertyStringDeletePersistenceIndex actualIndex = actualIndexes.get(i);

			Assertions.assertEquals(expectedIndex.getId(), actualIndex.getId());
			Assertions.assertEquals(expectedIndex.getSource().getId(), actualIndex.getSource().getId());
			Assertions.assertEquals(expectedIndex.getSource().getType().getName(),
					actualIndex.getSource().getType().getName());
			Assertions.assertEquals(expectedIndex.getValueString(), actualIndex.getValueString());
		}
	}
}
