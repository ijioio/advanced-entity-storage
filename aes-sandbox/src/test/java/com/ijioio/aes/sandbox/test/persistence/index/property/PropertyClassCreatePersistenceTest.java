package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.sql.ResultSet;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertyCreatePersistenceTest.Some;
import com.ijioio.test.model.PropertyClassCreatePersistence;
import com.ijioio.test.model.PropertyClassCreatePersistenceIndex;

public class PropertyClassCreatePersistenceTest
		extends BasePropertyCreatePersistenceTest<PropertyClassCreatePersistenceIndex, Class<Some>> {

	@Entity( //
			name = PropertyClassCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "Class<Some>", type = Type.CLASS, parameters = @Parameter(name = Some.NAME)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueClass", type = "Class<Some>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyClassCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueClass", type = "Class<Some>") //
							} //
					) //
			} //
	)
	public static interface PropertyClassCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyClassCreatePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-class-create-persistence.sql";
	}

	@Override
	protected String getTableName() {
		return PropertyClassCreatePersistenceIndex.class.getSimpleName();
	}

	@Override
	protected PropertyClassCreatePersistenceIndex createIndex() {

		PropertyClassCreatePersistenceIndex index = new PropertyClassCreatePersistenceIndex();

		index.setId("property-class-create-persistence-index");
		index.setSource(EntityReference.of("property-class-create-persistence", PropertyClassCreatePersistence.class));
		index.setValueClass(Some.class);

		return index;
	}

	@Override
	protected Class<Some> getPropertyValue(PropertyClassCreatePersistenceIndex index) {
		return index.getValueClass();
	}

	@Override
	protected void setPropertyValue(PropertyClassCreatePersistenceIndex index, Class<Some> value) {
		index.setValueClass(value);
	}

	@Override
	protected void checkPropertyValue(PropertyClassCreatePersistenceIndex index, ResultSet resultSet) throws Exception {
		Assertions.assertEquals(Optional.ofNullable(index.getValueClass()).map(item -> item.getName()).orElse(null),
				resultSet.getString("valueClass"));
	}
}
