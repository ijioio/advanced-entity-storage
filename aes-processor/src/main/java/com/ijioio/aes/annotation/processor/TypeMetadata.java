package com.ijioio.aes.annotation.processor;

import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.exception.TypeIllegalStateException;
import com.ijioio.aes.annotation.processor.util.ProcessorUtil;
import com.ijioio.aes.annotation.processor.util.TextUtil;
import com.ijioio.aes.annotation.processor.util.TypeUtil;

public class TypeMetadata {

	public static TypeMetadata of(ProcessingEnvironment environment, ProcessorContext context)
			throws ProcessorException {
		return new TypeMetadata(environment, context);
	}

	private String name;

	private boolean reference;

	private TypeMetadata(ProcessingEnvironment environment, ProcessorContext context) throws ProcessorException {

		Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = context.getAnnotationMirror()
				.getElementValues();

		for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

			ExecutableElement key = entry.getKey();
			AnnotationValue value = entry.getValue();

			if (key.getSimpleName().contentEquals("name")) {

				name = ProcessorUtil.stringVisitor.visit(value);

				if (!TypeUtil.isValidIdentifier(name)) {
					throw new TypeIllegalStateException(String.format("Name should be a valid class name identifier"),
							MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
				}

			} else if (key.getSimpleName().contentEquals("reference")) {

				reference = ProcessorUtil.booleanVisitor.visit(value).booleanValue();
			}
		}

		if (TextUtil.isBlank(name)) {
			throw new TypeIllegalStateException(String.format("Name of the type is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}
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
