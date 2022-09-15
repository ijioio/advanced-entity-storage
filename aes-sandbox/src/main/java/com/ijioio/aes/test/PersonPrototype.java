package com.ijioio.aes.test;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;

@Entity( //
		name = PersonPrototype.NAME, //
		properties = { //
				@EntityProperty(name = "firstName", type = @Type(name = Type.STRING)), //
				@EntityProperty(name = "lastName", type = @Type(name = Type.STRING)) //
		} //
)
public interface PersonPrototype {

	public static final String NAME = "com.ijioio.model.Person";
}
