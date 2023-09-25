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

public class ParameterMetadata {

	public static ParameterMetadata of(ProcessingEnvironment environment, ProcessorContext context)
			throws ProcessorException {
		return new ParameterMetadata(environment, context);
	}

	private String name;

	private boolean wildcard;

	private ParameterMetadata(ProcessingEnvironment environment, ProcessorContext context) throws ProcessorException {

		Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = context.getAnnotationMirror()
				.getElementValues();

		for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

			ExecutableElement key = entry.getKey();
			AnnotationValue value = entry.getValue();

			if (key.getSimpleName().contentEquals("name")) {

				name = ProcessorUtil.stringVisitor.visit(value);

			} else if (key.getSimpleName().contentEquals("wildcard")) {

				wildcard = ProcessorUtil.booleanVisitor.visit(value);
			}
		}

		if (TextUtil.isBlank(name)) {
			throw new TypeIllegalStateException(String.format("Name of the parameter is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}
	}

	public String getName() {
		return name;
	}

	public boolean isWildcard() {
		return wildcard;
	}

	@Override
	public String toString() {
		return "ParameterMetadata [name=" + name + ", wildcard=" + wildcard + "]";
	}
}
