package com.ijioio.aes.annotation.processor;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.processor.exception.EntityPropertyIllegalStateException;
import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.util.ProcessorUtil;
import com.ijioio.aes.annotation.processor.util.TextUtil;

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

	private String type;

	private boolean reference;

	private boolean list;

	private boolean set;

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

				type = ProcessorUtil.stringVisitor.visit(value);

			} else if (key.getSimpleName().contentEquals("reference")) {

				reference = ProcessorUtil.booleanVisitor.visit(value).booleanValue();

			} else if (key.getSimpleName().contentEquals("list")) {

				list = ProcessorUtil.booleanVisitor.visit(value).booleanValue();

			} else if (key.getSimpleName().contentEquals("set")) {

				set = ProcessorUtil.booleanVisitor.visit(value).booleanValue();

			} else if (key.getSimpleName().contentEquals("attributes")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				attributes.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					VariableElement variableElement = ProcessorUtil.enumVisitor.visit(annotationValue);

					Attribute attribute = Attribute.valueOf(variableElement.getSimpleName().toString());

					if (!supportedAttributes.contains(attribute)) {
						throw new EntityPropertyIllegalStateException(
								String.format("Attribute %s is not allowed for the entity property", attribute),
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

		if (TextUtil.isBlank(type)) {
			throw new EntityPropertyIllegalStateException(String.format("Type of the entity property is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}

		if (list && set) {
			throw new EntityPropertyIllegalStateException(
					String.format("Entity property is not allowed to be declared as list and set at the same time"),
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

	public boolean isFinal() {
		return attributes.contains(Attribute.FINAL);
	}

	@Override
	public String toString() {
		return "EntityPropertyMetadata [name=" + name + ", type=" + type + ", reference=" + reference + ", list=" + list
				+ ", set=" + set + ", attributes=" + attributes + "]";
	}
}
