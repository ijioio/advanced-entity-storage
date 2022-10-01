package com.ijioio.aes.sandbox;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;

@Entity( //
		name = SandboxChildPrototype.NAME, //
		properties = { //
				@EntityProperty(name = "valueString", type = @Type(name = Type.STRING)) //
		} //
)
public interface SandboxChildPrototype {

	public static final String NAME = "com.ijioio.model.SandboxChild";
}
