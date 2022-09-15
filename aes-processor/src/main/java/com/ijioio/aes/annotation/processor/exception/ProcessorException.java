package com.ijioio.aes.annotation.processor.exception;

import com.ijioio.aes.annotation.processor.MessageContext;

public abstract class ProcessorException extends Exception {

	private static final long serialVersionUID = 4561832558038397350L;

	private MessageContext context;

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 */
	protected ProcessorException(String message) {
		super(message);
	}

	/**
	 * Constructs a new exception with the specified details.
	 *
	 * @param message with the details of the exception
	 * @param context defining an exception position
	 */
	protected ProcessorException(String message, MessageContext context) {

		super(message);

		this.context = context;
	}

	/**
	 * Returns the context defining an exception position.
	 *
	 * @return context of this exception, may be {@code null}
	 */
	public MessageContext getContext() {
		return context;
	}
}
