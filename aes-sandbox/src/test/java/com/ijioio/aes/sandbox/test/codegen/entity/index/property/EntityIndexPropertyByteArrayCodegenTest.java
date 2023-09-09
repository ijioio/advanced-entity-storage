package com.ijioio.aes.sandbox.test.codegen.entity.index.property;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.sandbox.test.codegen.BaseCodegenTest;
import com.ijioio.test.model.EntityIndexPropertyByteArrayCodegenIndex;

public class EntityIndexPropertyByteArrayCodegenTest extends BaseCodegenTest {

	@Entity( //
			name = EntityIndexPropertyByteArrayCodegenPrototype.NAME, //
			indexes = @EntityIndex( //
					name = EntityIndexPropertyByteArrayCodegenPrototype.INDEX_NAME, //
					properties = { //
							@EntityIndexProperty(name = "valueByteArray", type = Type.BYTE_ARRAY) //
					} //
			) //
	)
	public static interface EntityIndexPropertyByteArrayCodegenPrototype {

		public static final String NAME = "com.ijioio.test.model.EntityIndexPropertyByteArrayCodegen";

		public static final String INDEX_NAME = "com.ijioio.test.model.EntityIndexPropertyByteArrayCodegenIndex";
	}

	@Test
	public void testCodegen() throws Exception {

		Class<EntityIndexPropertyByteArrayCodegenIndex> type = EntityIndexPropertyByteArrayCodegenIndex.class;

		checkFieldExists(type, "valueByteArray", Modifier.PRIVATE, byte[].class);

		checkMethodExists(type, "setValueByteArray", Arrays.asList(byte[].class), Modifier.PUBLIC, void.class);
		checkMethodExists(type, "getValueByteArray", Collections.emptyList(), Modifier.PUBLIC, byte[].class);
	}
}
