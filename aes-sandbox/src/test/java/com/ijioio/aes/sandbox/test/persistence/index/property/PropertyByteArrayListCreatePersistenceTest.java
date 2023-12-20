package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.ResultSet;
import java.util.ArrayList;
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
import com.ijioio.test.model.PropertyByteArrayListCreatePersistence;
import com.ijioio.test.model.PropertyByteArrayListCreatePersistenceIndex;

public class PropertyByteArrayListCreatePersistenceTest extends
		BasePropertyCollectionCreatePersistenceTest<PropertyByteArrayListCreatePersistenceIndex, List<byte[]>, byte[]> {

	@Entity( //
			name = PropertyByteArrayListCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "List<byte[]>", type = Type.LIST, parameters = @Parameter(name = Type.BYTE_ARRAY)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueByteArrayList", type = "List<byte[]>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyByteArrayListCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueByteArrayList", type = "List<byte[]>") //
							} //
					) //
			} //
	)
	public static interface PropertyByteArrayListCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteArrayListCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyByteArrayListCreatePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-byte-array-list-create-persistence.sql";
	}

	@Override
	protected String getTableName() {
		return PropertyByteArrayListCreatePersistenceIndex.class.getSimpleName();
	}

	@Override
	protected PropertyByteArrayListCreatePersistenceIndex createIndex() {

		PropertyByteArrayListCreatePersistenceIndex index = new PropertyByteArrayListCreatePersistenceIndex();

		index.setId("property-byte-array-list-create-persistence-index");
		index.setSource(EntityReference.of("property-byte-array-list-create-persistence",
				PropertyByteArrayListCreatePersistence.class));

		List<byte[]> value = new ArrayList<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.add(String.format("value-%s", i + 1).getBytes(StandardCharsets.UTF_8));
		}

		index.setValueByteArrayList(value);

		return index;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected List<byte[]> createEmptyPropertyValue() {
		return Collections.emptyList();
	}

	@Override
	protected List<byte[]> getPropertyValue(PropertyByteArrayListCreatePersistenceIndex index) {
		return index.getValueByteArrayList();
	}

	@Override
	protected void setPropertyValue(PropertyByteArrayListCreatePersistenceIndex index, List<byte[]> value) {
		index.setValueByteArrayList(value);
	}

	@Override
	protected void checkPropertyValue(List<byte[]> value, ResultSet resultSet) throws Exception {
		Assertions
				.assertEquals(
						Optional.ofNullable(value)
								.map(item -> item.stream().map(element -> ByteArray.of(element))
										.collect(Collectors.toList()))
								.orElse(null),
						Optional.ofNullable(getArray(resultSet.getArray("valueByteArrayList")))
								.map(item -> item.stream().map(element -> ByteArray.of(getBytes((Blob) element)))
										.collect(Collectors.toList()))
								.orElse(null));
	}
}
