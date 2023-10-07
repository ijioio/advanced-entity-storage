package com.ijioio.aes.annotation.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

import com.ijioio.aes.annotation.processor.exception.EntityPropertyIllegalStateException;
import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.exception.TypeIllegalStateException;
import com.ijioio.aes.annotation.processor.util.ProcessorUtil;
import com.ijioio.aes.annotation.processor.util.TextUtil;

public class TypeMetadata {

	public static TypeMetadata of(String name, String type, boolean reference, boolean list, boolean set,
			List<ParameterMetadata> parameters) {
		return new TypeMetadata(name, type, reference, list, set, parameters);
	}

	public static TypeMetadata of(ProcessingEnvironment environment, ProcessorContext context)
			throws ProcessorException {
		return new TypeMetadata(environment, context);
	}

	private String name;

	private String type;

	private boolean reference;

	private boolean list;

	private boolean set;

	private final List<ParameterMetadata> parameters = new ArrayList<>();

	private TypeMetadata(String name, String type, boolean reference, boolean list, boolean set,
			List<ParameterMetadata> parameters) {

		this.name = name;
		this.type = type;
		this.reference = reference;
		this.list = list;
		this.set = set;

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

			} else if (key.getSimpleName().contentEquals("reference")) {

				reference = ProcessorUtil.booleanVisitor.visit(value).booleanValue();

			} else if (key.getSimpleName().contentEquals("list")) {

				list = ProcessorUtil.booleanVisitor.visit(value).booleanValue();

			} else if (key.getSimpleName().contentEquals("set")) {

				set = ProcessorUtil.booleanVisitor.visit(value).booleanValue();

			} else if (key.getSimpleName().contentEquals("parameters")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				parameters.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					AnnotationMirror annotationMirror = ProcessorUtil.annotationVisitor.visit(annotationValue);

					ParameterMetadata type = ParameterMetadata.of(environment,
							context.withAnnotationMirror(annotationMirror));

					parameters.add(type);
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

		if (list && set) {
			throw new EntityPropertyIllegalStateException(
					String.format("Type is not allowed to be declared as list and set at the same time"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public boolean isReference() {
		return reference;
	}

	public boolean isList() {
		return list;
	}

	public boolean isSet() {
		return set;
	}

	public List<ParameterMetadata> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return "TypeMetadata [name=" + name + ", type=" + type + ", reference=" + reference + ", list=" + list
				+ ", set=" + set + ", parameters=" + parameters + "]";
	}
}
