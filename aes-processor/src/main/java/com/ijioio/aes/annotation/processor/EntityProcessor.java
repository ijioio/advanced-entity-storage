package com.ijioio.aes.annotation.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
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
import com.ijioio.aes.annotation.processor.exception.CodeGenerationException;
import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.util.CodeGenTypeUtil;
import com.ijioio.aes.annotation.processor.util.CodeGenTypeUtil.CodeGenTypeHandler;
import com.ijioio.aes.annotation.processor.util.TextUtil;
import com.ijioio.aes.annotation.processor.util.TypeUtil;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

public class EntityProcessor extends AbstractProcessor {

	protected ProcessingEnvironment environment;

	protected Messager messager;

	@Override
	public synchronized void init(ProcessingEnvironment environment) {

		super.init(environment);

		this.environment = environment;

		messager = Messager.of(environment.getMessager());
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

					AnnotationMirror annotationMirror = annotatedElement.getAnnotationMirrors().stream()
							.filter(item -> item.getAnnotationType().toString().equals(Entity.class.getCanonicalName()))
							.findFirst().orElse(null);

					ProcessorContext context = ProcessorContext.of(annotatedElement, annotationMirror);

					EntityMetadata entity = EntityMetadata.of(environment, context);

					messager.debug("entity -> " + entity);

					generateCode(entity, processingEnv.getFiler());
				}
			}

		} catch (ProcessorException e) {
			messager.error(e.getMessage(), e.getContext());
		}

		return true;
	}

	private void generateCode(EntityMetadata entity, Filer filer) throws ProcessorException {

		try {

			generateEntity(entity, filer);

			for (EntityIndexMetadata index : entity.getIndexes().values()) {

				generateIndex(entity, index, filer);
				generateIndexData(entity, index, filer);
			}

		} catch (IOException e) {
			throw new CodeGenerationException(
					String.format("Failed to generate code for %s: %s", entity.getName(), e.getMessage()));
		}
	}

	private void generateEntity(EntityMetadata entity, Filer filer) throws IOException {

		List<FieldSpec> fields = new ArrayList<>();
		List<MethodSpec> methods = new ArrayList<>();

		Collection<EntityPropertyMetadata> properties = entity.getProperties().values();

		for (EntityPropertyMetadata property : properties) {

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property.getType(), property.getParameters());

			if (handler.isBoolean()) {
				methods.add(MethodSpec.methodBuilder(String.format("is%s", TextUtil.capitalize(property.getName())))
						.addModifiers(Modifier.PUBLIC).returns(handler.getParameterizedType())
						.addStatement("return $L", property.getName()).build());
			} else {
				methods.add(MethodSpec.methodBuilder(String.format("get%s", TextUtil.capitalize(property.getName())))
						.addModifiers(Modifier.PUBLIC).returns(handler.getParameterizedType())
						.addStatement("return $L", property.getName()).build());
			}

			if (property.isFinal()) {

				fields.add(FieldSpec.builder(handler.getParameterizedType(), property.getName())
						.addModifiers(Modifier.PRIVATE, Modifier.FINAL)
						.initializer("new $T()", handler.getParameterizedImplementationType()).build());

			} else {

				fields.add(FieldSpec.builder(handler.getParameterizedType(), property.getName())
						.addModifiers(Modifier.PRIVATE).build());

				methods.add(MethodSpec.methodBuilder(String.format("set%s", TextUtil.capitalize(property.getName())))
						.addModifiers(Modifier.PUBLIC).addParameter(handler.getParameterizedType(), property.getName())
						.addStatement("this.$L = $L", property.getName(), property.getName()).build());
			}
		}

		methods.add(generateWrite(entity));
		methods.add(generateRead(entity));

		ClassName className = ClassName.bestGuess(entity.getName());
		ClassName parentClassName = ClassName.bestGuess(entity.getParent());
		List<ClassName> interfaceNames = entity.getInterfaces().stream().map(item -> ClassName.bestGuess(item))
				.collect(Collectors.toList());

		List<Modifier> modifiers = new ArrayList<>();

		modifiers.add(Modifier.PUBLIC);

		if (entity.isFinal()) {
			modifiers.add(Modifier.FINAL);
		}

		List<AnnotationSpec> annotations = new ArrayList<>();

		TypeSpec type = TypeSpec.classBuilder(className.simpleName()).superclass(parentClassName)
				.addSuperinterfaces(interfaceNames).addModifiers(modifiers.toArray(new Modifier[modifiers.size()]))
				.addAnnotations(annotations).addFields(fields).addMethods(methods).build();

		JavaFile javaFile = JavaFile.builder(className.packageName(), type).build();

		try (Writer writer = filer.createSourceFile(entity.getName()).openWriter()) {
			javaFile.writeTo(writer);
		}
	}

	private MethodSpec generateWrite(EntityMetadata entity) {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("$T<$T, $T> writers = new $T<>(super.getWriters(context, handler))", Map.class,
				String.class, ClassName.bestGuess(TypeUtil.SERIALIZATION_WRITER_TYPE_NAME), LinkedHashMap.class);

		Collection<EntityPropertyMetadata> properties = entity.getProperties().values();

		if (properties.size() > 0) {
			codeBlockBuilder.add("\n");
		}

		for (EntityPropertyMetadata property : properties) {
			codeBlockBuilder.addStatement("writers.put($S, () -> handler.write(context, $S, $L))", property.getName(),
					property.getName(), property.getName());
		}

		codeBlockBuilder.add("\n");
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

	private MethodSpec generateRead(EntityMetadata entity) {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("$T<$T, $T> readers = new $T<>(super.getReaders(context, handler))", Map.class,
				String.class, ClassName.bestGuess(TypeUtil.SERIALIZATION_READER_TYPE_NAME), LinkedHashMap.class);

		Collection<EntityPropertyMetadata> properties = entity.getProperties().values();

		if (properties.size() > 0) {
			codeBlockBuilder.add("\n");
		}

		for (EntityPropertyMetadata property : properties) {

			if (property.isFinal()) {
				codeBlockBuilder.addStatement("readers.put($S, () -> handler.read(context, $L))", property.getName(),
						property.getName());
			} else {
				codeBlockBuilder.addStatement("readers.put($S, () -> $L = handler.read(context, $L))",
						property.getName(), property.getName(), property.getName());
			}
		}

		codeBlockBuilder.add("\n");
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

	private void generateIndex(EntityMetadata entity, EntityIndexMetadata index, Filer filer) throws IOException {

		List<FieldSpec> fields = new ArrayList<>();
		List<MethodSpec> methods = new ArrayList<>();

		Collection<EntityIndexPropertyMetadata> properties = index.getProperties().values();

		for (EntityIndexPropertyMetadata property : properties) {

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property.getType(), property.getParameters());

			fields.add(FieldSpec.builder(handler.getParameterizedType(), property.getName())
					.addModifiers(Modifier.PRIVATE).build());

			if (handler.isBoolean()) {
				methods.add(MethodSpec.methodBuilder(String.format("is%s", TextUtil.capitalize(property.getName())))
						.addModifiers(Modifier.PUBLIC).returns(handler.getParameterizedType())
						.addStatement("return $L", property.getName()).build());
			} else {
				methods.add(MethodSpec.methodBuilder(String.format("get%s", TextUtil.capitalize(property.getName())))
						.addModifiers(Modifier.PUBLIC).returns(handler.getParameterizedType())
						.addStatement("return $L", property.getName()).build());
			}

			methods.add(MethodSpec.methodBuilder(String.format("set%s", TextUtil.capitalize(property.getName())))
					.addModifiers(Modifier.PUBLIC).addParameter(handler.getParameterizedType(), property.getName())
					.addStatement("this.$L = $L", property.getName(), property.getName()).build());
		}

		methods.add(generateGetColumnProviders(entity, index));
		methods.add(generateGetWriters(entity, index));
		methods.add(generateGetReaders(entity, index));
		methods.add(generateGetProperties());

		TypeSpec propertiesType = generateProperties(entity, index);

		ClassName className = ClassName.bestGuess(index.getName());
		TypeName parentTypeName = ParameterizedTypeName.get(ClassName.bestGuess(TypeUtil.BASE_ENTITY_INDEX_TYPE_NAME),
				ClassName.bestGuess(entity.getName()));

		List<Modifier> modifiers = new ArrayList<>();

		modifiers.add(Modifier.PUBLIC);
		modifiers.add(Modifier.FINAL);

		List<AnnotationSpec> annotations = new ArrayList<>();

		TypeSpec type = TypeSpec.classBuilder(className.simpleName()).superclass(parentTypeName)
				.addModifiers(modifiers.toArray(new Modifier[modifiers.size()])).addAnnotations(annotations)
				.addType(propertiesType).addFields(fields).addMethods(methods).build();

		JavaFile javaFile = JavaFile.builder(className.packageName(), type).build();

		try (Writer writer = filer.createSourceFile(index.getName()).openWriter()) {
			javaFile.writeTo(writer);
		}
	}

	private MethodSpec generateGetColumnProviders(EntityMetadata entity, EntityIndexMetadata index) {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement(
				"$T<$T, $T> columnProviders = new $T<>(super.getColumnProviders(context, handler))", Map.class,
				String.class, ClassName.bestGuess(TypeUtil.PERSISTENCE_COLUMN_PROVIDER_TYPE_NAME), LinkedHashMap.class);

		Collection<EntityIndexPropertyMetadata> properties = index.getProperties().values();

		if (properties.size() > 0) {
			codeBlockBuilder.add("\n");
		}

		for (EntityIndexPropertyMetadata property : properties) {

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property.getType(), property.getParameters());

			codeBlockBuilder.addStatement("columnProviders.put($S, () -> handler.getColumns(context, $S, $T.class))",
					property.getName(), property.getName(), handler.getType());
		}

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("return columnProviders");

		List<AnnotationSpec> annotations = new ArrayList<>();

		annotations.add(AnnotationSpec.builder(ClassName.get(Override.class)).build());

		List<ParameterSpec> parameters = new ArrayList<>();

		parameters.add(
				ParameterSpec.builder(ClassName.bestGuess(TypeUtil.PERSISTENCE_CONTEXT_TYPE_NAME), "context").build());
		parameters.add(
				ParameterSpec.builder(ClassName.bestGuess(TypeUtil.PERSISTENCE_HANDLER_TYPE_NAME), "handler").build());

		CodeBlock codeBlock = codeBlockBuilder.build();

		MethodSpec method = MethodSpec.methodBuilder("getColumnProviders").addAnnotations(annotations)
				.addModifiers(Modifier.PUBLIC)
				.returns(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class),
						ClassName.bestGuess(TypeUtil.PERSISTENCE_COLUMN_PROVIDER_TYPE_NAME)))
				.addParameters(parameters).addCode(codeBlock).build();

		return method;
	}

	private MethodSpec generateGetWriters(EntityMetadata entity, EntityIndexMetadata index) {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("$T<$T, $T> writers = new $T<>(super.getWriters(context, handler))", Map.class,
				String.class, ClassName.bestGuess(TypeUtil.PERSISTENCE_WRITER_TYPE_NAME), LinkedHashMap.class);

		Collection<EntityIndexPropertyMetadata> properties = index.getProperties().values();

		if (properties.size() > 0) {
			codeBlockBuilder.add("\n");
		}

		for (EntityIndexPropertyMetadata property : properties) {

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property.getType(), property.getParameters());

			codeBlockBuilder.addStatement("writers.put($S, () -> handler.write(context, $S, $L, $T.class))",
					property.getName(), property.getName(), property.getName(), handler.getType());
		}

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("return writers");

		List<AnnotationSpec> annotations = new ArrayList<>();

		annotations.add(AnnotationSpec.builder(ClassName.get(Override.class)).build());

		List<ParameterSpec> parameters = new ArrayList<>();

		parameters.add(
				ParameterSpec.builder(ClassName.bestGuess(TypeUtil.PERSISTENCE_CONTEXT_TYPE_NAME), "context").build());
		parameters.add(
				ParameterSpec.builder(ClassName.bestGuess(TypeUtil.PERSISTENCE_HANDLER_TYPE_NAME), "handler").build());

		CodeBlock codeBlock = codeBlockBuilder.build();

		MethodSpec method = MethodSpec.methodBuilder("getWriters").addAnnotations(annotations)
				.addModifiers(Modifier.PUBLIC)
				.returns(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class),
						ClassName.bestGuess(TypeUtil.PERSISTENCE_WRITER_TYPE_NAME)))
				.addParameters(parameters).addCode(codeBlock).build();

		return method;
	}

	private MethodSpec generateGetReaders(EntityMetadata entity, EntityIndexMetadata index) {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("$T<$T, $T> readers = new $T<>(super.getReaders(context, handler))", Map.class,
				String.class, ClassName.bestGuess(TypeUtil.PERSISTENCE_READER_TYPE_NAME), LinkedHashMap.class);

		Collection<EntityIndexPropertyMetadata> properties = index.getProperties().values();

		if (properties.size() > 0) {
			codeBlockBuilder.add("\n");
		}

		for (EntityIndexPropertyMetadata property : properties) {

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property.getType(), property.getParameters());

//			if (property.isFinal()) {
//				codeBlockBuilder.addStatement("readers.put($S, () -> handler.read(context, $L))", property.getName(),
//						property.getName());
//			} else {
			codeBlockBuilder.addStatement("readers.put($S, () -> $L = handler.read(context, $S, $L, $T.class))",
					property.getName(), property.getName(), property.getName(), property.getName(), handler.getType());
//			}
		}

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("return readers");

		List<AnnotationSpec> annotations = new ArrayList<>();

		annotations.add(AnnotationSpec.builder(ClassName.get(Override.class)).build());

		List<ParameterSpec> parameters = new ArrayList<>();

		parameters.add(
				ParameterSpec.builder(ClassName.bestGuess(TypeUtil.PERSISTENCE_CONTEXT_TYPE_NAME), "context").build());
		parameters.add(
				ParameterSpec.builder(ClassName.bestGuess(TypeUtil.PERSISTENCE_HANDLER_TYPE_NAME), "handler").build());

		CodeBlock codeBlock = codeBlockBuilder.build();

		MethodSpec method = MethodSpec.methodBuilder("getReaders").addAnnotations(annotations)
				.addModifiers(Modifier.PUBLIC)
				.returns(ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class),
						ClassName.bestGuess(TypeUtil.PERSISTENCE_READER_TYPE_NAME)))
				.addParameters(parameters).addCode(codeBlock).build();

		return method;
	}

	private MethodSpec generateGetProperties() {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("$T properties = new $T<>(super.getProperties())",
				ParameterizedTypeName.get(ClassName.get(Collection.class), ParameterizedTypeName.get(
						ClassName.bestGuess(TypeUtil.PROPERTY_TYPE_NAME), WildcardTypeName.subtypeOf(TypeName.OBJECT))),
				ArrayList.class);
		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("properties.addAll($T.values)", ClassName.bestGuess("Properties"));
		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("return properties");

		List<AnnotationSpec> annotations = new ArrayList<>();

		annotations.add(AnnotationSpec.builder(ClassName.get(Override.class)).build());

		CodeBlock codeBlock = codeBlockBuilder.build();

		MethodSpec method = MethodSpec.methodBuilder("getProperties").addAnnotations(annotations)
				.addModifiers(Modifier.PUBLIC)
				.returns(
						ParameterizedTypeName
								.get(ClassName.get(Collection.class),
										ParameterizedTypeName.get(ClassName.bestGuess(TypeUtil.PROPERTY_TYPE_NAME),
												WildcardTypeName.subtypeOf(TypeName.OBJECT))))
				.addCode(codeBlock).build();

		return method;
	}

	private TypeSpec generateProperties(EntityMetadata entity, EntityIndexMetadata index) {

		List<FieldSpec> fields = new ArrayList<>();
		List<MethodSpec> methods = new ArrayList<>();

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.add("\n");

		Collection<EntityIndexPropertyMetadata> properties = index.getProperties().values();

		for (EntityIndexPropertyMetadata property : properties) {

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property.getType(), property.getParameters());

			fields.add(FieldSpec
					.builder(
							ParameterizedTypeName.get(ClassName.bestGuess(TypeUtil.PROPERTY_TYPE_NAME),
									handler.getType().isPrimitive() ? handler.getType().box() : handler.getType()),
							property.getName())
					.addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
					.initializer("$T.of($S, $T.class)", ClassName.bestGuess(TypeUtil.PROPERTY_TYPE_NAME),
							property.getName(),
							handler.getType().isPrimitive() ? handler.getType().box() : handler.getType())
					.build());

			codeBlockBuilder.addStatement("values.add($L)", property.getName());
		}

		fields.add(FieldSpec
				.builder(ParameterizedTypeName.get(ClassName.get(List.class),
						ParameterizedTypeName.get(ClassName.bestGuess(TypeUtil.PROPERTY_TYPE_NAME),
								WildcardTypeName.subtypeOf(TypeName.OBJECT))),
						"values")
				.addModifiers(Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
				.initializer("new $T<>()", ClassName.get(ArrayList.class)).build());

		ClassName className = ClassName.bestGuess("Properties");

		List<Modifier> modifiers = new ArrayList<>();

		modifiers.add(Modifier.PUBLIC);
		modifiers.add(Modifier.STATIC);
		modifiers.add(Modifier.FINAL);

		List<AnnotationSpec> annotations = new ArrayList<>();

		CodeBlock codeBlock = codeBlockBuilder.build();

		TypeSpec type = TypeSpec.classBuilder(className.simpleName())
				.addModifiers(modifiers.toArray(new Modifier[modifiers.size()])).addAnnotations(annotations)
				.addFields(fields).addStaticBlock(codeBlock).addMethods(methods).build();

		return type;
	}

	private void generateIndexData(EntityMetadata entity, EntityIndexMetadata index, Filer filer) throws IOException {

		List<FieldSpec> fields = new ArrayList<>();
		List<MethodSpec> methods = new ArrayList<>();

		Collection<EntityIndexPropertyMetadata> properties = index.getProperties().values();

		for (EntityIndexPropertyMetadata property : properties) {

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property.getType(), property.getParameters());

			if (handler.isReference()) {

				fields.add(FieldSpec.builder(ClassName.get(String.class), String.format("%sId", property.getName()))
						.addModifiers(Modifier.PRIVATE).build());
				fields.add(FieldSpec.builder(ClassName.get(String.class), String.format("%sType", property.getName()))
						.addModifiers(Modifier.PRIVATE).build());

				methods.add(MethodSpec.methodBuilder(String.format("get%s", TextUtil.capitalize(property.getName())))
						.addAnnotation(AnnotationSpec.builder(SuppressWarnings.class)
								.addMember("value", "$S", "unchecked").build())
						.addModifiers(Modifier.PUBLIC).returns(handler.getParameterizedType()).addCode("\n")
						.beginControlFlow("try")
						.addStatement("return $T.of($L, ($T) Class.forName($L))",
								ClassName.bestGuess(TypeUtil.ENTITY_REFERENCE_TYPE_NAME),
								String.format("%sId", property.getName()),
								ParameterizedTypeName.get(ClassName.get(Class.class), handler.getParameters().get(0)),
								String.format("%sType", property.getName()))
						.nextControlFlow("catch ($T e)", Exception.class).addStatement("return null").endControlFlow()
						.build());

				methods.add(MethodSpec.methodBuilder(String.format("set%s", TextUtil.capitalize(property.getName())))
						.addModifiers(Modifier.PUBLIC).addParameter(handler.getParameterizedType(), property.getName())
						.addCode("\n")
						.addStatement("this.$L = $L.getId()", String.format("%sId", property.getName()),
								property.getName())
						.addStatement("this.$L = $L.getType().getName()", String.format("%sType", property.getName()),
								property.getName())
						.build());

			} else {

				fields.add(FieldSpec.builder(handler.getParameterizedType(), property.getName())
						.addModifiers(Modifier.PRIVATE).build());

				if (handler.isBoolean()) {
					methods.add(MethodSpec.methodBuilder(String.format("is%s", TextUtil.capitalize(property.getName())))
							.addModifiers(Modifier.PUBLIC).returns(handler.getParameterizedType())
							.addStatement("return $L", property.getName()).build());
				} else {
					methods.add(
							MethodSpec.methodBuilder(String.format("get%s", TextUtil.capitalize(property.getName())))
									.addModifiers(Modifier.PUBLIC).returns(handler.getParameterizedType())
									.addStatement("return $L", property.getName()).build());
				}

				methods.add(MethodSpec.methodBuilder(String.format("set%s", TextUtil.capitalize(property.getName())))
						.addModifiers(Modifier.PUBLIC).addParameter(handler.getParameterizedType(), property.getName())
						.addStatement("this.$L = $L", property.getName(), property.getName()).build());
			}
		}

		methods.add(generateToIndex(entity, index));

		ClassName className = ClassName.bestGuess(String.format("%sData", index.getName()));
		TypeName parentTypeName = ParameterizedTypeName.get(
				ClassName.bestGuess(TypeUtil.BASE_ENTITY_INDEX_DATA_TYPE_NAME), ClassName.bestGuess(entity.getName()),
				ParameterizedTypeName.get(ClassName.bestGuess(TypeUtil.BASE_ENTITY_INDEX_TYPE_NAME),
						ClassName.bestGuess(entity.getName())));

		List<Modifier> modifiers = new ArrayList<>();

		modifiers.add(Modifier.PUBLIC);
		modifiers.add(Modifier.FINAL);

		List<AnnotationSpec> annotations = new ArrayList<>();

		TypeSpec type = TypeSpec.classBuilder(className.simpleName()).superclass(parentTypeName)
				.addModifiers(modifiers.toArray(new Modifier[modifiers.size()])).addAnnotations(annotations)
				.addFields(fields).addMethods(methods).build();

		JavaFile javaFile = JavaFile.builder(className.packageName(), type).build();

		try (Writer writer = filer.createSourceFile(String.format("%sData", index.getName())).openWriter()) {
			javaFile.writeTo(writer);
		}
	}

	private MethodSpec generateToIndex(EntityMetadata entity, EntityIndexMetadata index) {

		ClassName className = ClassName.bestGuess(index.getName());

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("$T index = new $T()", className, className);
		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("index.setId(getId())");
		codeBlockBuilder.addStatement("index.setSource(getSource())");

		Collection<EntityIndexPropertyMetadata> properties = index.getProperties().values();

		for (EntityIndexPropertyMetadata property : properties) {

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property.getType(), property.getParameters());

			codeBlockBuilder.add("\n");
			codeBlockBuilder.beginControlFlow("if (properties.isEmpty() || properties.contains($S))",
					property.getName());

			if (handler.isBoolean()) {
				codeBlockBuilder.addStatement("index.set$L(is$L())", TextUtil.capitalize(property.getName()),
						TextUtil.capitalize(property.getName()));
			} else {
				codeBlockBuilder.addStatement("index.set$L(get$L())", TextUtil.capitalize(property.getName()),
						TextUtil.capitalize(property.getName()));
			}

			codeBlockBuilder.endControlFlow();
		}

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("return index");

		List<AnnotationSpec> annotations = new ArrayList<>();

		annotations.add(AnnotationSpec.builder(ClassName.get(Override.class)).build());

		List<ParameterSpec> parameters = new ArrayList<>();

		parameters.add(ParameterSpec
				.builder(ParameterizedTypeName.get(ClassName.get(Set.class), ClassName.get(String.class)), "properties")
				.build());

		CodeBlock codeBlock = codeBlockBuilder.build();

		MethodSpec method = MethodSpec.methodBuilder("toIndex").addAnnotations(annotations)
				.addModifiers(Modifier.PUBLIC).returns(className).addParameters(parameters).addCode(codeBlock).build();

		return method;
	}
}
