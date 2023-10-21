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
import com.ijioio.test.model.PropertyCharSearchPersistence;
import com.ijioio.test.model.PropertyCharSearchPersistenceIndex;

public class PropertyCharSearchPersistenceTest
		extends BasePropertySearchPersistenceTest<PropertyCharSearchPersistenceIndex, Character> {

	@Entity( //
			name = PropertyCharSearchPersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueChar", type = Type.CHAR) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyCharSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueChar", type = Type.CHAR) //
							} //
					) //
			} //
	)
	public static interface PropertyCharSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyCharSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyCharSearchPersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-char-search-persistence.sql";
	}

	@Override
	protected Class<PropertyCharSearchPersistenceIndex> getIndexClass() {
		return PropertyCharSearchPersistenceIndex.class;
	}

	@Override
	protected List<PropertyCharSearchPersistenceIndex> createIndexes() {

		List<PropertyCharSearchPersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyCharSearchPersistenceIndex index = new PropertyCharSearchPersistenceIndex();

			index.setId(String.format("property-char-search-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-char-search-persistence-%s", i + 1),
					PropertyCharSearchPersistence.class));
			index.setValueChar(characters.get(i));

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected Property<Character> getProperty() {
		return PropertyCharSearchPersistenceIndex.Properties.valueChar;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected Character getPropertyValue(PropertyCharSearchPersistenceIndex index) {
		return index.getValueChar();
	}

	@Override
	protected void setPropertyValue(PropertyCharSearchPersistenceIndex index, Character value) {
		index.setValueChar(value);
	}

	@Override
	protected int comparePropertyValue(Character value1, Character value2) {
		return compare(value1, value2);
	}

	@Override
	protected void checkPropertyValue(Character expectedValue, Character actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
