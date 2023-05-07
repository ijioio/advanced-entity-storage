package com.ijioio.aes.annotation.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.processor.exception.EntityIllegalStateException;
import com.ijioio.aes.annotation.processor.exception.EntityPropertyIllegalStateException;
import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.util.ProcessorUtil;
import com.ijioio.aes.annotation.processor.util.TextUtil;
import com.ijioio.aes.annotation.processor.util.TypeUtil;

public class EntityPropertyMetadata {

	public static EntityPropertyMetadata of(ProcessingEnvironment environment, ProcessorContext context)
			throws ProcessorException {
		return new EntityPropertyMetadata(environment, context);
	}

	private static final Set<Attribute> supportedAttributes = new HashSet<>();

	static {

		supportedAttributes.add(Attribute.FINAL);
	}

	private String name;

	private TypeMetadata type;

	private final List<TypeMetadata> parameters = new ArrayList<>();

	private final Set<Attribute> attributes = new HashSet<>();

	private EntityPropertyMetadata(ProcessingEnvironment environment, ProcessorContext context)
			throws ProcessorException {

		Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = context.getAnnotationMirror()
				.getElementValues();

		for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

			ExecutableElement key = entry.getKey();
			AnnotationValue value = entry.getValue();

			if (key.getSimpleName().contentEquals("name")) {

				name = ProcessorUtil.stringVisitor.visit(value);

			} else if (key.getSimpleName().contentEquals("type")) {

				AnnotationMirror annotationMirror = ProcessorUtil.annotationVisitor.visit(value);

				type = TypeMetadata.of(environment, context.withAnnotationMirror(annotationMirror));

			} else if (key.getSimpleName().contentEquals("parameters")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				parameters.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					AnnotationMirror annotationMirror = ProcessorUtil.annotationVisitor.visit(annotationValue);

					TypeMetadata type = TypeMetadata.of(environment, context.withAnnotationMirror(annotationMirror));

					parameters.add(type);
				}

			} else if (key.getSimpleName().contentEquals("attributes")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				attributes.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					VariableElement variableElement = ProcessorUtil.enumVisitor.visit(annotationValue);

					Attribute attribute = Attribute.valueOf(variableElement.getSimpleName().toString());

					if (!supportedAttributes.contains(attribute)) {
						throw new EntityIllegalStateException(
								String.format("attribute %s is not allowed for the entity property", attribute),
								MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
					}

					attributes.add(attribute);
				}
			}
		}

		if (TextUtil.isBlank(name)) {
			throw new EntityPropertyIllegalStateException(String.format("Name of the entity property is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}

		if (type == null) {
			throw new EntityPropertyIllegalStateException(String.format("Type of the entity property is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}

		if (attributes.contains(Attribute.FINAL)
				&& TypeUtil.isImmutable(type.isReference() ? TypeUtil.ENTITY_REFERENCE_TYPE_NAME : type.getName())) {
			throw new EntityPropertyIllegalStateException(
					String.format("Final attribute is not allowed for the immutable types"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}
	}

	public String getName() {
		return name;
	}

	public TypeMetadata getType() {
		return type;
	}

	public List<TypeMetadata> getParameters() {
		return parameters;
	}

	public boolean isFinal() {
		return attributes.contains(Attribute.FINAL);
	}

	@Override
	public String toString() {
		return "EntityPropertyMetadata [name=" + name + ", type=" + type + ", parameters=" + parameters
				+ ", attributes=" + attributes + "]";
	}
}
