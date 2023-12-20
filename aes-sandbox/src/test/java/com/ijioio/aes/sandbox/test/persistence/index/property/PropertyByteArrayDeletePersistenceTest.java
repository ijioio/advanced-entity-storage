package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.Property;
import com.ijioio.test.model.PropertyByteArrayDeletePersistence;
import com.ijioio.test.model.PropertyByteArrayDeletePersistenceIndex;

public class PropertyByteArrayDeletePersistenceTest
		extends BasePropertyDeletePersistenceTest<PropertyByteArrayDeletePersistenceIndex, byte[]> {

	@Entity( //
			name = PropertyByteArrayDeletePersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByteArray", type = Type.BYTE_ARRAY) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyByteArrayDeletePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueByteArray", type = Type.BYTE_ARRAY) //
							} //
					) //
			} //
	)
	public static interface PropertyByteArrayDeletePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteArrayDeletePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyByteArrayDeletePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-byte-array-delete-persistence.sql";
	}

	@Override
	protected Class<PropertyByteArrayDeletePersistenceIndex> getIndexClass() {
		return PropertyByteArrayDeletePersistenceIndex.class;
	}

	@Override
	protected List<PropertyByteArrayDeletePersistenceIndex> createIndexes() {

		List<PropertyByteArrayDeletePersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyByteArrayDeletePersistenceIndex index = new PropertyByteArrayDeletePersistenceIndex();

			index.setId(String.format("property-byte-array-delete-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-byte-array-delete-persistence-%s", i + 1),
					PropertyByteArrayDeletePersistence.class));
			index.setValueByteArray(String.format("value-%s", i + 1).getBytes(StandardCharsets.UTF_8));

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected Property<byte[]> getProperty() {
		return PropertyByteArrayDeletePersistenceIndex.Properties.valueByteArray;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected byte[] getPropertyValue(PropertyByteArrayDeletePersistenceIndex index) {
		return index.getValueByteArray();
	}

	@Override
	protected void setPropertyValue(PropertyByteArrayDeletePersistenceIndex index, byte[] value) {
		index.setValueByteArray(value);
	}

	@Override
	protected int comparePropertyValue(byte[] value1, byte[] value2) {
		return Arrays.compare(value1, value2);
	}

	@Override
	protected void checkPropertyValue(byte[] expectedValue, byte[] actualValue) {
		Assertions.assertArrayEquals(expectedValue, actualValue);
	}
}
