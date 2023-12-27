package com.ijioio.aes.persistence;

public class PersistenceException extends Exception {

	private static final long serialVersionUID = -4122131614458304205L;

	public PersistenceException(final String message) {
		super(message);
	}

	public PersistenceException(final Throwable cause) {
		super(cause);
	}

	public PersistenceException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
