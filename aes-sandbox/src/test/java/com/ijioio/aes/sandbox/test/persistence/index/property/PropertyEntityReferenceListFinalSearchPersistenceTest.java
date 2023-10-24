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
import com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertySearchPersistenceTest.Some;
import com.ijioio.test.model.PropertyEntityReferenceListFinalSearchPersistence;
import com.ijioio.test.model.PropertyEntityReferenceListFinalSearchPersistenceIndex;

public class PropertyEntityReferenceListFinalSearchPersistenceTest extends
		BasePropertyCollectionSearchPersistenceTest<PropertyEntityReferenceListFinalSearchPersistenceIndex, List<EntityReference<? extends Some>>, EntityReference<? extends Some>> {

	@Entity( //
			name = PropertyEntityReferenceListFinalSearchPersistencePrototype.NAME, //
			types = { //
					@Type(name = "EntityReference<? extends Some>", type = Type.ENTITY_REFERENCE, parameters = @Parameter(name = Some.NAME, wildcard = true)), //
					@Type(name = "List<EntityReference<? extends Some>>", type = Type.LIST, parameters = @Parameter(name = "EntityReference<? extends Some>")) //
			}, //
			properties = { //
					@EntityProperty(name = "valueEntityReferenceList", type = "List<EntityReference<? extends Some>>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyEntityReferenceListFinalSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueEntityReferenceList", type = "List<EntityReference<? extends Some>>", attributes = Attribute.FINAL) //
							} //
					) //
			} //
	)
	public static interface PropertyEntityReferenceListFinalSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceListFinalSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyEntityReferenceListFinalSearchPersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-entity-reference-list-final-search-persistence.sql";
	}

	@Override
	protected Class<PropertyEntityReferenceListFinalSearchPersistenceIndex> getIndexClass() {
		return PropertyEntityReferenceListFinalSearchPersistenceIndex.class;
	}

	@Override
	protected List<PropertyEntityReferenceListFinalSearchPersistenceIndex> createIndexes() {

		List<PropertyEntityReferenceListFinalSearchPersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyEntityReferenceListFinalSearchPersistenceIndex index = new PropertyEntityReferenceListFinalSearchPersistenceIndex();

			index.setId(String.format("property-entity-reference-list-final-search-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(
					String.format("property-entity-reference-list-final-search-persistence-%s", i + 1),
					PropertyEntityReferenceListFinalSearchPersistence.class));

			List<EntityReference<? extends Some>> value = new ArrayList<>();

			for (int j = 0; j < VALUE_MAX_COUNT; j++) {
				value.add(EntityReference.of(String.format("some-%s%s", i + 1, j + 1),
						collectionTypes.get(i + 1).get(j)));
			}

			index.getValueEntityReferenceList().clear();
			index.getValueEntityReferenceList().addAll(value);

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
		return PropertyEntityReferenceListFinalSearchPersistenceIndex.Properties.valueEntityReferenceList;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected List<EntityReference<? extends Some>> getPropertyValue(
			PropertyEntityReferenceListFinalSearchPersistenceIndex index) {
		return index.getValueEntityReferenceList();
	}

	@Override
	protected void setPropertyValue(PropertyEntityReferenceListFinalSearchPersistenceIndex index,
			List<EntityReference<? extends Some>> value) {

		index.getValueEntityReferenceList().clear();
		index.getValueEntityReferenceList().addAll(value);
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
