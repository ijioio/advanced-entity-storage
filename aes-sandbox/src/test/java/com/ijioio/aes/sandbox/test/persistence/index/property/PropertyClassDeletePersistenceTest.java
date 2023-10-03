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
import com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertyDeletePersistenceTest.Some;
import com.ijioio.test.model.PropertyClassDeletePersistence;
import com.ijioio.test.model.PropertyClassDeletePersistenceIndex;

public class PropertyClassDeletePersistenceTest
		extends BasePropertyDeletePersistenceTest<PropertyClassDeletePersistenceIndex, Class<? extends Some>> {

	@Entity( //
			name = PropertyClassDeletePersistencePrototype.NAME, //
			types = { //
					@Type(name = "Class<? extends Some>", type = Type.CLASS, parameters = @Parameter(name = Some.NAME, wildcard = true)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueClass", type = "Class<? extends Some>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyClassDeletePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueClass", type = "Class<? extends Some>") //
							} //
					) //
			} //
	)
	public static interface PropertyClassDeletePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassDeletePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyClassDeletePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-class-delete-persistence.sql";
	}

	@Override
	protected Class<PropertyClassDeletePersistenceIndex> getIndexClass() {
		return PropertyClassDeletePersistenceIndex.class;
	}

	@Override
	protected List<PropertyClassDeletePersistenceIndex> createIndexes() {

		List<PropertyClassDeletePersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyClassDeletePersistenceIndex index = new PropertyClassDeletePersistenceIndex();

			index.setId(String.format("property-class-delete-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-class-delete-persistence-%s", i + 1),
					PropertyClassDeletePersistence.class));
			index.setValueClass(types.get(i));

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected Property<Class<? extends Some>> getProperty() {
		return PropertyClassDeletePersistenceIndex.Properties.valueClass;
	}

	@Override
	protected Class<? extends Some> getPropertyValue(PropertyClassDeletePersistenceIndex index) {
		return index.getValueClass();
	}

	@Override
	protected void setPropertyValue(PropertyClassDeletePersistenceIndex index, Class<? extends Some> value) {
		index.setValueClass(value);
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
