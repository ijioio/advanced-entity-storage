package com.ijioio.aes.annotation.processor;

import java.util.HashSet;
import java.util.LinkedHashMap;
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
import com.ijioio.aes.annotation.processor.exception.EntityIndexIllegalStateException;
import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.util.ProcessorUtil;
import com.ijioio.aes.annotation.processor.util.TextUtil;
import com.ijioio.aes.annotation.processor.util.TypeUtil;

public class EntityIndexMetadata {

	public static EntityIndexMetadata of(ProcessingEnvironment environment, ProcessorContext context)
			throws ProcessorException {
		return new EntityIndexMetadata(environment, context);
	}

	private static final Set<Attribute> supportedAttributes = new HashSet<>();

	static {

	}

	private String name;

	private final Set<Attribute> attributes = new HashSet<>();

	private final Map<String, EntityIndexPropertyMetadata> properties = new LinkedHashMap<>();

	private EntityIndexMetadata(ProcessingEnvironment environment, ProcessorContext context) throws ProcessorException {

		Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = context.getAnnotationMirror()
				.getElementValues();

		for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

			ExecutableElement key = entry.getKey();
			AnnotationValue value = entry.getValue();

			if (key.getSimpleName().contentEquals("name")) {

				name = ProcessorUtil.stringVisitor.visit(value);

				if (!TypeUtil.isValidIdentifier(name)) {
					throw new EntityIndexIllegalStateException(
							String.format("Name should be a valid class name identifier"),
							MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
				}

			} else if (key.getSimpleName().contentEquals("attributes")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				attributes.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					VariableElement variableElement = ProcessorUtil.enumVisitor.visit(annotationValue);

					Attribute attribute = Attribute.valueOf(variableElement.getSimpleName().toString());

					if (!supportedAttributes.contains(attribute)) {
						throw new EntityIndexIllegalStateException(
								String.format("Attribute %s is not allowed for the entity index", attribute),
								MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
					}

					attributes.add(attribute);
				}

			} else if (key.getSimpleName().contentEquals("properties")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				properties.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					AnnotationMirror annotationMirror = ProcessorUtil.annotationVisitor.visit(annotationValue);

					EntityIndexPropertyMetadata property = EntityIndexPropertyMetadata.of(environment,
							context.withAnnotationMirror(annotationMirror));

					if (properties.containsKey(property.getName())) {
						throw new EntityIndexIllegalStateException(
								String.format("Property %s is already defined for the entity index",
										property.getName()),
								MessageContext.of(context.getElement(), annotationMirror, annotationValue));
					}

					properties.put(property.getName(), property);
				}
			}
		}

		if (TextUtil.isBlank(name)) {
			throw new EntityIndexIllegalStateException(String.format("Name of the entity index is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}
	}

	public String getName() {
		return name;
	}

	public Map<String, EntityIndexPropertyMetadata> getProperties() {
		return properties;
	}

	@Override
	public String toString() {
		return "EntityIndexMetadata [name=" + name + ", attributes=" + attributes + ", properties=" + properties + "]";
	}
}
