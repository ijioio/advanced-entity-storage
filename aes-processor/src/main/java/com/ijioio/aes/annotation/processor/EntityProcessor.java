package com.ijioio.aes.annotation.processor;

import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import com.ijioio.aes.annotation.Entity;
import com.ijioio.aes.annotation.processor.exception.ProcessorException;

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

		messager.debug("call -> " + annotations);
		messager.debug("call proccesing over -> " + roundEnv.processingOver());

		try {

			for (TypeElement annotation : annotations) {

				Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);

				for (Element annotatedElement : annotatedElements) {

					AnnotationMirror annotatinMirror = annotatedElement.getAnnotationMirrors().stream()
							.filter(item -> item.getAnnotationType().toString().equals(Entity.class.getCanonicalName()))
							.findFirst().orElse(null);

					ProcessorContext processorContext = ProcessorContext.of(annotatedElement, annotatinMirror);

					EntityMetadata entityMetadata = EntityMetadata.of(processorContext);

					messager.debug("entity metadata -> " + entityMetadata);

					entityMetadata.generateCode(processingEnv.getFiler());
				}
			}

		} catch (ProcessorException e) {
			messager.error(e.getMessage(), e.getContext());
		}

		return true;
	}
}
