package com.ijioio.aes.annotation.processor;

import java.util.Objects;

import javax.tools.Diagnostic.Kind;

public class Messager {

	public static Messager of(javax.annotation.processing.Messager messager) {
		return new Messager(messager);
	}

	private final javax.annotation.processing.Messager messager;

	private Messager(javax.annotation.processing.Messager messager) {
		this.messager = messager;
	}

	public void debug(CharSequence message) {
		print(Kind.NOTE, message, null);
	}

	public void debug(CharSequence message, MessageContext context) {
		print(Kind.NOTE, message, context);
	}

	public void info(CharSequence message) {
		print(Kind.NOTE, message, null);
	}

	public void info(CharSequence message, MessageContext context) {
		print(Kind.NOTE, message, context);
	}

	public void warn(CharSequence message) {
		print(Kind.WARNING, message, null);
	}

	public void warn(CharSequence message, MessageContext context) {
		print(Kind.WARNING, message, context);
	}

	public void error(CharSequence message) {
		print(Kind.ERROR, message, null);
	}

	public void error(CharSequence message, MessageContext context) {
		print(Kind.ERROR, message, context);
	}

	public void print(Kind kind, CharSequence message, MessageContext context) {

		Objects.requireNonNull(kind);
		Objects.requireNonNull(message);

		messager.printMessage(kind, message, context != null ? context.getElement() : null,
				context != null ? context.getAnnotationMirror() : null,
				context != null ? context.getAnnotationValue() : null);
	}
}
