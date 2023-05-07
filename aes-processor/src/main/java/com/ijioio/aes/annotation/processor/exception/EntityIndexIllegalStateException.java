package com.ijioio.aes.annotation.processor.exception;

import com.ijioio.aes.annotation.processor.MessageContext;

public class EntityIndexIllegalStateException extends ProcessorException {

	private static final long serialVersionUID = -3353971118124628303L;

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 */
	public EntityIndexIllegalStateException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 * @param context defining an exception position
	 */
	public EntityIndexIllegalStateException(String message, MessageContext context) {
		super(message, context);
	}
}
