package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.test.model.PropertyStringListCreatePersistence;
import com.ijioio.test.model.PropertyStringListCreatePersistenceIndex;

public class PropertyStringListCreatePersistenceTest extends
		BasePropertyCollectionCreatePersistenceTest<PropertyStringListCreatePersistenceIndex, List<String>, String> {

	@Entity( //
			name = PropertyStringListCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = Type.LIST, parameters = @Parameter(name = Type.STRING)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = "List<String>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringListCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueStringList", type = "List<String>") //
							} //
					) //
			} //
	)
	public static interface PropertyStringListCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringListCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringListCreatePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-string-list-create-persistence.sql";
	}

	@Override
	protected String getTableName() {
		return PropertyStringListCreatePersistenceIndex.class.getSimpleName();
	}

	@Override
	protected PropertyStringListCreatePersistenceIndex createIndex() {

		PropertyStringListCreatePersistenceIndex index = new PropertyStringListCreatePersistenceIndex();

		index.setId("property-string-list-create-persistence-index");
		index.setSource(EntityReference.of("property-string-list-create-persistence",
				PropertyStringListCreatePersistence.class));

		List<String> value = new ArrayList<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.add(String.format("value-%s", i + 1));
		}

		index.setValueStringList(value);

		return index;
	}

	@Override
	protected List<String> createEmptyPropertyValue() {
		return Collections.emptyList();
	}

	@Override
	protected List<String> getPropertyValue(PropertyStringListCreatePersistenceIndex index) {
		return index.getValueStringList();
	}

	@Override
	protected void setPropertyValue(PropertyStringListCreatePersistenceIndex index, List<String> value) {
		index.setValueStringList(value);
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected void checkPropertyValue(List<String> value, ResultSet resultSet) throws Exception {
		Assertions.assertEquals(value, getArray(resultSet.getArray("valueStringList")));
	}
}
