package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.sql.ResultSet;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.test.model.PropertyBooleanCreatePersistence;
import com.ijioio.test.model.PropertyBooleanCreatePersistenceIndex;

public class PropertyBooleanCreatePersistenceTest
		extends BasePropertyCreatePersistenceTest<PropertyBooleanCreatePersistenceIndex, Boolean> {

	@Entity( //
			name = PropertyBooleanCreatePersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueBoolean", type = Type.BOOLEAN) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyBooleanCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueBoolean", type = Type.BOOLEAN) //
							} //
					) //
			} //
	)
	public static interface PropertyBooleanCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyBooleanCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyBooleanCreatePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() {
		return "property-boolean-create-persistence.sql";
	}

	@Override
	protected boolean isFinal() {
		return true;
	}

	@Override
	protected String getTableName() {
		return PropertyBooleanCreatePersistenceIndex.class.getSimpleName();
	}

	@Override
	protected PropertyBooleanCreatePersistenceIndex createIndex() {

		PropertyBooleanCreatePersistenceIndex index = new PropertyBooleanCreatePersistenceIndex();

		index.setId("property-boolean-create-persistence-index");
		index.setSource(
				EntityReference.of("property-boolean-create-persistence", PropertyBooleanCreatePersistence.class));
		index.setValueBoolean(true);

		return index;
	}

	@Override
	protected Boolean getPropertyValue(PropertyBooleanCreatePersistenceIndex index) {
		return index.isValueBoolean();
	}

	@Override
	protected void setPropertyValue(PropertyBooleanCreatePersistenceIndex index, Boolean value) {
		index.setValueBoolean(value);
	}

	@Override
	protected void checkPropertyValue(Boolean value, ResultSet resultSet) throws Exception {
		Assertions.assertEquals(value, resultSet.getBoolean("valueBoolean"));
	}
}
