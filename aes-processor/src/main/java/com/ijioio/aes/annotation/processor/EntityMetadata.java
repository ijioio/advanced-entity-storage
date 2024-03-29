package com.ijioio.aes.annotation.processor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import com.ijioio.aes.annotation.Attribute;
import com.ijioio.aes.annotation.processor.exception.EntityIllegalStateException;
import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.util.ProcessorUtil;
import com.ijioio.aes.annotation.processor.util.TextUtil;
import com.ijioio.aes.annotation.processor.util.TypeUtil;

public class EntityMetadata {

	public static EntityMetadata of(ProcessingEnvironment environment, ProcessorContext context)
			throws ProcessorException {
		return new EntityMetadata(environment, context);
	}

	private static final Set<Attribute> supportedAttributes = new HashSet<>();

	static {

		supportedAttributes.add(Attribute.FINAL);
	}

	private String name;

	private String parent;

	private final List<String> interfaces = new ArrayList<>();

	private final Set<Attribute> attributes = new HashSet<>();

	private final Map<String, TypeMetadata> types = new LinkedHashMap<>();

	private final Map<String, EntityPropertyMetadata> properties = new LinkedHashMap<>();

	private final Map<String, EntityIndexMetadata> indexes = new LinkedHashMap<>();

	private EntityMetadata(ProcessingEnvironment environment, ProcessorContext context) throws ProcessorException {

		Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = context.getAnnotationMirror()
				.getElementValues();

		for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

			ExecutableElement key = entry.getKey();
			AnnotationValue value = entry.getValue();

			if (key.getSimpleName().contentEquals("name")) {

				name = ProcessorUtil.stringVisitor.visit(value);

				if (!TypeUtil.isValidIdentifier(name)) {
					throw new EntityIllegalStateException(String.format("Name should be a valid class name identifier"),
							MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
				}

			} else if (key.getSimpleName().contentEquals("parent")) {

				parent = ProcessorUtil.stringVisitor.visit(value);

				if (!TypeUtil.isValidIdentifier(parent)) {
					throw new EntityIllegalStateException(
							String.format("Parent should be a valid class name identifier"),
							MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
				}

				TypeElement parentTypeElement = environment.getElementUtils().getTypeElement(parent);

				// It can be null in cases parent is also generated type
				if (parentTypeElement != null) {

					if (parentTypeElement.getKind() != ElementKind.CLASS) {
						throw new EntityIllegalStateException(String.format("Parent should be of class type"),
								MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
					}

					if (!ProcessorUtil.isSubtype(parentTypeElement.asType(), TypeUtil.BASE_ENTITY_TYPE_NAME)
							.orElse(Boolean.TRUE).booleanValue()) {
						throw new EntityIllegalStateException(
								String.format("Parent should be a subclass of %s", TypeUtil.BASE_ENTITY_TYPE_NAME),
								MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
					}
				}

			} else if (key.getSimpleName().contentEquals("interfaces")) {

				List<? extends AnnotationValue> annotationValues = value.accept(ProcessorUtil.arrayVisitor, null);

				interfaces.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					TypeElement typeElement = ProcessorUtil.typeElementVisitor.visit(ProcessorUtil.declaredTypeVisitor
							.visit(ProcessorUtil.typeVisitor.visit(annotationValue)).asElement());

					if (typeElement.getKind() != ElementKind.INTERFACE) {
						throw new EntityIllegalStateException(
								String.format("Type %s is not an interface type",
										typeElement.getQualifiedName().toString()),
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
								String.format("Attribute %s is not allowed for the entity", attribute),
								MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
					}

					attributes.add(attribute);
				}

			} else if (key.getSimpleName().contentEquals("types")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				types.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					AnnotationMirror annotationMirror = ProcessorUtil.annotationVisitor.visit(annotationValue);

					TypeMetadata type = TypeMetadata.of(environment, context.withAnnotationMirror(annotationMirror));

					if (types.containsKey(type.getName())) {
						throw new EntityIllegalStateException(
								String.format("Type %s is already defined for the entity", type.getName()),
								MessageContext.of(context.getElement(), annotationMirror, annotationValue));
					}

					types.put(type.getName(), type);
				}

			} else if (key.getSimpleName().contentEquals("properties")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				properties.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					AnnotationMirror annotationMirror = ProcessorUtil.annotationVisitor.visit(annotationValue);

					EntityPropertyMetadata property = EntityPropertyMetadata.of(environment,
							context.withAnnotationMirror(annotationMirror));

					if (properties.containsKey(property.getName())) {
						throw new EntityIllegalStateException(
								String.format("Property %s is already defined for the entity", property.getName()),
								MessageContext.of(context.getElement(), annotationMirror, annotationValue));
					}

					properties.put(property.getName(), property);
				}

			} else if (key.getSimpleName().contentEquals("indexes")) {

				List<? extends AnnotationValue> annotationValues = ProcessorUtil.arrayVisitor.visit(value);

				indexes.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					AnnotationMirror annotationMirror = ProcessorUtil.annotationVisitor.visit(annotationValue);

					EntityIndexMetadata index = EntityIndexMetadata.of(environment,
							context.withAnnotationMirror(annotationMirror));

					if (indexes.containsKey(index.getName())) {
						throw new EntityIllegalStateException(
								String.format("Index %s is already defined for the entity", index.getName()),
								MessageContext.of(context.getElement(), annotationMirror, annotationValue));
					}

					indexes.put(index.getName(), index);
				}
			}
		}

		if (TextUtil.isBlank(parent)) {
			parent = TypeUtil.BASE_ENTITY_TYPE_NAME;
		}

		if (TextUtil.isBlank(name)) {
			throw new EntityIllegalStateException(String.format("Name of the entity is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}

		if (TextUtil.isBlank(parent)) {
			throw new EntityIllegalStateException(String.format("Parent of the entity is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}
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

	public Map<String, TypeMetadata> getTypes() {
		return types;
	}

	public Map<String, EntityIndexMetadata> getIndexes() {
		return indexes;
	}

	public boolean isFinal() {
		return attributes.contains(Attribute.FINAL);
	}

	@Override
	public String toString() {
		return "EntityMetadata [name=" + name + ", parent=" + parent + ", interfaces=" + interfaces + ", attributes="
				+ attributes + ", types=" + types + ", properties=" + properties + ", indexes=" + indexes + "]";
	}
}
