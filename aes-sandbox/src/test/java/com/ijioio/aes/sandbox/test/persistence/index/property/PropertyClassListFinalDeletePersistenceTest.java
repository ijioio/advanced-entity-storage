package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertyDeletePersistenceTest.Some;
import com.ijioio.test.model.PropertyClassListFinalDeletePersistence;
import com.ijioio.test.model.PropertyClassListFinalDeletePersistenceIndex;

public class PropertyClassListFinalDeletePersistenceTest extends
		BasePropertyCollectionDeletePersistenceTest<PropertyClassListFinalDeletePersistenceIndex, List<Class<? extends Some>>, Class<? extends Some>> {

	@Entity( //
			name = PropertyClassListFinalDeletePersistencePrototype.NAME, //
			types = { //
					@Type(name = "Class<? extends Some>", type = Type.CLASS, parameters = @Parameter(name = Some.NAME, wildcard = true)), //
					@Type(name = "List<Class<? extends Some>>", type = Type.LIST, parameters = @Parameter(name = "Class<? extends Some>")) //
			}, //
			properties = { //
					@EntityProperty(name = "valueClassList", type = "List<Class<? extends Some>>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyClassListFinalDeletePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueClassList", type = "List<Class<? extends Some>>", attributes = Attribute.FINAL) //
							} //
					) //
			} //
	)
	public static interface PropertyClassListFinalDeletePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassListFinalDeletePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyClassListFinalDeletePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-class-list-final-delete-persistence.sql";
	}

	@Override
	protected boolean isFinal() {
		return true;
	}

	@Override
	protected Class<PropertyClassListFinalDeletePersistenceIndex> getIndexClass() {
		return PropertyClassListFinalDeletePersistenceIndex.class;
	}

	@Override
	protected List<PropertyClassListFinalDeletePersistenceIndex> createIndexes() {

		List<PropertyClassListFinalDeletePersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyClassListFinalDeletePersistenceIndex index = new PropertyClassListFinalDeletePersistenceIndex();

			index.setId(String.format("property-class-list-final-delete-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-class-list-final-delete-persistence-%s", i + 1),
					PropertyClassListFinalDeletePersistence.class));

			List<Class<? extends Some>> value = new ArrayList<>();

			for (int j = 0; j < VALUE_MAX_COUNT; j++) {
				value.add(collectionTypes.get(i + 1).get(j));
			}

			index.getValueClassList().clear();
			index.getValueClassList().addAll(value);

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
		return PropertyClassListFinalDeletePersistenceIndex.Properties.valueClassList;
	}

	@Override
	protected List<Class<? extends Some>> getPropertyValue(PropertyClassListFinalDeletePersistenceIndex index) {
		return index.getValueClassList();
	}

	@Override
	protected void setPropertyValue(PropertyClassListFinalDeletePersistenceIndex index,
			List<Class<? extends Some>> value) {

		index.getValueClassList().clear();
		index.getValueClassList().addAll(value);
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
