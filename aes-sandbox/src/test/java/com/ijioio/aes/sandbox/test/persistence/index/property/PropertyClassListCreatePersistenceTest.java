package com.ijioio.aes.sandbox.test.persistence.index.property;

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
import com.ijioio.aes.sandbox.test.persistence.index.property.BasePropertyCreatePersistenceTest.Some;
import com.ijioio.test.model.PropertyClassListCreatePersistence;
import com.ijioio.test.model.PropertyClassListCreatePersistenceIndex;

public class PropertyClassListCreatePersistenceTest extends
		BasePropertyCollectionCreatePersistenceTest<PropertyClassListCreatePersistenceIndex, List<Class<? extends Some>>, Class<? extends Some>> {

	@Entity( //
			name = PropertyClassListCreatePersistencePrototype.NAME, //
			types = { //
					@Type(name = "Class<? extends Some>", type = Type.CLASS, parameters = @Parameter(name = Some.NAME, wildcard = true)), //
					@Type(name = "List<Class<? extends Some>>", type = Type.LIST, parameters = @Parameter(name = "Class<? extends Some>")) //
			}, //
			properties = { //
					@EntityProperty(name = "valueClassList", type = "List<Class<? extends Some>>") //
			}, //
			indexes = { //
					@EntityIndex( //
							name = PropertyClassListCreatePersistencePrototype.INDEX_NAME, //
							properties = { //
									@EntityIndexProperty(name = "valueClassList", type = "List<Class<? extends Some>>") //
							} //
					) //
			} //
	)
	public static interface PropertyClassListCreatePersistencePrototype {

		public static final String NAME = "com.ijioio.test.model.PropertyClassListCreatePersistence";

		public static final String INDEX_NAME = "com.ijioio.test.model.PropertyClassListCreatePersistenceIndex";
	}

	@Override
	protected String getSqlScriptFileName() throws Exception {
		return "property-class-list-create-persistence.sql";
	}

	@Override
	protected boolean isFinal() {
		return false;
	}

	@Override
	protected String getTableName() {
		return PropertyClassListCreatePersistenceIndex.class.getSimpleName();
	}

	@Override
	protected PropertyClassListCreatePersistenceIndex createIndex() {

		PropertyClassListCreatePersistenceIndex index = new PropertyClassListCreatePersistenceIndex();

		index.setId("property-class-list-create-persistence-index");
		index.setSource(
				EntityReference.of("property-class-list-create-persistence", PropertyClassListCreatePersistence.class));

		List<Class<? extends Some>> value = new ArrayList<>();

		for (int i = 0; i < VALUE_MAX_COUNT; i++) {
			value.add(types.get(i));
		}

		index.setValueClassList(value);

		return index;
	}

	@Override
	protected List<Class<? extends Some>> createEmptyPropertyValue() {
		return Collections.emptyList();
	}

	@Override
	protected List<Class<? extends Some>> getPropertyValue(PropertyClassListCreatePersistenceIndex index) {
		return index.getValueClassList();
	}

	@Override
	protected void setPropertyValue(PropertyClassListCreatePersistenceIndex index, List<Class<? extends Some>> value) {
		index.setValueClassList(value);
	}

	@Override
	protected void checkPropertyValue(List<Class<? extends Some>> value, ResultSet resultSet) throws Exception {
		Assertions
				.assertEquals(
						Optional.ofNullable(value)
								.map(item -> item.stream().map(element -> getClassName(element))
										.collect(Collectors.toList()))
								.orElse(null),
						getArray(resultSet.getArray("valueClassList")));
	}

	private String getClassName(Class<? extends Some> value) {
		return Optional.ofNullable(value).map(item -> item.getName()).orElse(null);
	}
}
