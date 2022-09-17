package com.ijioio.aes.annotation.processor.exception;

import com.ijioio.aes.annotation.processor.MessageContext;

public class TypeIllegalStateException extends ProcessorException {

	private static final long serialVersionUID = -3684414439797512813L;

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 */
	public TypeIllegalStateException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 * @param context defining an exception position
	 */
	public TypeIllegalStateException(String message, MessageContext context) {
		super(message, context);
	}
}
