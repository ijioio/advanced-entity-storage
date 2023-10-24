package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.sql.ResultSet;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.test.model.PropertyCharCreatePersistence;
import com.ijioio.test.model.PropertyCharCreatePersistenceIndex;

public class PropertyCharCreatePersistenceTest
		extends BasePropertyCreatePersistenceTest<PropertyCharCreatePersistenceIndex, Character> {

	@Entity( //
			name = PropertyCharCreatePersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueChar", type = Type.CHAR) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyCharCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueChar", type = Type.CHAR) //
							} //
					) //
			} //
	)
	public static interface PropertyCharCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyCharCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyCharCreatePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() {
		return "property-char-create-persistence.sql";
	}

	@Override
	protected String getTableName() {
		return PropertyCharCreatePersistenceIndex.class.getSimpleName();
	}

	@Override
	protected PropertyCharCreatePersistenceIndex createIndex() {

		PropertyCharCreatePersistenceIndex index = new PropertyCharCreatePersistenceIndex();

		index.setId("property-char-create-persistence-index");
		index.setSource(EntityReference.of("property-char-create-persistence", PropertyCharCreatePersistence.class));
		index.setValueChar('a');

		return index;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected Character getPropertyValue(PropertyCharCreatePersistenceIndex index) {
		return index.getValueChar();
	}

	@Override
	protected void setPropertyValue(PropertyCharCreatePersistenceIndex index, Character value) {
		index.setValueChar(value);
	}

	@Override
	protected void checkPropertyValue(Character value, ResultSet resultSet) throws Exception {
		Assertions.assertEquals(value != null ? String.valueOf(value) : null, resultSet.getString("valueChar"));
	}
}
