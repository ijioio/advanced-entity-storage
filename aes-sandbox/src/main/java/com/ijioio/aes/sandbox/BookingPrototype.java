package com.ijioio.aes.sandbox;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.EntityProperty;
import com.ijioio.aes.annotation.Type;

@Entity( //
		name = BookingPrototype.NAME, //
		interfaces = { Number.class }, //
		properties = { //
				@EntityProperty(name = "number", type = @Type(name = Type.STRING)), //
				@EntityProperty(name = "status", type = @Type(name = BookingStatus.NAME)), //
				@EntityProperty(name = "pnr", type = @Type(name = Type.STRING)), //
				@EntityProperty(name = "agent", type = @Type(name = PersonPrototype.NAME, reference = true)), //
				@EntityProperty(name = "passengers", type = @Type(name = Type.LIST), parameters = @Type(name = PersonPrototype.NAME, reference = true)), //
				@EntityProperty(name = "codes", type = @Type(name = Type.MAP), parameters = { @Type(name = Type.STRING),
						@Type(name = Type.STRING) }) //
		} //
)
public interface BookingPrototype {

	public static final String NAME = "com.ijioio.model.Booking";
}
