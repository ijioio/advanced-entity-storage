package com.ijioio.aes.annotation.processor;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;

public class MessageContext {

	public static MessageContext empty() {
		return new MessageContext(null, null, null);
	}

	public static MessageContext of(Element element) {
		return new MessageContext(element, null, null);
	}

	public static MessageContext of(Element element, AnnotationMirror annotationMirror) {
		return new MessageContext(element, annotationMirror, null);
	}

	public static MessageContext of(Element element, AnnotationMirror annotationMirror,
			AnnotationValue annotationValue) {
		return new MessageContext(element, annotationMirror, annotationValue);
	}

	private final Element element;

	private final AnnotationMirror annotationMirror;

	private final AnnotationValue annotationValue;

	private MessageContext(Element element, AnnotationMirror annotationMirror, AnnotationValue annotationValue) {

		this.element = element;
		this.annotationMirror = annotationMirror;
		this.annotationValue = annotationValue;
	}

	public Element getElement() {
		return element;
	}

	public AnnotationMirror getAnnotationMirror() {
		return annotationMirror;
	}

	public AnnotationValue getAnnotationValue() {
		return annotationValue;
	}

	public MessageContext withElement(Element element) {
		return new MessageContext(element, annotationMirror, annotationValue);
	}

	public MessageContext withAnnotationMirror(AnnotationMirror annotationMirror) {
		return new MessageContext(element, annotationMirror, annotationValue);
	}

	public MessageContext withAnnotationValue(AnnotationValue annotationValue) {
		return new MessageContext(element, annotationMirror, annotationValue);
	}
}
