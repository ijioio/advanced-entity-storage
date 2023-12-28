package com.ijioio.aes.serialization.test.fixture.entity.property;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.test.model.PropertyStringListFinalSerialization;

public abstract class BasePropertyStringListFinalSerializationTest
		extends BasePropertyCollectionSerializationTest<PropertyStringListFinalSerialization, List<String>, String> {

	@Entity( //
			name = PropertyStringListFinalSerializationPrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = Type.LIST, parameters = @Parameter(name = Type.STRING)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueStringList", type = "List<String>", attributes = Attribute.FINAL) //
			} //
	)
	public static interface PropertyStringListFinalSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringListFinalSerialization";
	}

	@Override
	protected Class<PropertyStringListFinalSerialization> getEntityClass() {
		return PropertyStringListFinalSerialization.class;
	}

	@Override
	protected PropertyStringListFinalSerialization createEntity() {

		PropertyStringListFinalSerialization entity = new PropertyStringListFinalSerialization();

		entity.setId("property-string-list-final-serialization");

		List<String> value = new ArrayList<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.add(String.format("value%s", i + 1));
		}

		entity.getValueStringList().clear();
		entity.getValueStringList().addAll(value);

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
		return false;
	}

	@Override
	protected List<String> getPropertyValue(PropertyStringListFinalSerialization entity) {
		return entity.getValueStringList();
	}

	@Override
	protected void setPropertyValue(PropertyStringListFinalSerialization entity, List<String> value) {

		entity.getValueStringList().clear();
		entity.getValueStringList().addAll(value);
	}

	@Override
	protected void checkPropertyValue(List<String> expectedValue, List<String> actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
