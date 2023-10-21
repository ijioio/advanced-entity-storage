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
import com.ijioio.test.model.PropertyCharDeletePersistence;
import com.ijioio.test.model.PropertyCharDeletePersistenceIndex;

public class PropertyCharDeletePersistenceTest
		extends BasePropertySearchPersistenceTest<PropertyCharDeletePersistenceIndex, Character> {

	@Entity( //
			name = PropertyCharDeletePersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueChar", type = Type.CHAR) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyCharDeletePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueChar", type = Type.CHAR) //
							} //
					) //
			} //
	)
	public static interface PropertyCharDeletePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyCharDeletePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyCharDeletePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-char-delete-persistence.sql";
	}

	@Override
	protected Class<PropertyCharDeletePersistenceIndex> getIndexClass() {
		return PropertyCharDeletePersistenceIndex.class;
	}

	@Override
	protected List<PropertyCharDeletePersistenceIndex> createIndexes() {

		List<PropertyCharDeletePersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyCharDeletePersistenceIndex index = new PropertyCharDeletePersistenceIndex();

			index.setId(String.format("property-char-delete-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-char-delete-persistence-%s", i + 1),
					PropertyCharDeletePersistence.class));
			index.setValueChar(characters.get(i));

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected Property<Character> getProperty() {
		return PropertyCharDeletePersistenceIndex.Properties.valueChar;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected Character getPropertyValue(PropertyCharDeletePersistenceIndex index) {
		return index.getValueChar();
	}

	@Override
	protected void setPropertyValue(PropertyCharDeletePersistenceIndex index, Character value) {
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
