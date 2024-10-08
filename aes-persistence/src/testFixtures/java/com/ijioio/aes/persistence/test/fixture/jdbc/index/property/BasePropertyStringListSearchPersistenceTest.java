package com.ijioio.aes.persistence.test.fixture.jdbc.index.property;

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
import com.ijioio.aes.core.Property;
import com.ijioio.aes.persistence.test.fixture.jdbc.PersistenceTestUtil;
import com.ijioio.test.model.PropertyStringListSearchPersistence;
import com.ijioio.test.model.PropertyStringListSearchPersistenceIndex;

public abstract class BasePropertyStringListSearchPersistenceTest extends
		BasePropertyCollectionSearchPersistenceTest<PropertyStringListSearchPersistenceIndex, List<String>, String> {

	@Entity( //
			name = PropertyStringListSearchPersistencePrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = Type.LIST, parameters = @Parameter(name = Type.STRING)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = "List<String>"), //
					@EntityProperty(name = "otherValueStringList", type = "List<String>"), //
					@EntityProperty(name = "otherValueStringSingle", type = Type.STRING) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringListSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueStringList", type = "List<String>"), //
									@EntityIndexProperty(name = "otherValueStringList", type = "List<String>"), //
									@EntityIndexProperty(name = "otherValueStringSingle", type = Type.STRING) //
							} //
					) //
			} //
	)
	public static interface PropertyStringListSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringListSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringListSearchPersistenceIndex";
	}

	@Override
	protected Class<PropertyStringListSearchPersistenceIndex> getIndexClass() {
		return PropertyStringListSearchPersistenceIndex.class;
	}

	@Override
	protected List<PropertyStringListSearchPersistenceIndex> createIndexes() {

		List<PropertyStringListSearchPersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyStringListSearchPersistenceIndex index = new PropertyStringListSearchPersistenceIndex();

			index.setId(String.format("property-string-list-search-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-string-list-search-persistence-%s", i + 1),
					PropertyStringListSearchPersistence.class));

			List<String> value = new ArrayList<>();

			for (int j = 0; j < VALUE_MAX_COUNT; j++) {
				value.add(String.format("value%s%s", i + 1, j + 1));
			}

			index.setValueStringList(value);

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected List<String> createEmptyPropertyValue() {
		return Collections.emptyList();
	}

	@Override
	protected List<String> createAllNullPropertyValue() {

		List<String> value = new ArrayList<>();

		for (int j = 0; j < VALUE_MAX_COUNT; j++) {
			value.add(null);
		}

		return value;
	}

	@Override
	protected List<String> createAllSamePropertyValue(int i) {

		List<String> value = new ArrayList<>();

		for (int j = 0; j < VALUE_MAX_COUNT; j++) {
			value.add(String.format("value%s", i + 1));
		}

		return value;
	}

	@Override
	protected Property<List<String>> getProperty() {
		return PropertyStringListSearchPersistenceIndex.Properties.valueStringList;
	}

	@Override
	protected Property<List<String>> getOtherProperty() {
		return PropertyStringListSearchPersistenceIndex.Properties.otherValueStringList;
	}

	@Override
	protected Property<String> getOtherSingleProperty() {
		return PropertyStringListSearchPersistenceIndex.Properties.otherValueStringSingle;
	}

	@Override
	protected List<String> getPropertyValue(PropertyStringListSearchPersistenceIndex index) {
		return index.getValueStringList();
	}

	@Override
	protected List<String> getOtherPropertyValue(PropertyStringListSearchPersistenceIndex index) {
		return index.getOtherValueStringList();
	}

	@Override
	protected String getOtherSinglePropertyValue(PropertyStringListSearchPersistenceIndex index) {
		return index.getOtherValueStringSingle();
	}

	@Override
	protected void setPropertyValue(PropertyStringListSearchPersistenceIndex index, List<String> value) {
		index.setValueStringList(value);
	}

	@Override
	protected void setOtherPropertyValue(PropertyStringListSearchPersistenceIndex index, List<String> value) {
		index.setOtherValueStringList(value);
	}

	@Override
	protected void setOtherSinglePropertyValue(PropertyStringListSearchPersistenceIndex index, String value) {
		index.setOtherValueStringSingle(value);
	}

	@Override
	protected int comparePropertyValue(List<String> value1, List<String> value2) {
		return PersistenceTestUtil.compare(value1, value2);
	}

	@Override
	protected int comparePropertyValueElement(String element1, String element2) {
		return PersistenceTestUtil.compare(element1, element2);
	}

	@Override
	protected void checkPropertyValue(List<String> expectedValue, List<String> actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
