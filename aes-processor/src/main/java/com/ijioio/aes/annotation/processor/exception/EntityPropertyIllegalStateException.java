package com.ijioio.aes.annotation.processor.exception;

import com.ijioio.aes.annotation.processor.MessageContext;

public class EntityPropertyIllegalStateException extends ProcessorException {

	private static final long serialVersionUID = 1818821903388531568L;

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 */
	public EntityPropertyIllegalStateException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 * @param context defining an exception position
	 */
	public EntityPropertyIllegalStateException(String message, MessageContext context) {
		super(message, context);
	}
}
