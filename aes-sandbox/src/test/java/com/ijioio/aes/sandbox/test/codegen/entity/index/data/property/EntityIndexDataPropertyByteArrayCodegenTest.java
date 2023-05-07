package com.ijioio.aes.sandbox.test.codegen.entity.index.data.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.EntityIndexDataPropertyByteArrayCodegenIndexData;

public class EntityIndexDataPropertyByteArrayCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = EntityIndexDataPropertyByteArrayCodegenPrototype.NAME, //
			indexes = @EntityIndex( //
					name = EntityIndexDataPropertyByteArrayCodegenPrototype.INDEX_NAME, //
					properties = { //
							@EntityIndexProperty(name = "valueByteArray", type = @Type(name = Type.BYTE_ARRAY)) //
					} //
			) //
	)
	public static interface EntityIndexDataPropertyByteArrayCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.EntityIndexDataPropertyByteArrayCodegen";

		public static final String INDEX_NAME = "com.ijioio.test.model.EntityIndexDataPropertyByteArrayCodegenIndex";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<EntityIndexDataPropertyByteArrayCodegenIndexData> type = EntityIndexDataPropertyByteArrayCodegenIndexData.class;

		checkFieldExists(type, "valueByteArray", Modifier.PRIVATE, byte[].class);

		checkMethodExists(type, "setValueByteArray", Arrays.asList(byte[].class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueByteArray", Collections.emptyList(), Modifier.PUBLIC, byte[].class);
	}
}
