package com.ijioio.aes.core.serialization;

public class SerializationException extends Exception {

	private static final long serialVersionUID = 5094661384014643526L;

	public SerializationException(final String message) {
		super(message);
	}

	public SerializationException(final Throwable cause) {
		super(cause);
	}

	public SerializationException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
