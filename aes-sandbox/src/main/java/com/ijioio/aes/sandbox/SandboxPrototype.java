package com.ijioio.aes.sandbox;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;

@Entity( //
		name = SandboxPrototype.NAME, //
		properties = { //
				@EntityProperty(name = "valueBoolean", type = @Type(name = Type.BOOLEAN)), //
				@EntityProperty(name = "valueChar", type = @Type(name = Type.CHAR)), //
				@EntityProperty(name = "valueByte", type = @Type(name = Type.BYTE)), //
				@EntityProperty(name = "valueShort", type = @Type(name = Type.SHORT)), // s
				@EntityProperty(name = "valueInt", type = @Type(name = Type.INT)), //
				@EntityProperty(name = "valueLong", type = @Type(name = Type.LONG)), //
				@EntityProperty(name = "valueFloat", type = @Type(name = Type.FLOAT)), //
				@EntityProperty(name = "valueDouble", type = @Type(name = Type.DOUBLE)), //
				@EntityProperty(name = "valueString", type = @Type(name = Type.STRING)), //
				@EntityProperty(name = "valueEnum", type = @Type(name = "java.time.Month")), //
				@EntityProperty(name = "valueStringList", type = @Type(name = Type.LIST), parameters = @Type(name = Type.STRING)), //
				@EntityProperty(name = "valueEnumList", type = @Type(name = Type.LIST), parameters = @Type(name = "java.time.Month")), //
				@EntityProperty(name = "valueObjectList", type = @Type(name = Type.LIST), parameters = @Type(name = "java.lang.Object")), //
				@EntityProperty(name = "valueXSerializable", type = @Type(name = SandboxChildPrototype.NAME)) //
		} //
)
public interface SandboxPrototype {

	public static final String NAME = "com.ijioio.model.Sandbox";
}
