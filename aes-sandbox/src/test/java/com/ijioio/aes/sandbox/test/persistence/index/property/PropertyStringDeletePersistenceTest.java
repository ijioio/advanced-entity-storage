package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.Property;
import com.ijioio.test.model.PropertyStringDeletePersistence;
import com.ijioio.test.model.PropertyStringDeletePersistenceIndex;

public class PropertyStringDeletePersistenceTest
		extends BasePropertyDeletePersistenceTest<PropertyStringDeletePersistenceIndex, String> {

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

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-string-delete-persistence.sql";
	}

	@Override
	protected boolean isFinal() {
		return false;
	}

	@Override
	protected Class<PropertyStringDeletePersistenceIndex> getIndexClass() {
		return PropertyStringDeletePersistenceIndex.class;
	}

	@Override
	protected List<PropertyStringDeletePersistenceIndex> createIndexes() {

		List<PropertyStringDeletePersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyStringDeletePersistenceIndex index = new PropertyStringDeletePersistenceIndex();

			index.setId(String.format("property-string-delete-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-string-delete-persistence-%s", i + 1),
					PropertyStringDeletePersistence.class));
			index.setValueString(String.format("value-%s", i + 1));

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected Property<String> getProperty() {
		return PropertyStringDeletePersistenceIndex.Properties.valueString;
	}

	@Override
	protected String getPropertyValue(PropertyStringDeletePersistenceIndex index) {
		return index.getValueString();
	}

	@Override
	protected void setPropertyValue(PropertyStringDeletePersistenceIndex index, String value) {
		index.setValueString(value);
	}

	@Override
	protected int comparePropertyValue(String value1, String value2) {
		return compare(value1, value2);
	}

	@Override
	protected void checkPropertyValue(String expectedValue, String actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
