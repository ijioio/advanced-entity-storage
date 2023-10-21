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
import com.ijioio.test.model.PropertyBooleanSearchPersistence;
import com.ijioio.test.model.PropertyBooleanSearchPersistenceIndex;

public class PropertyBooleanSearchPersistenceTest
		extends BasePropertySearchPersistenceTest<PropertyBooleanSearchPersistenceIndex, Boolean> {

	@Entity( //
			name = PropertyBooleanSearchPersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueBoolean", type = Type.BOOLEAN) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyBooleanSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueBoolean", type = Type.BOOLEAN) //
							} //
					) //
			} //
	)
	public static interface PropertyBooleanSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyBooleanSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyBooleanSearchPersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-boolean-search-persistence.sql";
	}

	@Override
	protected Class<PropertyBooleanSearchPersistenceIndex> getIndexClass() {
		return PropertyBooleanSearchPersistenceIndex.class;
	}

	@Override
	protected List<PropertyBooleanSearchPersistenceIndex> createIndexes() {

		List<PropertyBooleanSearchPersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyBooleanSearchPersistenceIndex index = new PropertyBooleanSearchPersistenceIndex();

			index.setId(String.format("property-boolean-search-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-boolean-search-persistence-%s", i + 1),
					PropertyBooleanSearchPersistence.class));
			index.setValueBoolean(random.nextBoolean());

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected Property<Boolean> getProperty() {
		return PropertyBooleanSearchPersistenceIndex.Properties.valueBoolean;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected Boolean getPropertyValue(PropertyBooleanSearchPersistenceIndex index) {
		return index.isValueBoolean();
	}

	@Override
	protected void setPropertyValue(PropertyBooleanSearchPersistenceIndex index, Boolean value) {
		index.setValueBoolean(value);
	}

	@Override
	protected int comparePropertyValue(Boolean value1, Boolean value2) {
		return compare(value1, value2);
	}

	@Override
	protected void checkPropertyValue(Boolean expectedValue, Boolean actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
