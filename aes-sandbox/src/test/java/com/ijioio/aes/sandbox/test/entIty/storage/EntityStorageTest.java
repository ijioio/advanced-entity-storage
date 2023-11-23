package com.ijioio.aes.sandbox.test.entIty.storage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.core.EntityContainer;
import com.ijioio.aes.core.entity.storage.EntityStorage;
import com.ijioio.aes.core.entity.storage.standard.StandardEntityStorage2;
import com.ijioio.aes.core.persistence.PersistenceHandler;
import com.ijioio.aes.core.persistence.jdbc.JdbcPersistenceHandler;
import com.ijioio.aes.core.serialization.SerializationHandler;
import com.ijioio.aes.core.serialization.xml.XmlSerializationHandler;
import com.ijioio.test.model.SaveEntityStorage;

public class EntityStorageTest extends BaseEntityStorageTest {

	@Entity( //
			name = SaveEntityStoragePrototype.NAME, //
			properties = { //
					@EntityProperty(name = "valueString", type = Type.STRING) //
			})
	public static interface SaveEntityStoragePrototype {

		public static final String NAME = "com.ijioio.test.model.SaveEntityStorage";
	}

	protected EntityStorage entityStorage;

	@BeforeEach
	public void before() throws Exception {

		PersistenceHandler<?> persistenceHandler = new JdbcPersistenceHandler();
		SerializationHandler serializationHandler = new XmlSerializationHandler();

		entityStorage = new StandardEntityStorage2(dataSource, persistenceHandler, serializationHandler);
	}

	@Test
	public void testSearch() throws Exception {

		SaveEntityStorage entity = new SaveEntityStorage();

		entity.setId("save-entity-storage");
		entity.setValueString("value");

		EntityContainer<SaveEntityStorage> entityContainer = EntityContainer.of(entity);

		entityStorage.save(entityContainer);
	}
}
