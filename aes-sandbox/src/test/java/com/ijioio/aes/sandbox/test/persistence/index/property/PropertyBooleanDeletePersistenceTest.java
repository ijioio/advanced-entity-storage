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
import com.ijioio.test.model.PropertyBooleanDeletePersistence;
import com.ijioio.test.model.PropertyBooleanDeletePersistenceIndex;

public class PropertyBooleanDeletePersistenceTest
		extends BasePropertyDeletePersistenceTest<PropertyBooleanDeletePersistenceIndex, Boolean> {

	@Entity( //
			name = PropertyBooleanDeletePersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueBoolean", type = Type.BOOLEAN) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyBooleanDeletePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueBoolean", type = Type.BOOLEAN) //
							} //
					) //
			} //
	)
	public static interface PropertyBooleanDeletePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyBooleanDeletePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyBooleanDeletePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-boolean-delete-persistence.sql";
	}

	@Override
	protected Class<PropertyBooleanDeletePersistenceIndex> getIndexClass() {
		return PropertyBooleanDeletePersistenceIndex.class;
	}

	@Override
	protected List<PropertyBooleanDeletePersistenceIndex> createIndexes() {

		List<PropertyBooleanDeletePersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyBooleanDeletePersistenceIndex index = new PropertyBooleanDeletePersistenceIndex();

			index.setId(String.format("property-boolean-delete-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-boolean-delete-persistence-%s", i + 1),
					PropertyBooleanDeletePersistence.class));
			index.setValueBoolean(random.nextBoolean());

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected Property<Boolean> getProperty() {
		return PropertyBooleanDeletePersistenceIndex.Properties.valueBoolean;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected Boolean getPropertyValue(PropertyBooleanDeletePersistenceIndex index) {
		return index.isValueBoolean();
	}

	@Override
	protected void setPropertyValue(PropertyBooleanDeletePersistenceIndex index, Boolean value) {
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
