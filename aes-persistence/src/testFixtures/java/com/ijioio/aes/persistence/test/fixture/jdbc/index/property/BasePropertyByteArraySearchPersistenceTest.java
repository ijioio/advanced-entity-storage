package com.ijioio.aes.persistence.test.fixture.jdbc.index.property;

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
import com.ijioio.test.model.PropertyByteArraySearchPersistence;
import com.ijioio.test.model.PropertyByteArraySearchPersistenceIndex;

public abstract class BasePropertyByteArraySearchPersistenceTest
		extends BasePropertySearchPersistenceTest<PropertyByteArraySearchPersistenceIndex, byte[]> {

	@Entity( //
			name = PropertyByteArraySearchPersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueByteArray", type = Type.BYTE_ARRAY), //
					@EntityProperty(name = "otherValueByteArray", type = Type.BYTE_ARRAY) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyByteArraySearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueByteArray", type = Type.BYTE_ARRAY), //
									@EntityIndexProperty(name = "otherValueByteArray", type = Type.BYTE_ARRAY) //
							} //
					) //
			} //
	)
	public static interface PropertyByteArraySearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteArraySearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyByteArraySearchPersistenceIndex";
	}

	@Override
	protected Class<PropertyByteArraySearchPersistenceIndex> getIndexClass() {
		return PropertyByteArraySearchPersistenceIndex.class;
	}

	@Override
	protected List<PropertyByteArraySearchPersistenceIndex> createIndexes() {

		List<PropertyByteArraySearchPersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyByteArraySearchPersistenceIndex index = new PropertyByteArraySearchPersistenceIndex();

			index.setId(String.format("property-byte-array-search-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-byte-array-search-persistence-%s", i + 1),
					PropertyByteArraySearchPersistence.class));
			index.setValueByteArray(String.format("value%s", i + 1).getBytes(StandardCharsets.UTF_8));

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected Property<byte[]> getProperty() {
		return PropertyByteArraySearchPersistenceIndex.Properties.valueByteArray;
	}

	@Override
	protected Property<byte[]> getOtherProperty() {
		return PropertyByteArraySearchPersistenceIndex.Properties.otherValueByteArray;
	}

	@Override
	protected byte[] getPropertyValue(PropertyByteArraySearchPersistenceIndex index) {
		return index.getValueByteArray();
	}

	@Override
	protected byte[] getOtherPropertyValue(PropertyByteArraySearchPersistenceIndex index) {
		return index.getOtherValueByteArray();
	}

	@Override
	protected void setPropertyValue(PropertyByteArraySearchPersistenceIndex index, byte[] value) {
		index.setValueByteArray(value);
	}

	@Override
	protected void setOtherPropertyValue(PropertyByteArraySearchPersistenceIndex index, byte[] value) {
		index.setOtherValueByteArray(value);
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
