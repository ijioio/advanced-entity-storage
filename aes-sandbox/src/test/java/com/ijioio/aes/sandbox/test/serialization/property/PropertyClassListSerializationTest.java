package com.ijioio.aes.sandbox.test.serialization.property;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.serialization.property.BasePropertySerializationTest.Some;
import com.ijioio.test.model.PropertyClassListSerialization;

public class PropertyClassListSerializationTest
		extends BasePropertySerializationTest<PropertyClassListSerialization, List<Class<? extends Some>>> {

	@Entity( //
			name = PropertyClassListSerializationPrototype.NAME, //
			types = { //
					@Type(name = "Class<? extends Some>", type = Type.CLASS, parameters = @Parameter(name = Some.NAME, wildcard = true)), //
					@Type(name = "List<Class<? extends Some>>", type = Type.LIST, parameters = @Parameter(name = "Class<? extends Some>")) //
			}, //
			properties = { //
					@EntityProperty(name = "valueClassList", type = "List<Class<? extends Some>>") //
			} //
	)
	public static interface PropertyClassListSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassListSerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-class-list-serialization.xml";
	}

	@Override
	protected Class<PropertyClassListSerialization> getEntityClass() {
		return PropertyClassListSerialization.class;
	}

	@Override
	protected PropertyClassListSerialization createEntity() {

		PropertyClassListSerialization entity = new PropertyClassListSerialization();

		entity.setId("property-class-list-serialization");

		List<Class<? extends Some>> value = new ArrayList<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.add(types.get(i));
		}

		entity.setValueClassList(value);

		return entity;
	}

	@Override
	protected List<Class<? extends Some>> getPropertyValue(PropertyClassListSerialization entity) {
		return entity.getValueClassList();
	}

	@Override
	protected void checkPropertyValue(List<Class<? extends Some>> expectedValue,
			List<Class<? extends Some>> actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
