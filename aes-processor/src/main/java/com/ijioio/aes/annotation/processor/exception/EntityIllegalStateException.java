package com.ijioio.aes.annotation.processor.exception;

import com.ijioio.aes.annotation.processor.MessageContext;

public class EntityIllegalStateException extends ProcessorException {

	private static final long serialVersionUID = -3866026527128977914L;

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 */
	public EntityIllegalStateException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 * @param context defining an exception position
	 */
	public EntityIllegalStateException(String message, MessageContext context) {
		super(message, context);
	}
}
