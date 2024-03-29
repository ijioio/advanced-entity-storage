package com.ijioio.aes.annotation.processor;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
import com.squareup.javapoet.TypeVariableName;
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

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property, entity.getTypes(), messager);

			TypeName type = handler.getType();
			TypeName implementationType = handler.getImplementationType();

			if (property.isFinal()) {
				fields.add(FieldSpec.builder(type, property.getName()).addModifiers(Modifier.PRIVATE, Modifier.FINAL)
						.initializer((implementationType instanceof ParameterizedTypeName) ? "new $T<>()" : "new $T()",
								(implementationType instanceof ParameterizedTypeName)
										? ((ParameterizedTypeName) implementationType).rawType
										: implementationType)
						.build());
			} else {
				fields.add(FieldSpec.builder(type, property.getName()).addModifiers(Modifier.PRIVATE).build());
			}

			methods.add(MethodSpec
					.methodBuilder(String.format("%s%s", type.equals(CodeGenTypeUtil.BOOLEAN_TYPE_NAME) ? "is" : "get",
							TextUtil.capitalize(property.getName())))
					.addModifiers(Modifier.PUBLIC).returns(type).addStatement("return $L", property.getName()).build());

			if (!property.isFinal()) {
				methods.add(MethodSpec.methodBuilder(String.format("set%s", TextUtil.capitalize(property.getName())))
						.addModifiers(Modifier.PUBLIC).addParameter(type, property.getName())
						.addStatement("this.$L = $L", property.getName(), property.getName()).build());
			}
		}

		methods.add(generateGetProperties());
		methods.add(generateRead(entity));
		methods.add(generateWrite(entity));

		TypeSpec propertiesType = generateProperties2(entity);

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
				.addAnnotations(annotations).addType(propertiesType).addFields(fields).addMethods(methods).build();

		JavaFile javaFile = JavaFile.builder(className.packageName(), type).build();

		try (Writer writer = filer.createSourceFile(entity.getName()).openWriter()) {
			javaFile.writeTo(writer);
		}
	}

	private MethodSpec generateRead(EntityMetadata entity) {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		Collection<EntityPropertyMetadata> properties = entity.getProperties().values();

		if (properties.size() > 0) {
			codeBlockBuilder.add("\n");
		}

		boolean unchecked = false;

		if (properties.size() > 0) {

			int count = 0;

			for (EntityPropertyMetadata property : properties) {

				CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property, entity.getTypes(), messager);

				TypeName type = handler.getType();

				if (count == 0) {
					codeBlockBuilder.beginControlFlow("if ($T.$L.equals(property))", ClassName.bestGuess("Properties"),
							property.getName());
				} else {
					codeBlockBuilder.nextControlFlow("else if ($T.$L.equals(property))",
							ClassName.bestGuess("Properties"), property.getName());
				}

				if (type.isPrimitive()) {
					codeBlockBuilder.addStatement("return ($T) ($T) $L", TypeVariableName.get("T"), type.box(),
							property.getName());
				} else {
					codeBlockBuilder.addStatement("return ($T) $L", TypeVariableName.get("T"), property.getName());
				}

				if (count == properties.size() - 1) {

					codeBlockBuilder.nextControlFlow("else");
					codeBlockBuilder.addStatement("return super.read(property)");
					codeBlockBuilder.endControlFlow();
				}

				unchecked = true;
				count++;
			}

		} else {

			codeBlockBuilder.addStatement("return super.read(property)");
		}

		List<AnnotationSpec> annotations = new ArrayList<>();

		if (unchecked) {
			annotations.add(AnnotationSpec.builder(ClassName.get(SuppressWarnings.class))
					.addMember("value", "$S", "unchecked").build());
		}

		annotations.add(AnnotationSpec.builder(ClassName.get(Override.class)).build());

		List<ParameterSpec> parameters = new ArrayList<>();

		parameters.add(ParameterSpec
				.builder(ParameterizedTypeName.get(CodeGenTypeUtil.PROPERTY_TYPE_NAME, TypeVariableName.get("T")),
						"property")
				.build());

		CodeBlock codeBlock = codeBlockBuilder.build();

		MethodSpec method = MethodSpec.methodBuilder("read").addAnnotations(annotations).addModifiers(Modifier.PUBLIC)
				.addTypeVariable(TypeVariableName.get("T")).returns(TypeVariableName.get("T")).addParameters(parameters)
				.addCode(codeBlock).addException(CodeGenTypeUtil.INTROSPECTION_EXCEPTION_TYPE_NAME).build();

		return method;
	}

	private MethodSpec generateWrite(EntityMetadata entity) {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		Collection<EntityPropertyMetadata> properties = entity.getProperties().values();

		if (properties.size() > 0) {
			codeBlockBuilder.add("\n");
		}

		boolean unchecked = false;

		if (properties.size() > 0) {

			int count = 0;

			for (EntityPropertyMetadata property : properties) {

				CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property, entity.getTypes(), messager);

				TypeName type = handler.getType();

				if (count == 0) {
					codeBlockBuilder.beginControlFlow("if ($T.$L.equals(property))", ClassName.bestGuess("Properties"),
							property.getName());
				} else {
					codeBlockBuilder.nextControlFlow("else if ($T.$L.equals(property))",
							ClassName.bestGuess("Properties"), property.getName());
				}

				if (property.isFinal()) {
					codeBlockBuilder.add("// do nothing\n");
				} else {
					codeBlockBuilder.addStatement("$L = ($T) value", property.getName(), type.box());
				}

				if (count == properties.size() - 1) {

					codeBlockBuilder.nextControlFlow("else");
					codeBlockBuilder.addStatement("super.write(property, value)");
					codeBlockBuilder.endControlFlow();
				}

				if (type instanceof ParameterizedTypeName && !property.isFinal()) {
					unchecked = true;
				}

				count++;
			}

		} else {

			codeBlockBuilder.addStatement("super.write(property, value)");
		}

		List<AnnotationSpec> annotations = new ArrayList<>();

		if (unchecked) {
			annotations.add(AnnotationSpec.builder(ClassName.get(SuppressWarnings.class))
					.addMember("value", "$S", "unchecked").build());
		}

		annotations.add(AnnotationSpec.builder(ClassName.get(Override.class)).build());

		List<ParameterSpec> parameters = new ArrayList<>();

		parameters.add(ParameterSpec
				.builder(ParameterizedTypeName.get(CodeGenTypeUtil.PROPERTY_TYPE_NAME, TypeVariableName.get("T")),
						"property")
				.build());
		parameters.add(ParameterSpec.builder(TypeVariableName.get("T"), "value").build());

		CodeBlock codeBlock = codeBlockBuilder.build();

		MethodSpec method = MethodSpec.methodBuilder("write").addAnnotations(annotations).addModifiers(Modifier.PUBLIC)
				.addTypeVariable(TypeVariableName.get("T")).addParameters(parameters).addCode(codeBlock)
				.addException(CodeGenTypeUtil.INTROSPECTION_EXCEPTION_TYPE_NAME).build();

		return method;
	}

	private TypeSpec generateProperties2(EntityMetadata entity) {

		List<FieldSpec> fields = new ArrayList<>();
		List<MethodSpec> methods = new ArrayList<>();

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.add("\n");

		Collection<EntityPropertyMetadata> properties = entity.getProperties().values();

		for (EntityPropertyMetadata property : properties) {

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property, entity.getTypes(), messager);

			TypeName type = handler.getType();

			fields.add(FieldSpec
					.builder(ParameterizedTypeName.get(CodeGenTypeUtil.PROPERTY_TYPE_NAME, type.box()),
							property.getName())
					.addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
					.initializer("$T.of($S, new $T() {})", CodeGenTypeUtil.PROPERTY_TYPE_NAME, property.getName(),
							ParameterizedTypeName.get(CodeGenTypeUtil.TYPE_REFERENCE_TYPE_NAME, type.box()))
					.build());

			codeBlockBuilder.addStatement("values.add($L)", property.getName());
		}

		fields.add(FieldSpec
				.builder(ParameterizedTypeName.get(ClassName.get(List.class),
						ParameterizedTypeName.get(CodeGenTypeUtil.PROPERTY_TYPE_NAME,
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

	private void generateIndex(EntityMetadata entity, EntityIndexMetadata index, Filer filer) throws IOException {

		List<FieldSpec> fields = new ArrayList<>();
		List<MethodSpec> methods = new ArrayList<>();

		Collection<EntityIndexPropertyMetadata> properties = index.getProperties().values();

		for (EntityIndexPropertyMetadata property : properties) {

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property, entity.getTypes(), messager);

			TypeName type = handler.getType();
			TypeName implementationType = handler.getImplementationType();

			if (property.isFinal()) {
				fields.add(FieldSpec.builder(type, property.getName()).addModifiers(Modifier.PRIVATE, Modifier.FINAL)
						.initializer((implementationType instanceof ParameterizedTypeName) ? "new $T<>()" : "new $T()",
								(implementationType instanceof ParameterizedTypeName)
										? ((ParameterizedTypeName) implementationType).rawType
										: implementationType)
						.build());
			} else {
				fields.add(FieldSpec.builder(type, property.getName()).addModifiers(Modifier.PRIVATE).build());
			}

			methods.add(MethodSpec
					.methodBuilder(String.format("%s%s", type.equals(CodeGenTypeUtil.BOOLEAN_TYPE_NAME) ? "is" : "get",
							TextUtil.capitalize(property.getName())))
					.addModifiers(Modifier.PUBLIC).returns(type).addStatement("return $L", property.getName()).build());

			if (!property.isFinal()) {
				methods.add(MethodSpec.methodBuilder(String.format("set%s", TextUtil.capitalize(property.getName())))
						.addModifiers(Modifier.PUBLIC).addParameter(type, property.getName())
						.addStatement("this.$L = $L", property.getName(), property.getName()).build());
			}
		}

		methods.add(generateGetProperties());
		methods.add(generateRead(entity, index));
		methods.add(generateWrite(entity, index));

		TypeSpec propertiesType = generateProperties(entity, index);

		ClassName className = ClassName.bestGuess(index.getName());
		TypeName parentTypeName = ParameterizedTypeName.get(CodeGenTypeUtil.BASE_ENTITY_INDEX_TYPE_NAME,
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

	private MethodSpec generateGetProperties() {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.add("\n");
		codeBlockBuilder.addStatement("$T properties = new $T<>(super.getProperties())",
				ParameterizedTypeName.get(ClassName.get(Collection.class), ParameterizedTypeName
						.get(CodeGenTypeUtil.PROPERTY_TYPE_NAME, WildcardTypeName.subtypeOf(TypeName.OBJECT))),
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
				.returns(ParameterizedTypeName.get(ClassName.get(Collection.class), ParameterizedTypeName
						.get(CodeGenTypeUtil.PROPERTY_TYPE_NAME, WildcardTypeName.subtypeOf(TypeName.OBJECT))))
				.addCode(codeBlock).build();

		return method;
	}

	private MethodSpec generateRead(EntityMetadata entity, EntityIndexMetadata index) {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		Collection<EntityIndexPropertyMetadata> properties = index.getProperties().values();

		if (properties.size() > 0) {
			codeBlockBuilder.add("\n");
		}

		boolean unchecked = false;

		if (properties.size() > 0) {

			int count = 0;

			for (EntityIndexPropertyMetadata property : properties) {

				CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property, entity.getTypes(), messager);

				TypeName type = handler.getType();

				if (count == 0) {
					codeBlockBuilder.beginControlFlow("if ($T.$L.equals(property))", ClassName.bestGuess("Properties"),
							property.getName());
				} else {
					codeBlockBuilder.nextControlFlow("else if ($T.$L.equals(property))",
							ClassName.bestGuess("Properties"), property.getName());
				}

				if (type.isPrimitive()) {
					codeBlockBuilder.addStatement("return ($T) ($T) $L", TypeVariableName.get("T"), type.box(),
							property.getName());
				} else {
					codeBlockBuilder.addStatement("return ($T) $L", TypeVariableName.get("T"), property.getName());
				}

				if (count == properties.size() - 1) {

					codeBlockBuilder.nextControlFlow("else");
					codeBlockBuilder.addStatement("return super.read(property)");
					codeBlockBuilder.endControlFlow();
				}

				unchecked = true;
				count++;
			}

		} else {

			codeBlockBuilder.addStatement("return super.read(property)");
		}

		List<AnnotationSpec> annotations = new ArrayList<>();

		if (unchecked) {
			annotations.add(AnnotationSpec.builder(ClassName.get(SuppressWarnings.class))
					.addMember("value", "$S", "unchecked").build());
		}

		annotations.add(AnnotationSpec.builder(ClassName.get(Override.class)).build());

		List<ParameterSpec> parameters = new ArrayList<>();

		parameters.add(ParameterSpec
				.builder(ParameterizedTypeName.get(CodeGenTypeUtil.PROPERTY_TYPE_NAME, TypeVariableName.get("T")),
						"property")
				.build());

		CodeBlock codeBlock = codeBlockBuilder.build();

		MethodSpec method = MethodSpec.methodBuilder("read").addAnnotations(annotations).addModifiers(Modifier.PUBLIC)
				.addTypeVariable(TypeVariableName.get("T")).returns(TypeVariableName.get("T")).addParameters(parameters)
				.addCode(codeBlock).addException(CodeGenTypeUtil.INTROSPECTION_EXCEPTION_TYPE_NAME).build();

		return method;
	}

	private MethodSpec generateWrite(EntityMetadata entity, EntityIndexMetadata index) {

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		Collection<EntityIndexPropertyMetadata> properties = index.getProperties().values();

		if (properties.size() > 0) {
			codeBlockBuilder.add("\n");
		}

		boolean unchecked = false;

		if (properties.size() > 0) {

			int count = 0;

			for (EntityIndexPropertyMetadata property : properties) {

				CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property, entity.getTypes(), messager);

				TypeName type = handler.getType();

				if (count == 0) {
					codeBlockBuilder.beginControlFlow("if ($T.$L.equals(property))", ClassName.bestGuess("Properties"),
							property.getName());
				} else {
					codeBlockBuilder.nextControlFlow("else if ($T.$L.equals(property))",
							ClassName.bestGuess("Properties"), property.getName());
				}

				if (property.isFinal()) {
					codeBlockBuilder.add("// do nothing\n");
				} else {
					codeBlockBuilder.addStatement("$L = ($T) value", property.getName(), type.box());
				}

				if (count == properties.size() - 1) {

					codeBlockBuilder.nextControlFlow("else");
					codeBlockBuilder.addStatement("super.write(property, value)");
					codeBlockBuilder.endControlFlow();
				}

				if (type instanceof ParameterizedTypeName && !property.isFinal()) {
					unchecked = true;
				}

				count++;
			}

		} else {

			codeBlockBuilder.addStatement("super.write(property, value)");
		}

		List<AnnotationSpec> annotations = new ArrayList<>();

		if (unchecked) {
			annotations.add(AnnotationSpec.builder(ClassName.get(SuppressWarnings.class))
					.addMember("value", "$S", "unchecked").build());
		}

		annotations.add(AnnotationSpec.builder(ClassName.get(Override.class)).build());

		List<ParameterSpec> parameters = new ArrayList<>();

		parameters.add(ParameterSpec
				.builder(ParameterizedTypeName.get(CodeGenTypeUtil.PROPERTY_TYPE_NAME, TypeVariableName.get("T")),
						"property")
				.build());
		parameters.add(ParameterSpec.builder(TypeVariableName.get("T"), "value").build());

		CodeBlock codeBlock = codeBlockBuilder.build();

		MethodSpec method = MethodSpec.methodBuilder("write").addAnnotations(annotations).addModifiers(Modifier.PUBLIC)
				.addTypeVariable(TypeVariableName.get("T")).addParameters(parameters).addCode(codeBlock)
				.addException(CodeGenTypeUtil.INTROSPECTION_EXCEPTION_TYPE_NAME).build();

		return method;
	}

	private TypeSpec generateProperties(EntityMetadata entity, EntityIndexMetadata index) {

		List<FieldSpec> fields = new ArrayList<>();
		List<MethodSpec> methods = new ArrayList<>();

		CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();

		codeBlockBuilder.add("\n");

		Collection<EntityIndexPropertyMetadata> properties = index.getProperties().values();

		for (EntityIndexPropertyMetadata property : properties) {

			CodeGenTypeHandler handler = CodeGenTypeUtil.getTypeHandler(property, entity.getTypes(), messager);

			TypeName type = handler.getType();

			fields.add(FieldSpec
					.builder(ParameterizedTypeName.get(CodeGenTypeUtil.PROPERTY_TYPE_NAME, type.box()),
							property.getName())
					.addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
					.initializer("$T.of($S, new $T() {})", CodeGenTypeUtil.PROPERTY_TYPE_NAME, property.getName(),
							ParameterizedTypeName.get(CodeGenTypeUtil.TYPE_REFERENCE_TYPE_NAME, type.box()))
					.build());

			codeBlockBuilder.addStatement("values.add($L)", property.getName());
		}

		fields.add(FieldSpec
				.builder(ParameterizedTypeName.get(ClassName.get(List.class),
						ParameterizedTypeName.get(CodeGenTypeUtil.PROPERTY_TYPE_NAME,
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
}
