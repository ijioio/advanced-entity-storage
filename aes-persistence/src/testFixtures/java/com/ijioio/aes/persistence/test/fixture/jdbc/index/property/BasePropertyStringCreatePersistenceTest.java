package com.ijioio.aes.persistence.test.fixture.jdbc.index.property;

import java.sql.ResultSet;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.test.model.PropertyStringCreatePersistence;
import com.ijioio.test.model.PropertyStringCreatePersistenceIndex;

public abstract class BasePropertyStringCreatePersistenceTest
		extends BasePropertyCreatePersistenceTest<PropertyStringCreatePersistenceIndex, String> {

	@Entity( //
			name = PropertyStringCreatePersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueString", type = Type.STRING) //
							} //
					) //
			} //
	)
	public static interface PropertyStringCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringCreatePersistenceIndex";
	}

	@Override
	protected Class<PropertyStringCreatePersistenceIndex> getIndexClass() {
		return PropertyStringCreatePersistenceIndex.class;
	}

	@Override
	protected PropertyStringCreatePersistenceIndex createIndex() {

		PropertyStringCreatePersistenceIndex index = new PropertyStringCreatePersistenceIndex();

		index.setId("property-string-create-persistence-index");
		index.setSource(
				EntityReference.of("property-string-create-persistence", PropertyStringCreatePersistence.class));
		index.setValueString("value");

		return index;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected String getPropertyValue(PropertyStringCreatePersistenceIndex index) {
		return index.getValueString();
	}

	@Override
	protected void setPropertyValue(PropertyStringCreatePersistenceIndex index, String value) {
		index.setValueString(value);
	}

	@Override
	protected void checkPropertyValue(String value, ResultSet resultSet) throws Exception {
		Assertions.assertEquals(value, resultSet.getString("valueString"));
	}
}
