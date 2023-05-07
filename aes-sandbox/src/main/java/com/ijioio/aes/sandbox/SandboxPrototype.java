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
		properties = { //
				@EntityProperty(name = "valueBoolean", type = @Type(name = Type.BOOLEAN)), //
				@EntityProperty(name = "valueChar", type = @Type(name = Type.CHAR)), //
				@EntityProperty(name = "valueByte", type = @Type(name = Type.BYTE)), //
				@EntityProperty(name = "valueShort", type = @Type(name = Type.SHORT)), //
				@EntityProperty(name = "valueInt", type = @Type(name = Type.INT)), //
				@EntityProperty(name = "valueLong", type = @Type(name = Type.LONG)), //
				@EntityProperty(name = "valueFloat", type = @Type(name = Type.FLOAT)), //
				@EntityProperty(name = "valueDouble", type = @Type(name = Type.DOUBLE)), //
				@EntityProperty(name = "valueByteArray", type = @Type(name = Type.BYTE_ARRAY)), //
				@EntityProperty(name = "valueString", type = @Type(name = Type.STRING)), //
				@EntityProperty(name = "valueInstant", type = @Type(name = Type.INSTANT)), //
				@EntityProperty(name = "valueLocalDate", type = @Type(name = Type.LOCAL_DATE)), //
				@EntityProperty(name = "valueLocalTime", type = @Type(name = Type.LOCAL_TIME)), //
				@EntityProperty(name = "valueLocalDateTime", type = @Type(name = Type.LOCAL_DATE_TIME)), //
				@EntityProperty(name = "valueEnum", type = @Type(name = "java.time.Month")), //
				@EntityProperty(name = "valueClass", type = @Type(name = Type.CLASS), parameters = @Type(name = Type.STRING)), //
				@EntityProperty(name = "valueStringList", type = @Type(name = Type.LIST), parameters = @Type(name = Type.STRING), attributes = Attribute.FINAL), //
				@EntityProperty(name = "valueEnumSet", type = @Type(name = Type.SET), parameters = @Type(name = "java.time.Month"), attributes = Attribute.FINAL), //
				@EntityProperty(name = "valueObjectMap", type = @Type(name = Type.MAP), parameters = {
						@Type(name = "java.lang.Object"),
						@Type(name = "java.lang.Object") }, attributes = Attribute.FINAL), //
				@EntityProperty(name = "valueReference", type = @Type(name = SandboxParentPrototype.NAME, reference = true), attributes = Attribute.FINAL), //
				@EntityProperty(name = "valueXSerializable", type = @Type(name = SandboxChildPrototype.NAME)), //
				@EntityProperty(name = "valueXSerializableList", type = @Type(name = Type.LIST), parameters = @Type(name = SandboxChildPrototype.NAME), attributes = Attribute.FINAL) //
		}, //
		indexes = { //
				@EntityIndex( //
						name = SandboxPrototype.INDEX_NAME, //
						properties = { //
								@EntityIndexProperty(name = "valueBoolean", type = @Type(name = Type.BOOLEAN)), //
								@EntityIndexProperty(name = "valueChar", type = @Type(name = Type.CHAR)), //
								@EntityIndexProperty(name = "valueByte", type = @Type(name = Type.BYTE)), //
								@EntityIndexProperty(name = "valueShort", type = @Type(name = Type.SHORT)), //
								@EntityIndexProperty(name = "valueInt", type = @Type(name = Type.INT)), //
								@EntityIndexProperty(name = "valueLong", type = @Type(name = Type.LONG)), //
								@EntityIndexProperty(name = "valueFloat", type = @Type(name = Type.FLOAT)), //
								@EntityIndexProperty(name = "valueDouble", type = @Type(name = Type.DOUBLE)), //
								@EntityIndexProperty(name = "valueByteArray", type = @Type(name = Type.BYTE_ARRAY)), //
								@EntityIndexProperty(name = "valueString", type = @Type(name = Type.STRING)), //
								@EntityIndexProperty(name = "valueInstant", type = @Type(name = Type.INSTANT)), //
								@EntityIndexProperty(name = "valueLocalDate", type = @Type(name = Type.LOCAL_DATE)), //
								@EntityIndexProperty(name = "valueLocalTime", type = @Type(name = Type.LOCAL_TIME)), //
								@EntityIndexProperty(name = "valueLocalDateTime", type = @Type(name = Type.LOCAL_DATE_TIME)), //
								@EntityIndexProperty(name = "valueEnum", type = @Type(name = "java.time.Month")), //
								@EntityIndexProperty(name = "valueClass", type = @Type(name = Type.CLASS), parameters = @Type(name = Type.STRING)), //
								@EntityIndexProperty(name = "valueStringList", type = @Type(name = Type.LIST), parameters = @Type(name = Type.STRING)), //
								@EntityIndexProperty(name = "valueEnumSet", type = @Type(name = Type.SET), parameters = @Type(name = "java.time.Month")), //
								@EntityIndexProperty(name = "valueObjectMap", type = @Type(name = Type.MAP), parameters = {
										@Type(name = "java.lang.Object"), @Type(name = "java.lang.Object") }), //
								@EntityIndexProperty(name = "valueReference", type = @Type(name = SandboxParentPrototype.NAME, reference = true)), //
								@EntityIndexProperty(name = "valueXSerializable", type = @Type(name = SandboxChildPrototype.NAME)) //
						} //
				), //
				@EntityIndex( //
						name = SandboxPrototype.INDEX_XSERIALIZABLE_NAME, //
						properties = { //
								@EntityIndexProperty(name = "valueParentString", type = @Type(name = Type.STRING)), //
								@EntityIndexProperty(name = "valueString", type = @Type(name = Type.STRING)) } //
				) //
		} //
)
public interface SandboxPrototype {

	public static final String NAME = "com.ijioio.model.Sandbox";

	public static final String INDEX_NAME = "com.ijioio.model.SandboxIndex";

	public static final String INDEX_XSERIALIZABLE_NAME = "com.ijioio.model.SandboxXSerializableIndex";
}
