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
import com.ijioio.test.model.PropertyEntityReferenceDeletePersistence;
import com.ijioio.test.model.PropertyEntityReferenceDeletePersistenceIndex;

public class PropertyEntityReferenceDeletePersistenceTest extends
		BasePropertyDeletePersistenceTest<PropertyEntityReferenceDeletePersistenceIndex, EntityReference<? extends Some>> {

	@Entity( //
			name = PropertyEntityReferenceDeletePersistencePrototype.NAME, //
			types = { //
					@Type(name = "EntityReference<? extends Some>", type = Type.ENTITY_REFERENCE, parameters = @Parameter(name = Some.NAME, wildcard = true)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueEntityReference", type = "EntityReference<? extends Some>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyEntityReferenceDeletePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueEntityReference", type = "EntityReference<? extends Some>") //
							} //
					) //
			} //
	)
	public static interface PropertyEntityReferenceDeletePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceDeletePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyEntityReferenceDeletePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-entity-reference-delete-persistence.sql";
	}

	@Override
	protected boolean isFinal() {
		return false;
	}

	@Override
	protected Class<PropertyEntityReferenceDeletePersistenceIndex> getIndexClass() {
		return PropertyEntityReferenceDeletePersistenceIndex.class;
	}

	@Override
	protected List<PropertyEntityReferenceDeletePersistenceIndex> createIndexes() {

		List<PropertyEntityReferenceDeletePersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyEntityReferenceDeletePersistenceIndex index = new PropertyEntityReferenceDeletePersistenceIndex();

			index.setId(String.format("property-entity-reference-delete-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-entity-reference-delete-persistence-%s", i + 1),
					PropertyEntityReferenceDeletePersistence.class));
			index.setValueEntityReference(EntityReference.of(String.format("some-%s", i + 1), types.get(i)));

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected Property<EntityReference<? extends Some>> getProperty() {
		return PropertyEntityReferenceDeletePersistenceIndex.Properties.valueEntityReference;
	}

	@Override
	protected EntityReference<? extends Some> getPropertyValue(PropertyEntityReferenceDeletePersistenceIndex index) {
		return index.getValueEntityReference();
	}

	@Override
	protected void setPropertyValue(PropertyEntityReferenceDeletePersistenceIndex index,
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
