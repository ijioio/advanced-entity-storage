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

	public void debug(CharSequence message, MessageContext hint) {
		print(Kind.NOTE, message, hint);
	}

	public void info(CharSequence message) {
		print(Kind.NOTE, message, null);
	}

	public void info(CharSequence message, MessageContext hint) {
		print(Kind.NOTE, message, hint);
	}

	public void warn(CharSequence message) {
		print(Kind.WARNING, message, null);
	}

	public void warn(CharSequence message, MessageContext hint) {
		print(Kind.WARNING, message, hint);
	}

	public void error(CharSequence message) {
		print(Kind.ERROR, message, null);
	}

	public void error(CharSequence message, MessageContext hint) {
		print(Kind.ERROR, message, hint);
	}

	public void print(Kind kind, CharSequence message, MessageContext hint) {

		Objects.requireNonNull(kind);
		Objects.requireNonNull(message);

		messager.printMessage(kind, message, hint != null ? hint.getElement() : null,
				hint != null ? hint.getAnnotationMirror() : null, hint != null ? hint.getAnnotationValue() : null);
	}
}
