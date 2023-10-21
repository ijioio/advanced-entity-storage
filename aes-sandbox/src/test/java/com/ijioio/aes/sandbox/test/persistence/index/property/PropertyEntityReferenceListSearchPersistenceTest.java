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
import com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertySearchPersistenceTest.Some;
import com.ijioio.test.model.PropertyEntityReferenceListSearchPersistence;
import com.ijioio.test.model.PropertyEntityReferenceListSearchPersistenceIndex;

public class PropertyEntityReferenceListSearchPersistenceTest extends
		BasePropertyCollectionSearchPersistenceTest<PropertyEntityReferenceListSearchPersistenceIndex, List<EntityReference<? extends Some>>, EntityReference<? extends Some>> {

	@Entity( //
			name = PropertyEntityReferenceListSearchPersistencePrototype.NAME, //
			types = { //
					@Type(name = "EntityReference<? extends Some>", type = Type.ENTITY_REFERENCE, parameters = @Parameter(name = Some.NAME, wildcard = true)), //
					@Type(name = "List<EntityReference<? extends Some>>", type = Type.LIST, parameters = @Parameter(name = "EntityReference<? extends Some>")) //
			}, //
			properties = { //
					@EntityProperty(name = "valueEntityReferenceList", type = "List<EntityReference<? extends Some>>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyEntityReferenceListSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueEntityReferenceList", type = "List<EntityReference<? extends Some>>") //
							} //
					) //
			} //
	)
	public static interface PropertyEntityReferenceListSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceListSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyEntityReferenceListSearchPersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-entity-reference-list-search-persistence.sql";
	}

	@Override
	protected Class<PropertyEntityReferenceListSearchPersistenceIndex> getIndexClass() {
		return PropertyEntityReferenceListSearchPersistenceIndex.class;
	}

	@Override
	protected List<PropertyEntityReferenceListSearchPersistenceIndex> createIndexes() {

		List<PropertyEntityReferenceListSearchPersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyEntityReferenceListSearchPersistenceIndex index = new PropertyEntityReferenceListSearchPersistenceIndex();

			index.setId(String.format("property-entity-reference-list-search-persistence-index-%s", i + 1));
			index.setSource(
					EntityReference.of(String.format("property-entity-reference-list-search-persistence-%s", i + 1),
							PropertyEntityReferenceListSearchPersistence.class));

			List<EntityReference<? extends Some>> value = new ArrayList<>();

			for (int j = 0; j < VALUE_MAX_COUNT; j++) {
				value.add(EntityReference.of(String.format("some-%s%s", i + 1, j + 1),
						collectionTypes.get(i + 1).get(j)));
			}

			index.setValueEntityReferenceList(value);

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected List<EntityReference<? extends Some>> createEmptyPropertyValue() {
		return Collections.emptyList();
	}

	@Override
	protected List<EntityReference<? extends Some>> createAllNullPropertyValue() {

		List<EntityReference<? extends Some>> value = new ArrayList<>();

		for (int j = 0; j < VALUE_MAX_COUNT; j++) {
			value.add(null);
		}

		return value;
	}

	@Override
	protected List<EntityReference<? extends Some>> createAllSamePropertyValue(int i) {

		List<EntityReference<? extends Some>> value = new ArrayList<>();

		for (int j = 0; j < VALUE_MAX_COUNT; j++) {
			value.add(EntityReference.of(String.format("some-%s", i + 1), types.get(i)));
		}

		return value;
	}

	@Override
	protected Property<List<EntityReference<? extends Some>>> getProperty() {
		return PropertyEntityReferenceListSearchPersistenceIndex.Properties.valueEntityReferenceList;
	}

	@Override
	protected List<EntityReference<? extends Some>> getPropertyValue(
			PropertyEntityReferenceListSearchPersistenceIndex index) {
		return index.getValueEntityReferenceList();
	}

	@Override
	protected void setPropertyValue(PropertyEntityReferenceListSearchPersistenceIndex index,
			List<EntityReference<? extends Some>> value) {
		index.setValueEntityReferenceList(value);
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected int comparePropertyValue(List<EntityReference<? extends Some>> value1,
			List<EntityReference<? extends Some>> value2) {
		return compare(
				Optional.ofNullable(value1)
						.map(item -> item.stream().map(element -> getEntityReferenceId(element))
								.collect(Collectors.toList()))
						.orElse(null),
				Optional.ofNullable(value2).map(item -> item.stream().map(element -> getEntityReferenceId(element))
						.collect(Collectors.toList())).orElse(null));
	}

	@Override
	protected int comparePropertyValueElement(EntityReference<? extends Some> element1,
			EntityReference<? extends Some> element2) {
		return compare(getEntityReferenceId(element1), getEntityReferenceId(element2));
	}

	@Override
	protected void checkPropertyValue(List<EntityReference<? extends Some>> expectedValue,
			List<EntityReference<? extends Some>> actualValue) {

		Assertions.assertEquals(
				Optional.ofNullable(expectedValue)
						.map(item -> item.stream().map(element -> getEntityReferenceId(element))
								.collect(Collectors.toList()))
						.orElse(null),
				Optional.ofNullable(actualValue).map(item -> item.stream().map(element -> getEntityReferenceId(element))
						.collect(Collectors.toList())).orElse(null));
		Assertions.assertEquals(
				Optional.ofNullable(expectedValue)
						.map(item -> item.stream().map(element -> getEntityReferenceTypeName(element))
								.collect(Collectors.toList()))
						.orElse(null),
				Optional.ofNullable(actualValue).map(item -> item.stream()
						.map(element -> getEntityReferenceTypeName(element)).collect(Collectors.toList()))
						.orElse(null));
	}

	private String getEntityReferenceId(EntityReference<? extends Some> element) {
		return Optional.ofNullable(element).map(item -> item.getId()).orElse(null);
	}

	private String getEntityReferenceTypeName(EntityReference<? extends Some> element) {
		return Optional.ofNullable(element).map(item -> item.getType()).map(item -> item.getName()).orElse(null);
	}
}
