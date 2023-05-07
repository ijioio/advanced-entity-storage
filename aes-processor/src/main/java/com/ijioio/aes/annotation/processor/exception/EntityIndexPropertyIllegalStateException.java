package com.ijioio.aes.annotation.processor.exception;

import com.ijioio.aes.annotation.processor.MessageContext;

public class EntityIndexPropertyIllegalStateException extends ProcessorException {

	private static final long serialVersionUID = -7869063405256942101L;

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 */
	public EntityIndexPropertyIllegalStateException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 * @param context defining an exception position
	 */
	public EntityIndexPropertyIllegalStateException(String message, MessageContext context) {
		super(message, context);
	}
}
