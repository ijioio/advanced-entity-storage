package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
import com.ijioio.test.model.PropertyClassListDeletePersistence;
import com.ijioio.test.model.PropertyClassListDeletePersistenceIndex;

public class PropertyClassListDeletePersistenceTest extends
		BasePropertyCollectionDeletePersistenceTest<PropertyClassListDeletePersistenceIndex, List<Class<? extends Some>>, Class<? extends Some>> {

	@Entity( //
			name = PropertyClassListDeletePersistencePrototype.NAME, //
			types = { //
					@Type(name = "Class<? extends Some>", type = Type.CLASS, parameters = @Parameter(name = Some.NAME, wildcard = true)), //
					@Type(name = "List<Class<? extends Some>>", type = Type.LIST, parameters = @Parameter(name = "Class<? extends Some>")) //
			}, //
			properties = { //
					@EntityProperty(name = "valueClassList", type = "List<Class<? extends Some>>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyClassListDeletePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueClassList", type = "List<Class<? extends Some>>") //
							} //
					) //
			} //
	)
	public static interface PropertyClassListDeletePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassListDeletePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyClassListDeletePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-class-list-delete-persistence.sql";
	}

	@Override
	protected Class<PropertyClassListDeletePersistenceIndex> getIndexClass() {
		return PropertyClassListDeletePersistenceIndex.class;
	}

	@Override
	protected List<PropertyClassListDeletePersistenceIndex> createIndexes() {

		List<PropertyClassListDeletePersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyClassListDeletePersistenceIndex index = new PropertyClassListDeletePersistenceIndex();

			index.setId(String.format("property-class-list-delete-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-class-list-delete-persistence-%s", i + 1),
					PropertyClassListDeletePersistence.class));

			List<Class<? extends Some>> value = new ArrayList<>();

			for (int j = 0; j < VALUE_MAX_COUNT; j++) {
				value.add(collectionTypes.get(i + 1).get(j));
			}

			index.setValueClassList(value);

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected List<Class<? extends Some>> createEmptyPropertyValue() {
		return Collections.emptyList();
	}

	@Override
	protected List<Class<? extends Some>> createAllNullPropertyValue() {

		List<Class<? extends Some>> value = new ArrayList<>();

		for (int j = 0; j < VALUE_MAX_COUNT; j++) {
			value.add(null);
		}

		return value;
	}

	@Override
	protected List<Class<? extends Some>> createAllSamePropertyValue(int i) {

		List<Class<? extends Some>> value = new ArrayList<>();

		for (int j = 0; j < VALUE_MAX_COUNT; j++) {
			value.add(types.get(i));
		}

		return value;
	}

	@Override
	protected Property<List<Class<? extends Some>>> getProperty() {
		return PropertyClassListDeletePersistenceIndex.Properties.valueClassList;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected List<Class<? extends Some>> getPropertyValue(PropertyClassListDeletePersistenceIndex index) {
		return index.getValueClassList();
	}

	@Override
	protected void setPropertyValue(PropertyClassListDeletePersistenceIndex index, List<Class<? extends Some>> value) {
		index.setValueClassList(value);
	}

	@Override
	protected int comparePropertyValue(List<Class<? extends Some>> value1, List<Class<? extends Some>> value2) {
		return compare(
				Optional.ofNullable(value1)
						.map(item -> item.stream().map(element -> getClassName(element)).collect(Collectors.toList()))
						.orElse(null),
				Optional.ofNullable(value2)
						.map(item -> item.stream().map(element -> getClassName(element)).collect(Collectors.toList()))
						.orElse(null));
	}

	@Override
	protected int comparePropertyValueElement(Class<? extends Some> element1, Class<? extends Some> element2) {
		return compare(getClassName(element1), getClassName(element2));
	}

	@Override
	protected void checkPropertyValue(List<Class<? extends Some>> expectedValue,
			List<Class<? extends Some>> actualValue) {
		Assertions.assertEquals(
				Optional.ofNullable(expectedValue)
						.map(item -> item.stream().map(element -> getClassName(element)).collect(Collectors.toList()))
						.orElse(null),
				Optional.ofNullable(actualValue)
						.map(item -> item.stream().map(element -> getClassName(element)).collect(Collectors.toList()))
						.orElse(null));
	}

	private String getClassName(Class<? extends Some> element) {
		return Optional.ofNullable(element).map(item -> item.getName()).orElse(null);
	}
}
