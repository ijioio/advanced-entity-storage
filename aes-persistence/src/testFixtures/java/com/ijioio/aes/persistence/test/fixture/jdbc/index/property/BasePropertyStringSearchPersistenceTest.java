package com.ijioio.aes.persistence.test.fixture.jdbc.index.property;

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
import com.ijioio.aes.persistence.test.fixture.jdbc.PersistenceTestUtil;
import com.ijioio.test.model.PropertyStringSearchPersistence;
import com.ijioio.test.model.PropertyStringSearchPersistenceIndex;

public abstract class BasePropertyStringSearchPersistenceTest
		extends BasePropertySearchPersistenceTest<PropertyStringSearchPersistenceIndex, String> {

	@Entity( //
			name = PropertyStringSearchPersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING), //
					@EntityProperty(name = "otherValueString", type = Type.STRING) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueString", type = Type.STRING), //
									@EntityIndexProperty(name = "otherValueString", type = Type.STRING) //
							} //
					) //
			} //
	)
	public static interface PropertyStringSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringSearchPersistenceIndex";
	}

	@Override
	protected Class<PropertyStringSearchPersistenceIndex> getIndexClass() {
		return PropertyStringSearchPersistenceIndex.class;
	}

	@Override
	protected List<PropertyStringSearchPersistenceIndex> createIndexes() {

		List<PropertyStringSearchPersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyStringSearchPersistenceIndex index = new PropertyStringSearchPersistenceIndex();

			index.setId(String.format("property-string-search-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-string-search-persistence-%s", i + 1),
					PropertyStringSearchPersistence.class));
			index.setValueString(String.format("value-%s", i + 1));

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected Property<String> getProperty() {
		return PropertyStringSearchPersistenceIndex.Properties.valueString;
	}

	@Override
	protected Property<String> getOtherProperty() {
		return PropertyStringSearchPersistenceIndex.Properties.otherValueString;
	}

	@Override
	protected String getPropertyValue(PropertyStringSearchPersistenceIndex index) {
		return index.getValueString();
	}

	@Override
	protected String getOtherPropertyValue(PropertyStringSearchPersistenceIndex index) {
		return index.getOtherValueString();
	}

	@Override
	protected void setPropertyValue(PropertyStringSearchPersistenceIndex index, String value) {
		index.setValueString(value);
	}

	@Override
	protected void setOtherPropertyValue(PropertyStringSearchPersistenceIndex index, String value) {
		index.setOtherValueString(value);
	}

	@Override
	protected int comparePropertyValue(String value1, String value2) {
		return PersistenceTestUtil.compare(value1, value2);
	}

	@Override
	protected void checkPropertyValue(String expectedValue, String actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
