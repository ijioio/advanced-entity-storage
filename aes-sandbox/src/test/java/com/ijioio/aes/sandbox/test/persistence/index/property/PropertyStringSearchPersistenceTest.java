package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.util.ArrayList;
import java.util.List;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.Property;
import com.ijioio.test.model.PropertyStringSearchPersistence;
import com.ijioio.test.model.PropertyStringSearchPersistenceIndex;

public class PropertyStringSearchPersistenceTest
		extends BasePropertySearchPersistenceTest<PropertyStringSearchPersistenceIndex, String> {

	@Entity( //
			name = PropertyStringSearchPersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueString", type = Type.STRING) //
							} //
					) //
			} //
	)
	public static interface PropertyStringSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringSearchPersistenceIndex";
	}

	@Override
	protected String getSqlScriptPath() throws Exception {
		return "persistence/index/property/property-string-search-persistence.sql";
	}

	@Override
	protected String getTableName() {
		return PropertyStringSearchPersistenceIndex.class.getSimpleName();
	}

	@Override
	protected Class<PropertyStringSearchPersistenceIndex> getIndexClass() {
		return PropertyStringSearchPersistenceIndex.class;
	}

	@Override
	protected List<PropertyStringSearchPersistenceIndex> createIndexes() {

		int count = random.nextInt(10) + 1;

		List<PropertyStringSearchPersistenceIndex> indexes = new ArrayList<>();

		for (int i = 0; i < count; i++) {

			PropertyStringSearchPersistenceIndex index = new PropertyStringSearchPersistenceIndex();

			index.setId(String.format("property-string-search-persistence-index-%s", i));
			index.setSource(EntityReference.of(String.format("property-string-search-persistence-%s", i),
					PropertyStringSearchPersistence.class));
			index.setValueString(String.format("value-%s", i));

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected Property<String> getProperty() {
		return PropertyStringSearchPersistenceIndex.Properties.valueString;
	}

	@Override
	protected String getPropertyValue(PropertyStringSearchPersistenceIndex index) {
		return index.getValueString();
	}

	@Override
	protected void setPropertyValue(PropertyStringSearchPersistenceIndex index, String value) {
		index.setValueString(value);
	}

	@Override
	protected int comparePropertyValue(PropertyStringSearchPersistenceIndex o1,
			PropertyStringSearchPersistenceIndex o2) {
		return compare(o1.getValueString(), o2.getValueString());
	}
}
