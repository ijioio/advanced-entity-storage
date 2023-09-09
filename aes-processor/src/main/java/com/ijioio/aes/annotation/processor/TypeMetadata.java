package com.ijioio.aes.annotation.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.exception.TypeIllegalStateException;
import com.ijioio.aes.annotation.processor.util.ProcessorUtil;
import com.ijioio.aes.annotation.processor.util.TextUtil;

public class TypeMetadata {

	public static TypeMetadata of(String name, String type, List<String> parameters) {
		return new TypeMetadata(name, type, parameters);
	}

	public static TypeMetadata of(ProcessingEnvironment environment, ProcessorContext context)
			throws ProcessorException {
		return new TypeMetadata(environment, context);
	}

	private String name;

	private String type;

	private final List<String> parameters = new ArrayList<>();

	private TypeMetadata(String name, String type, List<String> parameters) {

		this.name = name;
		this.type = type;

		this.parameters.clear();
		this.parameters.addAll(parameters);
	}

	private TypeMetadata(ProcessingEnvironment environment, ProcessorContext context) throws ProcessorException {

		Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = context.getAnnotationMirror()
				.getElementValues();

		for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

			ExecutableElement key = entry.getKey();
			AnnotationValue value = entry.getValue();

			if (key.getSimpleName().contentEquals("name")) {

				name = ProcessorUtil.stringVisitor.visit(value);

			} else if (key.getSimpleName().contentEquals("type")) {

				type = ProcessorUtil.stringVisitor.visit(value);

			} else if (key.getSimpleName().contentEquals("parameters")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				parameters.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					String parameter = ProcessorUtil.stringVisitor.visit(annotationValue);

					parameters.add(parameter);
				}
			}
		}

		if (TextUtil.isBlank(name)) {
			throw new TypeIllegalStateException(String.format("Name of the type is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}

		if (TextUtil.isBlank(type)) {
			throw new TypeIllegalStateException(String.format("Type of the type is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public List<String> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return "TypeMetadata [name=" + name + ", type=" + type + ", parameters=" + parameters + "]";
	}
}
