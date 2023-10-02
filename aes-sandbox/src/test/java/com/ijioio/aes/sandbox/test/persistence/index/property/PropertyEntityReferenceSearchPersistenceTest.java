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
import com.ijioio.test.model.PropertyEntityReferenceSearchPersistence;
import com.ijioio.test.model.PropertyEntityReferenceSearchPersistenceIndex;

public class PropertyEntityReferenceSearchPersistenceTest extends
		BasePropertySearchPersistenceTest<PropertyEntityReferenceSearchPersistenceIndex, EntityReference<? extends Some>> {

	@Entity( //
			name = PropertyEntityReferenceSearchPersistencePrototype.NAME, //
			types = { //
					@Type(name = "EntityReference<? extends Some>", type = Type.ENTITY_REFERENCE, parameters = @Parameter(name = Some.NAME, wildcard = true)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueEntityReference", type = "EntityReference<? extends Some>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyEntityReferenceSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueEntityReference", type = "EntityReference<? extends Some>") //
							} //
					) //
			} //
	)
	public static interface PropertyEntityReferenceSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyEntityReferenceSearchPersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-entity-reference-search-persistence.sql";
	}

	@Override
	protected Class<PropertyEntityReferenceSearchPersistenceIndex> getIndexClass() {
		return PropertyEntityReferenceSearchPersistenceIndex.class;
	}

	@Override
	protected List<PropertyEntityReferenceSearchPersistenceIndex> createIndexes() {

		List<PropertyEntityReferenceSearchPersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyEntityReferenceSearchPersistenceIndex index = new PropertyEntityReferenceSearchPersistenceIndex();

			index.setId(String.format("property-entity-reference-search-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-entity-reference-search-persistence-%s", i + 1),
					PropertyEntityReferenceSearchPersistence.class));
			index.setValueEntityReference(EntityReference.of(String.format("some-%s", i + 1), types.get(i)));

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected Property<EntityReference<? extends Some>> getProperty() {
		return PropertyEntityReferenceSearchPersistenceIndex.Properties.valueEntityReference;
	}

	@Override
	protected EntityReference<? extends Some> getPropertyValue(PropertyEntityReferenceSearchPersistenceIndex index) {
		return index.getValueEntityReference();
	}

	@Override
	protected void setPropertyValue(PropertyEntityReferenceSearchPersistenceIndex index,
			EntityReference<? extends Some> value) {
		index.setValueEntityReference(value);
	}

	@Override
	protected int comparePropertyValue(EntityReference<? extends Some> value1, EntityReference<? extends Some> value2) {
		return compare(getEntityReferenceId(value1), getEntityReferenceId(value2));
	}

	@Override
	protected void checkPropertyValue(EntityReference<? extends Some> expectedValue,
			EntityReference<? extends Some> actualValue) {

		Assertions.assertEquals(getEntityReferenceId(expectedValue), getEntityReferenceId(actualValue));
		Assertions.assertEquals(getEntityReferenceTypeName(expectedValue), getEntityReferenceTypeName(actualValue));
	}

	private String getEntityReferenceId(EntityReference<? extends Some> value) {
		return Optional.ofNullable(value).map(item -> item.getId()).orElse(null);
	}

	private String getEntityReferenceTypeName(EntityReference<? extends Some> value) {
		return Optional.ofNullable(value).map(item -> item.getType()).map(item -> item.getName()).orElse(null);
	}
}
