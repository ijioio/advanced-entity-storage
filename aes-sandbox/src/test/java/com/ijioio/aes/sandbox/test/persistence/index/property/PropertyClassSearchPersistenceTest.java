package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertySearchPersistenceTest.Some;
import com.ijioio.test.model.PropertyClassSearchPersistence;
import com.ijioio.test.model.PropertyClassSearchPersistenceIndex;

public class PropertyClassSearchPersistenceTest
		extends BasePropertySearchPersistenceTest<PropertyClassSearchPersistenceIndex, Class<? extends Some>> {

	@Entity( //
			name = PropertyClassSearchPersistencePrototype.NAME, //
			types = { //
					@Type(name = "Class<? extends Some>", type = Type.CLASS, parameters = @Parameter(name = Some.NAME, wildcard = true)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueClass", type = "Class<? extends Some>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyClassSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueClass", type = "Class<? extends Some>") //
							} //
					) //
			} //
	)
	public static interface PropertyClassSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyClassSearchPersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-class-search-persistence.sql";
	}

	@Override
	protected Class<PropertyClassSearchPersistenceIndex> getIndexClass() {
		return PropertyClassSearchPersistenceIndex.class;
	}

	@Override
	protected List<PropertyClassSearchPersistenceIndex> createIndexes() {

		List<PropertyClassSearchPersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyClassSearchPersistenceIndex index = new PropertyClassSearchPersistenceIndex();

			index.setId(String.format("property-class-search-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-class-search-persistence-%s", i + 1),
					PropertyClassSearchPersistence.class));
			index.setValueClass(types.get(i));

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected Property<Class<? extends Some>> getProperty() {
		return PropertyClassSearchPersistenceIndex.Properties.valueClass;
	}

	@Override
	protected Class<? extends Some> getPropertyValue(PropertyClassSearchPersistenceIndex index) {
		return index.getValueClass();
	}

	@Override
	protected void setPropertyValue(PropertyClassSearchPersistenceIndex index, Class<? extends Some> value) {
		index.setValueClass(value);
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected int comparePropertyValue(Class<? extends Some> value1, Class<? extends Some> value2) {
		return compare(getClassName(value1), getClassName(value2));
	}

	@Override
	protected void checkPropertyValue(Class<? extends Some> expectedValue, Class<? extends Some> actualValue) {
		Assertions.assertEquals(getClassName(expectedValue), getClassName(actualValue));
	}

	private String getClassName(Class<? extends Some> value) {
		return Optional.ofNullable(value).map(item -> item.getName()).orElse(null);
	}
}
