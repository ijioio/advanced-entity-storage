package com.ijioio.aes.annotation.processor;

import java.util.Map;
import java.util.Map.Entry;

import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;

import com.ijioio.aes.annotation.processor.exception.ProcessorException;

public class TypeMetadata {

	public static TypeMetadata of(ProcessorContext context) throws ProcessorException {
		return new TypeMetadata(context);
	}

	private final ProcessorContext context;

	private String name;

	private boolean reference;

	private TypeMetadata(ProcessorContext context) throws ProcessorException {

		this.context = context;

		Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = context.getAnnotationMirror()
				.getElementValues();

		for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

			ExecutableElement key = entry.getKey();
			AnnotationValue value = entry.getValue();

			if (key.getSimpleName().contentEquals("name")) {

				name = value.accept(new SimpleAnnotationValueVisitor8<String, Void>() {

					@Override
					public String visitString(String s, Void p) {
						return s;
					}

				}, null);

			} else if (key.getSimpleName().contentEquals("reference")) {

				reference = value.accept(new SimpleAnnotationValueVisitor8<Boolean, Void>() {

					@Override
					public Boolean visitBoolean(boolean b, Void p) {
						return Boolean.valueOf(b);
					}

				}, null).booleanValue();
			}
		}
	}

	public ProcessorContext getContext() {
		return context;
	}

	public String getName() {
		return name;
	}

	public boolean isReference() {
		return reference;
	}

	@Override
	public String toString() {
		return "TypeMetadata [name=" + name + ", reference=" + reference + "]";
	}
}
