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

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.test.model.PropertyByteArrayListFinalCreatePersistence;
import com.ijioio.test.model.PropertyByteArrayListFinalCreatePersistenceIndex;

public class PropertyByteArrayListFinalCreatePersistenceTest extends
		BasePropertyCollectionCreatePersistenceTest<PropertyByteArrayListFinalCreatePersistenceIndex, List<byte[]>, byte[]> {

	@Entity( //
			name = PropertyByteArrayListFinalCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "List<byte[]>", type = Type.LIST, parameters = @Parameter(name = Type.BYTE_ARRAY)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueByteArrayList", type = "List<byte[]>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyByteArrayListFinalCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueByteArrayList", type = "List<byte[]>", attributes = Attribute.FINAL) //
							} //
					) //
			} //
	)
	public static interface PropertyByteArrayListFinalCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyByteArrayListFinalCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyByteArrayListFinalCreatePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-byte-array-list-final-create-persistence.sql";
	}

	@Override
	protected String getTableName() {
		return PropertyByteArrayListFinalCreatePersistenceIndex.class.getSimpleName();
	}

	@Override
	protected PropertyByteArrayListFinalCreatePersistenceIndex createIndex() {

		PropertyByteArrayListFinalCreatePersistenceIndex index = new PropertyByteArrayListFinalCreatePersistenceIndex();

		index.setId("property-byte-array-list-final-create-persistence-index");
		index.setSource(EntityReference.of("property-byte-array-list-final-create-persistence",
				PropertyByteArrayListFinalCreatePersistence.class));

		List<byte[]> value = new ArrayList<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.add(String.format("value-%s", i + 1).getBytes(StandardCharsets.UTF_8));
		}

		index.getValueByteArrayList().clear();
		index.getValueByteArrayList().addAll(value);

		return index;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return false;
	}

	@Override
	protected List<byte[]> createEmptyPropertyValue() {
		return Collections.emptyList();
	}

	@Override
	protected List<byte[]> getPropertyValue(PropertyByteArrayListFinalCreatePersistenceIndex index) {
		return index.getValueByteArrayList();
	}

	@Override
	protected void setPropertyValue(PropertyByteArrayListFinalCreatePersistenceIndex index, List<byte[]> value) {

		index.getValueByteArrayList().clear();
		index.getValueByteArrayList().addAll(value);
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
