package com.ijioio.aes.persistence.test.fixture.jdbc.index.property;

import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.persistence.test.fixture.jdbc.PersistenceTestUtil;
import com.ijioio.test.model.PropertyByteArrayCreatePersistence;
import com.ijioio.test.model.PropertyByteArrayCreatePersistenceIndex;

public abstract class BasePropertyByteArrayCreatePersistenceTest
		extends BasePropertyCreatePersistenceTest<PropertyByteArrayCreatePersistenceIndex, byte[]> {

	@Entity( //
			name = PropertyByteArrayCreatePersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByteArray", type = Type.BYTE_ARRAY) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyByteArrayCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueByteArray", type = Type.BYTE_ARRAY) //
							} //
					) //
			} //
	)
	public static interface PropertyByteArrayCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteArrayCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyByteArrayCreatePersistenceIndex";
	}

	@Override
	protected Class<PropertyByteArrayCreatePersistenceIndex> getIndexClass() {
		return PropertyByteArrayCreatePersistenceIndex.class;
	}

	@Override
	protected PropertyByteArrayCreatePersistenceIndex createIndex() {

		PropertyByteArrayCreatePersistenceIndex index = new PropertyByteArrayCreatePersistenceIndex();

		index.setId("property-byte-array-create-persistence-index");
		index.setSource(
				EntityReference.of("property-byte-array-create-persistence", PropertyByteArrayCreatePersistence.class));
		index.setValueByteArray("value".getBytes(StandardCharsets.UTF_8));

		return index;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected byte[] getPropertyValue(PropertyByteArrayCreatePersistenceIndex index) {
		return index.getValueByteArray();
	}

	@Override
	protected void setPropertyValue(PropertyByteArrayCreatePersistenceIndex index, byte[] value) {
		index.setValueByteArray(value);
	}

	@Override
	protected void checkPropertyValue(byte[] value, ResultSet resultSet) throws Exception {
		Assertions.assertArrayEquals(value, PersistenceTestUtil.getBytes(resultSet, "valueByteArray"));
	}
}
