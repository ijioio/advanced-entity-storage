package com.ijioio.aes.sandbox.test.serialization.property;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyEnumListSerialization;

public class PropertyEnumListSerializationTest
		extends BasePropertyCollectionSerializationTest<PropertyEnumListSerialization, List<Month>, Month> {

	@Entity( //
			name = PropertyEnumListSerializationPrototype.NAME, //
			types = { //
					@Type(name = "List<Month>", type = Type.LIST, parameters = @Parameter(name = "java.time.Month")) //
			}, //
			properties = { //
					@EntityProperty(name = "valueEnumList", type = "List<Month>") //
			} //
	)
	public static interface PropertyEnumListSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyEnumListSerialization";
	}

	@Override
	protected String getXmlFileName(PropertyType type) {

		if (type == PropertyType.STANDARD) {
			return "property-enum-list-serialization.xml";
		} else if (type == PropertyType.NULL) {
			return "property-enum-list-null-serialization.xml";
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected String getElementsEmptyXmlFileName() {
		return "property-enum-list-elements-empty-serialization.xml";
	}

	@Override
	protected Class<PropertyEnumListSerialization> getEntityClass() {
		return PropertyEnumListSerialization.class;
	}

	@Override
	protected PropertyEnumListSerialization createEntity() {

		PropertyEnumListSerialization entity = new PropertyEnumListSerialization();

		entity.setId("property-enum-list-serialization");

		List<Month> value = new ArrayList<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.add(Month.values()[i]);
		}

		entity.setValueEnumList(value);

		return entity;
	}

	@Override
	protected List<Month> createEmptyPropertyValue() {
		return new ArrayList<>();
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected List<Month> getPropertyValue(PropertyEnumListSerialization entity) {
		return entity.getValueEnumList();
	}

	@Override
	protected void setPropertyValue(PropertyEnumListSerialization entity, List<Month> value) {
		entity.setValueEnumList(value);
	}

	@Override
	protected void checkPropertyValue(List<Month> expectedValue, List<Month> actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
