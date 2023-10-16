package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.test.model.PropertyStringListFinalCreatePersistence;
import com.ijioio.test.model.PropertyStringListFinalCreatePersistenceIndex;

public class PropertyStringListFinalCreatePersistenceTest extends
		BasePropertyCollectionCreatePersistenceTest<PropertyStringListFinalCreatePersistenceIndex, List<String>, String> {

	@Entity( //
			name = PropertyStringListFinalCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = Type.LIST, parameters = @Parameter(name = Type.STRING)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = "List<String>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringListFinalCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueStringList", type = "List<String>", attributes = Attribute.FINAL) //
							} //
					) //
			} //
	)
	public static interface PropertyStringListFinalCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringListFinalCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringListFinalCreatePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-string-list-final-create-persistence.sql";
	}

	@Override
	protected boolean isFinal() {
		return true;
	}

	@Override
	protected String getTableName() {
		return PropertyStringListFinalCreatePersistenceIndex.class.getSimpleName();
	}

	@Override
	protected PropertyStringListFinalCreatePersistenceIndex createIndex() {

		PropertyStringListFinalCreatePersistenceIndex index = new PropertyStringListFinalCreatePersistenceIndex();

		index.setId("property-string-list-final-create-persistence-index");
		index.setSource(EntityReference.of("property-string-list-final-create-persistence",
				PropertyStringListFinalCreatePersistence.class));

		List<String> value = new ArrayList<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.add(String.format("value-%s", i + 1));
		}

		index.getValueStringList().clear();
		index.getValueStringList().addAll(value);

		return index;
	}

	@Override
	protected List<String> createEmptyPropertyValue() {
		return Collections.emptyList();
	}

	@Override
	protected List<String> getPropertyValue(PropertyStringListFinalCreatePersistenceIndex index) {
		return index.getValueStringList();
	}

	@Override
	protected void setPropertyValue(PropertyStringListFinalCreatePersistenceIndex index, List<String> value) {

		index.getValueStringList().clear();
		index.getValueStringList().addAll(value);
	}

	@Override
	protected void checkPropertyValue(List<String> value, ResultSet resultSet) throws Exception {
		Assertions.assertEquals(value, getArray(resultSet.getArray("valueStringList")));
	}
}
