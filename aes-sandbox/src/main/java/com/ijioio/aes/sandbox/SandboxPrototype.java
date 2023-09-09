package com.ijioio.aes.sandbox;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityIndex;
import com.ijioio.aes.annotation.EntityIndexProperty;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;

@Entity( //
		name = SandboxPrototype.NAME, //
		parent = SandboxParentPrototype.NAME, //
		attributes = Attribute.FINAL, //
		types = { //
				@Type(name = "Class<String>", type = Type.CLASS, parameters = Type.STRING), //
				@Type(name = "List<String>", type = Type.LIST, parameters = Type.STRING), //
				@Type(name = "Set<Month>", type = Type.SET, parameters = "java.time.Month"), //
				@Type(name = "Map<Object, Object>", type = Type.MAP, parameters = { "java.lang.Object",
						"java.lang.Object" }), //
				@Type(name = "EntityReference<SandboxParent>", type = Type.ENTITY_REFERENCE, parameters = SandboxParentPrototype.NAME), //
				@Type(name = "List<SandboxChild>", type = Type.LIST, parameters = SandboxChildPrototype.NAME), //
		}, //
		properties = { //
				@EntityProperty(name = "valueBoolean", type = Type.BOOLEAN), //
				@EntityProperty(name = "valueChar", type = Type.CHAR), //
				@EntityProperty(name = "valueByte", type = Type.BYTE), //
				@EntityProperty(name = "valueShort", type = Type.SHORT), //
				@EntityProperty(name = "valueInt", type = Type.INT), //
				@EntityProperty(name = "valueLong", type = Type.LONG), //
				@EntityProperty(name = "valueFloat", type = Type.FLOAT), //
				@EntityProperty(name = "valueDouble", type = Type.DOUBLE), //
				@EntityProperty(name = "valueByteArray", type = Type.BYTE_ARRAY), //
				@EntityProperty(name = "valueString", type = Type.STRING), //
				@EntityProperty(name = "valueInstant", type = Type.INSTANT), //
				@EntityProperty(name = "valueLocalDate", type = Type.LOCAL_DATE), //
				@EntityProperty(name = "valueLocalTime", type = Type.LOCAL_TIME), //
				@EntityProperty(name = "valueLocalDateTime", type = Type.LOCAL_DATE_TIME), //
				@EntityProperty(name = "valueEnum", type = "java.time.Month"), //
				@EntityProperty(name = "valueClass", type = "Class<String>"), //
				@EntityProperty(name = "valueStringList", type = "List<String>", attributes = Attribute.FINAL), //
				@EntityProperty(name = "valueEnumSet", type = "Set<Month>", attributes = Attribute.FINAL), //
				@EntityProperty(name = "valueObjectMap", type = "Map<Object, Object>", attributes = Attribute.FINAL), //
				@EntityProperty(name = "valueReference", type = "EntityReference<SandboxParent>", attributes = Attribute.FINAL), //
				@EntityProperty(name = "valueXSerializable", type = SandboxChildPrototype.NAME), //
				@EntityProperty(name = "valueXSerializableList", type = "List<SandboxChild>", attributes = Attribute.FINAL) //
		}, //
		indexes = { //
				@EntityIndex( //
						name = SandboxPrototype.INDEX_NAME, //
						properties = { //
								@EntityIndexProperty(name = "valueBoolean", type = Type.BOOLEAN), //
								@EntityIndexProperty(name = "valueChar", type = Type.CHAR), //
								@EntityIndexProperty(name = "valueByte", type = Type.BYTE), //
								@EntityIndexProperty(name = "valueShort", type = Type.SHORT), //
								@EntityIndexProperty(name = "valueInt", type = Type.INT), //
								@EntityIndexProperty(name = "valueLong", type = Type.LONG), //
								@EntityIndexProperty(name = "valueFloat", type = Type.FLOAT), //
								@EntityIndexProperty(name = "valueDouble", type = Type.DOUBLE), //
								@EntityIndexProperty(name = "valueByteArray", type = Type.BYTE_ARRAY), //
								@EntityIndexProperty(name = "valueString", type = Type.STRING), //
								@EntityIndexProperty(name = "valueInstant", type = Type.INSTANT), //
								@EntityIndexProperty(name = "valueLocalDate", type = Type.LOCAL_DATE), //
								@EntityIndexProperty(name = "valueLocalTime", type = Type.LOCAL_TIME), //
								@EntityIndexProperty(name = "valueLocalDateTime", type = Type.LOCAL_DATE_TIME), //
								@EntityIndexProperty(name = "valueEnum", type = "java.time.Month"), //
								@EntityIndexProperty(name = "valueClass", type = "Class<String>"), //
								@EntityIndexProperty(name = "valueStringList", type = "List<String>"), //
								@EntityIndexProperty(name = "valueEnumSet", type = "Set<Month>"), //
								@EntityIndexProperty(name = "valueObjectMap", type = "Map<Object, Object>"), //
								@EntityIndexProperty(name = "valueReference", type = "EntityReference<SandboxParent>"), //
								@EntityIndexProperty(name = "valueXSerializable", type = SandboxChildPrototype.NAME) //
						} //
				), //
				@EntityIndex( //
						name = SandboxPrototype.INDEX_XSERIALIZABLE_NAME, //
						properties = { //
								@EntityIndexProperty(name = "valueParentString", type = Type.STRING), //
								@EntityIndexProperty(name = "valueString", type = Type.STRING) //
						} //
				) //
		} //
)
public interface SandboxPrototype {

	public static final String NAME = "com.ijioio.model.Sandbox";

	public static final String INDEX_NAME = "com.ijioio.model.SandboxIndex";

	public static final String INDEX_XSERIALIZABLE_NAME = "com.ijioio.model.SandboxXSerializableIndex";
}
