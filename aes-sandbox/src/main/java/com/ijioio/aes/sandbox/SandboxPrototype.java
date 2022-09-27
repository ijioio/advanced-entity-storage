package com.ijioio.aes.sandbox;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;

//public static final String BOOLEAN = "BOOLEAN";
//public static final String BYTE = "BYTE";
//public static final String SHORT = "SHORT";
//public static final String INT = "INT";
//public static final String LONG = "LONG";
//public static final String CHAR = "CHAR";
//public static final String FLOAT = "FLOAT";
//public static final String DOUBLE = "DOUBLE";
//public static final String STRING = "STRING";
//public static final String LIST = "LIST";
//public static final String SET = "SET";
//public static final String MAP = "MAP";

@Entity( //
		name = SandboxPrototype.NAME, //
		properties = { //
				@EntityProperty(name = "valueBoolean", type = @Type(name = Type.BOOLEAN)), //
				@EntityProperty(name = "valueByte", type = @Type(name = Type.BYTE)), //
				@EntityProperty(name = "valueShort", type = @Type(name = Type.SHORT)), // s
				@EntityProperty(name = "valueInt", type = @Type(name = Type.INT)), //
				@EntityProperty(name = "valueLong", type = @Type(name = Type.LONG)), //
				@EntityProperty(name = "valueChar", type = @Type(name = Type.CHAR)), //
				@EntityProperty(name = "valueFloat", type = @Type(name = Type.FLOAT)), //
				@EntityProperty(name = "valueDouble", type = @Type(name = Type.DOUBLE)), //
				@EntityProperty(name = "valueString", type = @Type(name = Type.STRING)), //
				@EntityProperty(name = "valueEnum", type = @Type(name = "java.time.Month")), //
				@EntityProperty(name = "valueStringList", type = @Type(name = Type.LIST), parameters = @Type(name = Type.STRING)), //
				@EntityProperty(name = "valueEnumList", type = @Type(name = Type.LIST), parameters = @Type(name = "java.time.Month")), //
				@EntityProperty(name = "valueObjectList", type = @Type(name = Type.LIST), parameters = @Type(name = "java.lang.Object")) //
		} //
)
public interface SandboxPrototype {

	public static final String NAME = "com.ijioio.model.Sandbox";
}
