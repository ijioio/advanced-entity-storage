package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.sql.ResultSet;
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
import com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertyCreatePersistenceTest.Some;
import com.ijioio.test.model.PropertyEntityReferenceListCreatePersistence;
import com.ijioio.test.model.PropertyEntityReferenceListCreatePersistenceIndex;

public class PropertyEntityReferenceListCreatePersistenceTest extends
		BasePropertyCollectionCreatePersistenceTest<PropertyEntityReferenceListCreatePersistenceIndex, List<EntityReference<? extends Some>>, EntityReference<? extends Some>> {

	@Entity( //
			name = PropertyEntityReferenceListCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "EntityReference<? extends Some>", type = Type.ENTITY_REFERENCE, parameters = @Parameter(name = Some.NAME, wildcard = true)), //
					@Type(name = "List<EntityReference<? extends Some>>", type = Type.LIST, parameters = @Parameter(name = "EntityReference<? extends Some>")) //
			}, //
			properties = { //
					@EntityProperty(name = "valueEntityReferenceList", type = "List<EntityReference<? extends Some>>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyEntityReferenceListCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueEntityReferenceList", type = "List<EntityReference<? extends Some>>") //
							} //
					) //
			} //
	)
	public static interface PropertyEntityReferenceListCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceListCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyEntityReferenceListCreatePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-entity-reference-list-create-persistence.sql";
	}

	@Override
	protected String getTableName() {
		return PropertyEntityReferenceListCreatePersistenceIndex.class.getSimpleName();
	}

	@Override
	protected PropertyEntityReferenceListCreatePersistenceIndex createIndex() {

		PropertyEntityReferenceListCreatePersistenceIndex index = new PropertyEntityReferenceListCreatePersistenceIndex();

		index.setId("property-entity-reference-list-create-persistence-index");
		index.setSource(EntityReference.of("property-entity-reference-list-create-persistence",
				PropertyEntityReferenceListCreatePersistence.class));

		List<EntityReference<? extends Some>> value = new ArrayList<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.add(EntityReference.of(String.format("some-%s", i + 1), types.get(i)));
		}

		index.setValueEntityReferenceList(value);

		return index;
	}

	@Override
	protected List<EntityReference<? extends Some>> createEmptyPropertyValue() {
		return Collections.emptyList();
	}

	@Override
	protected List<EntityReference<? extends Some>> getPropertyValue(
			PropertyEntityReferenceListCreatePersistenceIndex index) {
		return index.getValueEntityReferenceList();
	}

	@Override
	protected void setPropertyValue(PropertyEntityReferenceListCreatePersistenceIndex index,
			List<EntityReference<? extends Some>> value) {
		index.setValueEntityReferenceList(value);
	}

	@Override
	protected void checkPropertyValue(List<EntityReference<? extends Some>> value, ResultSet resultSet)
			throws Exception {

		Assertions.assertEquals(
				Optional.ofNullable(value)
						.map(item -> item.stream().map(element -> getEntityReferenceId(element))
								.collect(Collectors.toList()))
						.orElse(null),
				getArray(resultSet.getArray("valueEntityReferenceListId")));
		Assertions.assertEquals(
				Optional.ofNullable(value)
						.map(item -> item.stream().map(element -> getEntityReferenceTypeName(element))
								.collect(Collectors.toList()))
						.orElse(null),
				getArray(resultSet.getArray("valueEntityReferenceListType")));
	}

	private String getEntityReferenceId(EntityReference<? extends Some> element) {
		return Optional.ofNullable(element).map(item -> item.getId()).orElse(null);
	}

	private String getEntityReferenceTypeName(EntityReference<? extends Some> element) {
		return Optional.ofNullable(element).map(item -> item.getType()).map(item -> item.getName()).orElse(null);
	}
}
