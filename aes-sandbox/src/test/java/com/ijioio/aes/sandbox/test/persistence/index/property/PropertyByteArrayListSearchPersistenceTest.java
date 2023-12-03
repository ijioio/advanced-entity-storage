package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.ijioio.aes.core.Property;
import com.ijioio.test.model.PropertyByteArrayListSearchPersistence;
import com.ijioio.test.model.PropertyByteArrayListSearchPersistenceIndex;

public class PropertyByteArrayListSearchPersistenceTest extends
		BasePropertyCollectionSearchPersistenceTest<PropertyByteArrayListSearchPersistenceIndex, List<byte[]>, byte[]> {

	@Entity( //
			name = PropertyByteArrayListSearchPersistencePrototype.NAME, //
			types = { //
					@Type(name = "List<byte[]>", type = Type.LIST, parameters = @Parameter(name = Type.BYTE_ARRAY)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueByteArrayList", type = "List<byte[]>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyByteArrayListSearchPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueByteArrayList", type = "List<byte[]>") //
							} //
					) //
			} //
	)
	public static interface PropertyByteArrayListSearchPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteArrayListSearchPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyByteArrayListSearchPersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-byte-array-list-search-persistence.sql";
	}

	@Override
	protected Class<PropertyByteArrayListSearchPersistenceIndex> getIndexClass() {
		return PropertyByteArrayListSearchPersistenceIndex.class;
	}

	@Override
	protected List<PropertyByteArrayListSearchPersistenceIndex> createIndexes() {

		List<PropertyByteArrayListSearchPersistenceIndex> indexes = new ArrayList<>();

		int count = random.nextInt(INDEX_MAX_COUNT) + 1;

		for (int i = 0; i < count; i++) {

			PropertyByteArrayListSearchPersistenceIndex index = new PropertyByteArrayListSearchPersistenceIndex();

			index.setId(String.format("property-byte-array-list-search-persistence-index-%s", i + 1));
			index.setSource(EntityReference.of(String.format("property-byte-array-list-search-persistence-%s", i + 1),
					PropertyByteArrayListSearchPersistence.class));

			List<byte[]> value = new ArrayList<>();

			for (int j = 0; j < VALUE_MAX_COUNT; j++) {
				value.add(String.format("value%s%s", i + 1, j + 1).getBytes(StandardCharsets.UTF_8));
			}

			index.setValueByteArrayList(value);

			indexes.add(index);
		}

		return indexes;
	}

	@Override
	protected List<byte[]> createEmptyPropertyValue() {
		return Collections.emptyList();
	}

	@Override
	protected List<byte[]> createAllNullPropertyValue() {

		List<byte[]> value = new ArrayList<>();

		for (int j = 0; j < VALUE_MAX_COUNT; j++) {
			value.add(null);
		}

		return value;
	}

	@Override
	protected List<byte[]> createAllSamePropertyValue(int i) {

		List<byte[]> value = new ArrayList<>();

		for (int j = 0; j < VALUE_MAX_COUNT; j++) {
			value.add(String.format("value%s", i + 1).getBytes(StandardCharsets.UTF_8));
		}

		return value;
	}

	@Override
	protected Property<List<byte[]>> getProperty() {
		return PropertyByteArrayListSearchPersistenceIndex.Properties.valueByteArrayList;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected List<byte[]> getPropertyValue(PropertyByteArrayListSearchPersistenceIndex index) {
		return index.getValueByteArrayList();
	}

	@Override
	protected void setPropertyValue(PropertyByteArrayListSearchPersistenceIndex index, List<byte[]> value) {
		index.setValueByteArrayList(value);
	}

	@Override
	protected int comparePropertyValue(List<byte[]> value1, List<byte[]> value2) {
		return compare(
				Optional.ofNullable(value1)
						.map(item -> item.stream().map(element -> ByteArray.of(element)).collect(Collectors.toList()))
						.orElse(null),
				Optional.ofNullable(value2)
						.map(item -> item.stream().map(element -> ByteArray.of(element)).collect(Collectors.toList()))
						.orElse(null));
	}

	@Override
	protected int comparePropertyValueElement(byte[] element1, byte[] element2) {
		return Arrays.compare(element1, element2);
	}

	@Override
	protected void checkPropertyValue(List<byte[]> expectedValue, List<byte[]> actualValue) {
		Assertions.assertEquals(
				Optional.ofNullable(expectedValue)
						.map(item -> item.stream().map(element -> ByteArray.of(element)).collect(Collectors.toList()))
						.orElse(null),
				Optional.ofNullable(actualValue)
						.map(item -> item.stream().map(element -> ByteArray.of(element)).collect(Collectors.toList()))
						.orElse(null));
	}
}
