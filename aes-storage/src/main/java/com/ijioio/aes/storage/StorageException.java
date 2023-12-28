package com.ijioio.aes.storage;

public class StorageException extends RuntimeException {

	private static final long serialVersionUID = -394434538970732576L;

	public StorageException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public StorageException(final Throwable cause) {
		super(cause);
	}

	public StorageException(final String message) {
		super(message);
	}
}
