package com.ijioio.aes.sandbox.test.serialization.property;

import org.junit.jupiter.api.Assertions;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.serialization.property.BasePropertySerializationTest.Some;
import com.ijioio.test.model.PropertyClassSerialization;

public class PropertyClassSerializationTest
		extends BasePropertySerializationTest<PropertyClassSerialization, Class<Some>> {

	@Entity( //
			name = PropertyClassSerializationPrototype.NAME, //
			types = { //
					@Type(name = "Class<Some>", type = Type.CLASS, parameters = @Parameter(name = Some.NAME)) //
			}, //
			properties = { //
					@EntityProperty(name = "valueClass", type = "Class<Some>") //
			} //
	)
	public static interface PropertyClassSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassSerialization";
	}

	@Override
	protected String getXmlFileName() throws Exception {
		return "property-class-serialization.xml";
	}

	@Override
	protected Class<PropertyClassSerialization> getEntityClass() {
		return PropertyClassSerialization.class;
	}

	@Override
	protected PropertyClassSerialization createEntity() {

		PropertyClassSerialization entity = new PropertyClassSerialization();

		entity.setId("property-class-serialization");
		entity.setValueClass(Some.class);

		return entity;
	}

	@Override
	protected Class<Some> getPropertyValue(PropertyClassSerialization entity) {
		return entity.getValueClass();
	}

	@Override
	protected void checkPropertyValue(Class<Some> expectedValue, Class<Some> actualValue) {
		Assertions.assertEquals(expectedValue, actualValue);
	}
}
