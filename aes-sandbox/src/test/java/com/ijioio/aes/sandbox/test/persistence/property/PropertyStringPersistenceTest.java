package com.ijioio.aes.sandbox.test.persistence.property;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.sandbox.test.persistence.BasePersistenceTest;
import com.ijioio.test.model.PropertyStringSerialization;

public class PropertyStringPersistenceTest extends BasePersistenceTest {

	@Entity( //
			name = PropertyStringPersistencePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = @Type(name = Type.STRING)) //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyStringPersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueString", type = @Type(name = Type.STRING)) //
							} //
					) //
			} //
	)
	public static interface PropertyStringPersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyStringPersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyStringPersistenceIndex";
	}

	private Path path;

	private PropertyStringSerialization model;

	@BeforeEach
	public void before() throws Exception {

		path = Paths.get(getClass().getClassLoader().getResource("property-string-serialization.xml").toURI());

		model = new PropertyStringSerialization();

		model.setId("property-string-serialization");
		model.setValueString("value");
	}

	@Test
	public void testInsert() throws Exception {

		JdbcPersistenceHandler handler = new JdbcPersistenceHandler();

//		String actual = XmlUtil.write(handler, model);
//		String expected = readString(path);
//
//		Assertions.assertEquals(expected, actual);
	}
}
