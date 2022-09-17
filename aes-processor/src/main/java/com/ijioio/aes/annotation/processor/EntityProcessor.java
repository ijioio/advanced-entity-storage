package com.ijioio.aes.annotation.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.annotation.processor.exception.CodeGenerationException;
import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.util.TextUtil;
import com.ijioio.aes.annotation.processor.util.TypeUtil;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

public class EntityProcessor extends AbstractProcessor {

	protected Messager messager;

	@Override
	public synchronized void init(ProcessingEnvironment processingEnvironment) {

		super.init(processingEnvironment);

		messager = Messager.of(processingEnvironment.getMessager());
	}

	@Override
	public Set<String> getSupportedAnnotationTypes() {
		return Collections.singleton(Entity.class.getName());
	}

	@Override
	public SourceVersion getSupportedSourceVersion() {
		return SourceVersion.RELEASE_8;
	}

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

		try {

			for (TypeElement annotation : annotations) {

				Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

				for (Element annotatedElement : annotatedElements) {

					AnnotationMirror annotatinMirror = annotatedElement.getAnnotationMirrors().stream()
							.filter(item -> item.getAnnotationType().toString().equals(Entity.class.getCanonicalName()))
							.findFirst().orElse(null);

					ProcessorContext processorContext = ProcessorContext.of(annotatedElement, annotatinMirror);

					EntityMetadata entity = EntityMetadata.of(processorContext);

					messager.debug("entity -> " + entity);

					generateCode(entity, processingEnv.getFiler());
				}
			}

		} catch (ProcessorException e) {
			messager.error(e.getMessage(), e.getContext());
		}

		return true;
	}

	public void generateCode(EntityMetadata entity, Filer filer) throws ProcessorException {

		try {

			List<FieldSpec> fields = new ArrayList<>();
			List<MethodSpec> methods = new ArrayList<>();

			Collection<EntityPropertyMetadata> properties = entity.getProperties().values();

			for (EntityPropertyMetadata property : properties) {

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

			ClassName className = ClassName.bestGuess(entity.getName());
			ClassName parentClassName = ClassName.bestGuess(entity.getParent());
			List<ClassName> interfaceNames = entity.getInterfaces().stream().map(item -> ClassName.bestGuess(item))
					.collect(Collectors.toList());

			TypeSpec type = TypeSpec.classBuilder(className.simpleName()).superclass(parentClassName)
					.addSuperinterfaces(interfaceNames).addModifiers(Modifier.PUBLIC)
					// .addAnnotation(AnnotationSpec.builder(tableClassName).addMember("name",
					// "$S", tableName).build())
					.addFields(fields).addMethods(methods).build();

			JavaFile javaFile = JavaFile.builder(className.packageName(), type).build();

			try (Writer writer = filer.createSourceFile(entity.getName()).openWriter()) {
				javaFile.writeTo(writer);
			}

		} catch (IOException e) {
			throw new CodeGenerationException(
					String.format("Failed to generate code for %s: %s", entity.getName(), e.getMessage()));
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
}
