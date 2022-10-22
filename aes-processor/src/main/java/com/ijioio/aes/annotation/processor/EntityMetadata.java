package com.ijioio.aes.annotation.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.processor.exception.EntityIllegalStateException;
import com.ijioio.aes.annotation.processor.exception.EntityPropertyIllegalStateException;
import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.util.ProcessorUtil;
import com.ijioio.aes.annotation.processor.util.TextUtil;
import com.ijioio.aes.annotation.processor.util.TypeUtil;

public class EntityMetadata {

	public static EntityMetadata of(ProcessorContext context) throws ProcessorException {
		return new EntityMetadata(context);
	}

	private static final Set<Attribute> supportedAttributes = new HashSet<>();

	static {

		supportedAttributes.add(Attribute.FINAL);
	}

	private final ProcessorContext context;

	private String name;

	private String parent;

	private final List<String> interfaces = new ArrayList<>();

	private final Set<Attribute> attributes = new HashSet<>();

	private final Map<String, EntityPropertyMetadata> properties = new LinkedHashMap<>();

	private EntityMetadata(ProcessorContext context) throws ProcessorException {

		this.context = context;

		Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = context.getAnnotationMirror()
				.getElementValues();

		for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

			ExecutableElement key = entry.getKey();
			AnnotationValue value = entry.getValue();

			if (key.getSimpleName().contentEquals("name")) {

				name = ProcessorUtil.stringVisitor.visit(value);

				if (!ProcessorUtil.isJavaIdentifier(name)) {
					throw new EntityIllegalStateException(String.format("Name should be a valid class name identifier"),
							MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
				}

			} else if (key.getSimpleName().contentEquals("parent")) {

				parent = ProcessorUtil.stringVisitor.visit(value);

				if (!ProcessorUtil.isJavaIdentifier(parent)) {
					throw new EntityIllegalStateException(
							String.format("Parent should be a valid class name identifier"),
							MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
				}

			} else if (key.getSimpleName().contentEquals("interfaces")) {

				List<? extends AnnotationValue> annotationValues = value.accept(ProcessorUtil.arrayVisitor, null);

				interfaces.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					TypeElement typeElement = ProcessorUtil.typeElementVisitor.visit(ProcessorUtil.declaredTypeVisitor
							.visit(ProcessorUtil.typeVisitor.visit(annotationValue)).asElement());

					if (typeElement.getKind() != ElementKind.INTERFACE) {
						throw new EntityIllegalStateException(
								String.format("%s is not an interface type", typeElement.getQualifiedName().toString()),
								MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
					}

					interfaces.add(typeElement.getQualifiedName().toString());
				}

			} else if (key.getSimpleName().contentEquals("attributes")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				attributes.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					VariableElement variableElement = ProcessorUtil.enumVisitor.visit(annotationValue);

					Attribute attribute = Attribute.valueOf(variableElement.getSimpleName().toString());

					if (!supportedAttributes.contains(attribute)) {
						throw new EntityIllegalStateException(
								String.format("attribute %s is not allowed for the entity", attribute),
								MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
					}

					attributes.add(attribute);
				}

			} else if (key.getSimpleName().contentEquals("properties")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				properties.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					AnnotationMirror annotationMirror = ProcessorUtil.annotationVisitor.visit(annotationValue);

					EntityPropertyMetadata property = EntityPropertyMetadata
							.of(context.withAnnotationMirror(annotationMirror));

					if (properties.containsKey(property.getName())) {
						throw new EntityPropertyIllegalStateException(
								String.format("property %s is already defined for the %s", property.getName(), name),
								MessageContext.of(context.getElement(), annotationMirror, annotationValue));
					}

					properties.put(property.getName(), property);
				}
			}
		}

		if (TextUtil.isBlank(parent)) {
			parent = TypeUtil.BASE_ENTITY_TYPE_NAME;
		}

		if (TextUtil.isBlank(name)) {
			throw new EntityPropertyIllegalStateException(String.format("Name of the entity is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}

		if (TextUtil.isBlank(parent)) {
			throw new EntityPropertyIllegalStateException(String.format("Parent of the entity is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}
	}

	public ProcessorContext getContext() {
		return context;
	}

	public String getName() {
		return name;
	}

	public String getParent() {
		return parent;
	}

	public List<String> getInterfaces() {
		return interfaces;
	}

	public Map<String, EntityPropertyMetadata> getProperties() {
		return properties;
	}

	public boolean isFinal() {
		return attributes.contains(Attribute.FINAL);
	}

	@Override
	public String toString() {
		return "EntityMetadata [name=" + name + ", parent=" + parent + ", interfaces=" + interfaces + ", properties="
				+ properties + "]";
	}
}
