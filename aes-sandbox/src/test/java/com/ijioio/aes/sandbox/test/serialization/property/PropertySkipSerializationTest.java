package com.ijioio.aes.sandbox.test.serialization.property;

import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Parameter;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;
import com.ijioio.aes.serialization.xml.XmlSerializationHandler;
import com.ijioio.test.model.PropertySkipSerialization;

public class PropertySkipSerializationTest extends BaseSerializationTest {

	@Entity( //
			name = PropertySkipSerializationPrototype.NAME, //
			types = { //
					@Type(name = "List<String>", type = Type.LIST, parameters = @Parameter(name = Type.STRING)), //
					@Type(name = "Set<String>", type = Type.SET, parameters = @Parameter(name = Type.STRING)), //
					@Type(name = "Map<String, String>", type = Type.MAP, parameters = { @Parameter(name = Type.STRING),
							@Parameter(name = Type.STRING) }) //
			}, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING), //
					@EntityProperty(name = "valueStringList", type = "List<String>"), //
					@EntityProperty(name = "valueStringSet", type = "Set<String>"), //
					@EntityProperty(name = "valueStringMap", type = "Map<String, String>") //
			} //
	)
	public static interface PropertySkipSerializationPrototype {

		public static final String NAME = "com.ijioio.test.model.PropertySkipSerialization";
	}

	protected final int VALUE_MAX_COUNT = 3;

	protected XmlSerializationHandler handler;

	private PropertySkipSerialization entity;

	@BeforeEach
	public void before() throws Exception {

		handler = new XmlSerializationHandler();

		entity = createEntity();
	}

	@Test
	public void testRead() throws Exception {

		Path path = Paths.get(getClass().getClassLoader()
				.getResource(String.format("serialization/entity/property/%s", getXmlFileName())).toURI());

		ByteArrayInputStream is = new ByteArrayInputStream(Files.readAllBytes(path));

		PropertySkipSerialization actualEntity = handler.read(is);
		PropertySkipSerialization expectedEntity = entity;

		check(expectedEntity, actualEntity);
	}

	private String getXmlFileName() {
		return "property-skip-serialization.xml";
	}

	private Class<PropertySkipSerialization> getEntityClass() {
		return PropertySkipSerialization.class;
	}

	private PropertySkipSerialization createEntity() {

		PropertySkipSerialization entity = new PropertySkipSerialization();

		entity.setId("property-skip-serialization");
		entity.setValueString("value");

		List<String> stringList = new ArrayList<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			stringList.add(String.format("value%s", i + 1));
		}

		entity.setValueStringList(stringList);

		Set<String> stringSet = new LinkedHashSet<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			stringSet.add(String.format("value%s", i + 1));
		}

		entity.setValueStringSet(stringSet);

		Map<String, String> stringMap = new LinkedHashMap<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			stringMap.put(String.format("key%s", i + 1), String.format("value%s", i + 1));
		}

		entity.setValueStringMap(stringMap);

		return entity;
	}

	private void check(PropertySkipSerialization expectedEntity, PropertySkipSerialization actualEntity) {

		Assertions.assertEquals(expectedEntity.getId(), actualEntity.getId());
		Assertions.assertEquals(expectedEntity.getValueString(), actualEntity.getValueString());
		Assertions.assertEquals(expectedEntity.getValueStringList(), actualEntity.getValueStringList());
		Assertions.assertEquals(expectedEntity.getValueStringSet(), actualEntity.getValueStringSet());
		Assertions.assertEquals(expectedEntity.getValueStringMap(), actualEntity.getValueStringMap());
	}
}
