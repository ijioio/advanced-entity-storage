package com.ijioio.aes.annotation.processor;

import java.io.IOException;
import java.io.Writer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
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

				if (property.isFinal()) {

					TypeName propertyImplementationType = getImplementationType(property.getType(),
							property.getParameters());

					fields.add(FieldSpec.builder(propertyType, property.getName())
							.addModifiers(Modifier.PRIVATE, Modifier.FINAL)
							.initializer("new $T()", propertyImplementationType).build());

				} else {

					fields.add(
							FieldSpec.builder(propertyType, property.getName()).addModifiers(Modifier.PRIVATE).build());

					methods.add(
							MethodSpec.methodBuilder(String.format("set%s", TextUtil.capitalize(property.getName())))
									.addModifiers(Modifier.PUBLIC).addParameter(propertyType, property.getName())
									.addStatement("this.$L = $L", property.getName(), property.getName()).build());
				}

				if (propertyType == TypeName.BOOLEAN) {
					methods.add(MethodSpec.methodBuilder(String.format("is%s", TextUtil.capitalize(property.getName())))
							.addModifiers(Modifier.PUBLIC).returns(propertyType)
							.addStatement("return $L", property.getName()).build());
				} else {
					methods.add(
							MethodSpec.methodBuilder(String.format("get%s", TextUtil.capitalize(property.getName())))
									.addModifiers(Modifier.PUBLIC).returns(propertyType)
									.addStatement("return $L", property.getName()).build());
				}
			}

			methods.add(generateWrite(entity));
			methods.add(generateRead(entity));

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

	public MethodSpec generateWrite(EntityMetadata entity) {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.addStatement("$T<$T, $T> writers = new $T<>(super.getWriters(context, handler))", Map.class,
				String.class, ClassName.bestGuess(TypeUtil.SERIALIZATION_WRITER_TYPE_NAME), LinkedHashMap.class);

		Collection<EntityPropertyMetadata> properties = entity.getProperties().values();

		for (EntityPropertyMetadata property : properties) {
			codeBlockBuilder.addStatement("writers.put($S, () -> handler.write(context, $S, $L))", property.getName(),
					property.getName(), property.getName());
		}

		codeBlockBuilder.addStatement("return writers");

		List<AnnotationSpec> annotations = new ArrayList<>();

		annotations.add(AnnotationSpec.builder(ClassName.get(Override.class)).build());

		List<ParameterSpec> parameters = new ArrayList<>();

		parameters.add(ParameterSpec.builder(ClassName.bestGuess(TypeUtil.SERIALIZATION_CONTEXT_TYPE_NAME), "context")
				.build());
		parameters.add(ParameterSpec.builder(ClassName.bestGuess(TypeUtil.SERIALIZATION_HANDLER_TYPE_NAME), "handler")
				.build());

		CodeBlock codeBlock = codeBlockBuilder.build();

		MethodSpec method = MethodSpec.methodBuilder("getWriters").addAnnotations(annotations)
				.addModifiers(Modifier.PUBLIC)
				.returns(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class),
						ClassName.bestGuess(TypeUtil.SERIALIZATION_WRITER_TYPE_NAME)))
				.addParameters(parameters).addCode(codeBlock).build();

		return method;
	}

	public MethodSpec generateRead(EntityMetadata entity) {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.addStatement("$T<$T, $T> readers = new $T<>(super.getReaders(context, handler))", Map.class,
				String.class, ClassName.bestGuess(TypeUtil.SERIALIZATION_READER_TYPE_NAME), LinkedHashMap.class);

		Collection<EntityPropertyMetadata> properties = entity.getProperties().values();

		for (EntityPropertyMetadata property : properties) {

			if (property.isFinal()) {
				codeBlockBuilder.addStatement("readers.put($S, () -> handler.read(context, $L))", property.getName(),
						property.getName());
			} else {
				codeBlockBuilder.addStatement("readers.put($S, () -> $L = handler.read(context, $L))",
						property.getName(), property.getName(), property.getName());
			}
		}

		codeBlockBuilder.addStatement("return readers");

		List<AnnotationSpec> annotations = new ArrayList<>();

		annotations.add(AnnotationSpec.builder(ClassName.get(Override.class)).build());

		List<ParameterSpec> parameters = new ArrayList<>();

		parameters.add(ParameterSpec.builder(ClassName.bestGuess(TypeUtil.SERIALIZATION_CONTEXT_TYPE_NAME), "context")
				.build());
		parameters.add(ParameterSpec.builder(ClassName.bestGuess(TypeUtil.SERIALIZATION_HANDLER_TYPE_NAME), "handler")
				.build());

		CodeBlock codeBlock = codeBlockBuilder.build();

		MethodSpec method = MethodSpec.methodBuilder("getReaders").addAnnotations(annotations)
				.addModifiers(Modifier.PUBLIC)
				.returns(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class),
						ClassName.bestGuess(TypeUtil.SERIALIZATION_READER_TYPE_NAME)))
				.addParameters(parameters).addCode(codeBlock).build();

		return method;
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

		if (name.equals(Type.BOOLEAN) || name.equals(boolean.class.getName())) {
			typeName = TypeName.BOOLEAN;
		} else if (name.equals(Type.CHAR) || name.equals(char.class.getName())) {
			typeName = TypeName.CHAR;
		} else if (name.equals(Type.BYTE) || name.equals(byte.class.getName())) {
			typeName = TypeName.BYTE;
		} else if (name.equals(Type.SHORT) || name.equals(short.class.getName())) {
			typeName = TypeName.SHORT;
		} else if (name.equals(Type.INT) || name.equals(int.class.getName())) {
			typeName = TypeName.INT;
		} else if (name.equals(Type.LONG) || name.equals(long.class.getName())) {
			typeName = TypeName.LONG;
		} else if (name.equals(Type.FLOAT) || name.equals(float.class.getName())) {
			typeName = TypeName.FLOAT;
		} else if (name.equals(Type.DOUBLE) || name.equals(double.class.getName())) {
			typeName = TypeName.DOUBLE;
		} else if (name.equals(Type.BYTE_ARRAY) || name.equals(byte[].class.getName())) {
			typeName = ArrayTypeName.of(TypeName.BYTE);
		} else if (name.equals(Type.STRING) || name.equals(String.class.getName())) {
			typeName = ClassName.get(String.class);
		} else if (name.equals(Type.INSTANT) || name.equals(Instant.class.getName())) {
			typeName = ClassName.get(Instant.class);
		} else if (name.equals(Type.LOCAL_DATE_TIME) || name.equals(LocalDateTime.class.getName())) {
			typeName = ClassName.get(LocalDateTime.class);
		} else if (name.equals(Type.LOCAL_DATE) || name.equals(LocalDate.class.getName())) {
			typeName = ClassName.get(LocalDate.class);
		} else if (name.equals(Type.LOCAL_TIME) || name.equals(LocalTime.class.getName())) {
			typeName = ClassName.get(LocalTime.class);
		} else if (name.equals(Type.LIST) || name.equals(List.class.getName())) {
			typeName = ClassName.get(List.class);
		} else if (name.equals(Type.SET) || name.equals(Set.class.getName())) {
			typeName = ClassName.get(Set.class);
		} else if (name.equals(Type.MAP) || name.equals(Map.class.getName())) {
			typeName = ClassName.get(Map.class);
		} else {
			typeName = ClassName.bestGuess(name);
		}

		return reference ? ParameterizedTypeName.get(ClassName.bestGuess(TypeUtil.ENTITY_REFERENCE_TYPE_NAME), typeName)
				: typeName;
	}

	private TypeName getImplementationType(TypeMetadata type, List<TypeMetadata> parameters) {

		if (parameters.size() > 0) {
			return ParameterizedTypeName.get((ClassName) getImplementationType(type.getName(), false),
					parameters.stream().map(item -> getType(item.getName(), item.isReference()))
							.toArray(size -> new TypeName[size]));
		}

		return getImplementationType(type.getName(), type.isReference());
	}

	private TypeName getImplementationType(String name, boolean reference) {

		TypeName typeName;

		if (name.equals(Type.BOOLEAN) || name.equals(boolean.class.getName())) {
			typeName = TypeName.BOOLEAN;
		} else if (name.equals(Type.CHAR) || name.equals(char.class.getName())) {
			typeName = TypeName.CHAR;
		} else if (name.equals(Type.BYTE) || name.equals(byte.class.getName())) {
			typeName = TypeName.BYTE;
		} else if (name.equals(Type.SHORT) || name.equals(short.class.getName())) {
			typeName = TypeName.SHORT;
		} else if (name.equals(Type.INT) || name.equals(int.class.getName())) {
			typeName = TypeName.INT;
		} else if (name.equals(Type.LONG) || name.equals(long.class.getName())) {
			typeName = TypeName.LONG;
		} else if (name.equals(Type.FLOAT) || name.equals(float.class.getName())) {
			typeName = TypeName.FLOAT;
		} else if (name.equals(Type.DOUBLE) || name.equals(double.class.getName())) {
			typeName = TypeName.DOUBLE;
		} else if (name.equals(Type.BYTE_ARRAY) || name.equals(byte[].class.getName())) {
			typeName = ArrayTypeName.of(TypeName.BYTE);
		} else if (name.equals(Type.STRING) || name.equals(String.class.getName())) {
			typeName = ClassName.get(String.class);
		} else if (name.equals(Type.INSTANT) || name.equals(Instant.class.getName())) {
			typeName = ClassName.get(Instant.class);
		} else if (name.equals(Type.LOCAL_DATE) || name.equals(LocalDate.class.getName())) {
			typeName = ClassName.get(LocalDate.class);
		} else if (name.equals(Type.LOCAL_TIME) || name.equals(LocalTime.class.getName())) {
			typeName = ClassName.get(LocalTime.class);
		} else if (name.equals(Type.LOCAL_DATE_TIME) || name.equals(LocalDateTime.class.getName())) {
			typeName = ClassName.get(LocalDateTime.class);
		} else if (name.equals(Type.LIST) || name.equals(List.class.getName())) {
			typeName = ClassName.get(ArrayList.class);
		} else if (name.equals(Type.SET) || name.equals(Set.class.getName())) {
			typeName = ClassName.get(LinkedHashSet.class);
		} else if (name.equals(Type.MAP) || name.equals(Map.class.getName())) {
			typeName = ClassName.get(LinkedHashMap.class);
		} else {
			typeName = ClassName.bestGuess(name);
		}

		return reference ? ParameterizedTypeName.get(ClassName.bestGuess(TypeUtil.ENTITY_REFERENCE_TYPE_NAME), typeName)
				: typeName;
	}
}
