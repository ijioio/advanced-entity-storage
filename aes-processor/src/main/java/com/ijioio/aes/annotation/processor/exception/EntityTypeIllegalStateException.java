package com.ijioio.aes.annotation.processor.exception;

import com.ijioio.aes.annotation.processor.MessageContext;

public class EntityTypeIllegalStateException extends ProcessorException {

	private static final long serialVersionUID = -3866026527128977914L;

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 */
	public EntityTypeIllegalStateException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 * @param context defining an exception position
	 */
	public EntityTypeIllegalStateException(String message, MessageContext context) {
		super(message, context);
	}
}
