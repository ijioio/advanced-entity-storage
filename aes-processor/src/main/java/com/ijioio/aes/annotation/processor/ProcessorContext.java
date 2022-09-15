package com.ijioio.aes.annotation.processor;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;

public class ProcessorContext {

	public static ProcessorContext empty() {
		return new ProcessorContext(null, null);
	}

	public static ProcessorContext of(Element element) {
		return new ProcessorContext(element, null);
	}

	public static ProcessorContext of(Element element, AnnotationMirror annotationMirror) {
		return new ProcessorContext(element, annotationMirror);
	}

	private final Element element;

	private final AnnotationMirror annotationMirror;

	private ProcessorContext(Element element, AnnotationMirror annotationMirror) {

		this.element = element;
		this.annotationMirror = annotationMirror;
	}

	public Element getElement() {
		return element;
	}

	public AnnotationMirror getAnnotationMirror() {
		return annotationMirror;
	}

	public ProcessorContext withElement(Element element) {
		return new ProcessorContext(element, annotationMirror);
	}

	public ProcessorContext withAnnotationMirror(AnnotationMirror annotationMirror) {
		return new ProcessorContext(element, annotationMirror);
	}
}
