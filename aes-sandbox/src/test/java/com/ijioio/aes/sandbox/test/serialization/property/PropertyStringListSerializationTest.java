package com.ijioio.aes.sandbox.test.serialization.property;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyStringListSerialization;

public class PropertyStringListSerializationTest
		extends BasePropertyCollectionSerializationTest<PropertyStringListSerialization, List<String>, String> {

	@Entity( //
			name = PropertyStringListSerializationPrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = Type.LIST, parameters = @Parameter(name = Type.STRING)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = "List<String>") //
			} //
	)
	public static interface PropertyStringListSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringListSerialization";
	}

	@Override
	protected String getXmlFileName() {
		return "property-string-list-serialization.xml";
	}

	@Override
	protected String getNullXmlFileName() {
		return "property-string-list-null-serialization.xml";
	}

	@Override
	protected String getElementsEmptyXmlFileName() {
		return "property-string-list-elements-empty-serialization.xml";
	}

	@Override
	protected String getElementsNullXmlFileName() {
		return "property-string-list-elements-null-serialization.xml";
	}

	@Override
	protected Class<PropertyStringListSerialization> getEntityClass() {
		return PropertyStringListSerialization.class;
	}

	@Override
	protected PropertyStringListSerialization createEntity() {

		PropertyStringListSerialization entity = new PropertyStringListSerialization();

		entity.setId("property-string-list-serialization");

		List<String> value = new ArrayList<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.add(String.format("value%s", i + 1));
		}

		entity.setValueStringList(value);

		return entity;
	}

	@Override
	protected List<String> createEmptyPropertyValue() {
		return new ArrayList<>();
	}

	@Override
	protected List<String> createAllNullPropertyValue() {

		List<String> value = new ArrayList<>();

		for (int j = 0; j < VALUE_MAX_COUNT; j++) {
			value.add(null);
		}

		return value;
	}

	@Override
	protected boolean isNullPropertyValueAllowed() {
		return true;
	}

	@Override
	protected List<String> getPropertyValue(PropertyStringListSerialization entity) {
		return entity.getValueStringList();
	}

	@Override
	protected void setPropertyValue(PropertyStringListSerialization entity, List<String> value) {
		entity.setValueStringList(value);
	}

	@Override
	protected void checkPropertyValue(List<String> expectedValue, List<String> actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
