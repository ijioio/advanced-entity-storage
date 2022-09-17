package com.ijioio.aes.annotation.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.processing.Filer;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;

import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.annotation.processor.exception.EntityIllegalStateException;
import com.ijioio.aes.annotation.processor.exception.EntityPropertyIllegalStateException;
import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.util.ProcessorUtil;
import com.ijioio.aes.annotation.processor.util.TextUtil;
import com.ijioio.aes.annotation.processor.util.TypeUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class EntityMetadata {

	public static EntityMetadata of(ProcessorContext context) throws ProcessorException {
		return new EntityMetadata(context);
	}

	private final ProcessorContext context;

	private String name;

	private String parent;

	private final List<String> interfaces = new ArrayList<>();

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
					throw new EntityIllegalStateException(String.format("name should be a valid class name identifier"),
							MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
				}

			} else if (key.getSimpleName().contentEquals("parent")) {

				parent = ProcessorUtil.stringVisitor.visit(value);

				if (!ProcessorUtil.isJavaIdentifier(parent)) {
					throw new EntityIllegalStateException(
							String.format("parent should be a valid class name identifier"),
							MessageContext.of(context.getElement(), context.getAnnotationMirror(), value));
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

	public Map<String, EntityPropertyMetadata> getProperties() {
		return properties;
	}

	public void generateCode(Filer filer) throws ProcessorException {

		System.out.println("generate!!!");

		try {

			List<FieldSpec> fields = new ArrayList<>();
			List<MethodSpec> methods = new ArrayList<>();

			for (EntityPropertyMetadata property : properties.values()) {

				TypeName propertyType = getType(property.getType(), property.getParameters());

				FieldSpec.builder(propertyType, property.getName()).addModifiers(Modifier.PRIVATE).build();

				fields.add(FieldSpec.builder(propertyType, property.getName()).addModifiers(Modifier.PRIVATE).build());

				methods.add(MethodSpec.methodBuilder(String.format("set%s", TextUtil.capitalize(property.getName())))
						.addModifiers(Modifier.PUBLIC).addParameter(propertyType, property.getName())
						.addStatement("this.$L = $L", property.getName(), property.getName()).build());
				methods.add(MethodSpec.methodBuilder(String.format("get%s", TextUtil.capitalize(property.getName())))
						.addModifiers(Modifier.PUBLIC).returns(propertyType)
						.addStatement("return $L", property.getName()).build());
			}

			System.out.println("generate -> fields -> " + fields.size());
			System.out.println("generate -> methods -> " + methods.size());

			ClassName className = ClassName.bestGuess(name);
			ClassName parentClassName = ClassName.bestGuess(parent);

			TypeSpec type = TypeSpec.classBuilder(className.simpleName()).superclass(parentClassName)
					.addModifiers(Modifier.PUBLIC)
					// .addAnnotation(AnnotationSpec.builder(tableClassName).addMember("name",
					// "$S", tableName).build())
					.addFields(fields).addMethods(methods).build();

			System.out.println("generate -> type -> " + type);

			JavaFile javaFile = JavaFile.builder(className.packageName(), type).build();

			try (Writer writer = filer.createSourceFile(name).openWriter()) {
				System.out.println("generate -> before write");
				javaFile.writeTo(writer);
				System.out.println("generate -> after write");
			}

		} catch (IOException e) {
			System.out.println("error!!!");
			e.printStackTrace();
			// throw new EntityTypeCodeGenerationException()
		}
	}

	private TypeName getType(TypeMetadata type, List<TypeMetadata> parameters) {

		if (parameters.size() > 0) {

			return ParameterizedTypeName.get((ClassName) getType(type.getName(), false), parameters.stream()
					.map(item -> getType(item.getName(), item.isReference())).toArray(size -> new TypeName[size]));

		}

		return getType(type.getName(), type.isReference());
	}

	private TypeName getType(String name, boolean reference) {

		TypeName typeName;

		if (name.equals(Type.BOOLEAN)) {
			typeName = TypeName.BOOLEAN;
		} else if (name.equals(Type.BYTE)) {
			typeName = TypeName.BYTE;
		} else if (name.equals(Type.SHORT)) {
			typeName = TypeName.SHORT;
		} else if (name.equals(Type.INT)) {
			typeName = TypeName.INT;
		} else if (name.equals(Type.LONG)) {
			typeName = TypeName.LONG;
		} else if (name.equals(Type.CHAR)) {
			typeName = TypeName.CHAR;
		} else if (name.equals(Type.FLOAT)) {
			typeName = TypeName.FLOAT;
		} else if (name.equals(Type.DOUBLE)) {
			typeName = TypeName.DOUBLE;
		} else if (name.equals(Type.STRING)) {
			typeName = ClassName.get(String.class);
		} else if (name.equals(Type.LIST)) {
			typeName = ClassName.get(List.class);
		} else if (name.equals(Type.SET)) {
			typeName = ClassName.get(Set.class);
		} else if (name.equals(Type.MAP)) {
			typeName = ClassName.get(Map.class);
		} else if (name.equals(Type.REFERENCE)) {
			typeName = ClassName.bestGuess(TypeUtil.ENTITY_REFERENCE_TYPE_NAME);
		} else {
			typeName = ClassName.bestGuess(name);
		}

		return reference ? ParameterizedTypeName.get(ClassName.bestGuess(TypeUtil.ENTITY_REFERENCE_TYPE_NAME), typeName)
				: typeName;
	}

	@Override
	public String toString() {
		return "EntityMetadata [name=" + name + ", parent=" + parent + ", interfaces=" + interfaces + ", properties="
				+ properties + "]";
	}
}
