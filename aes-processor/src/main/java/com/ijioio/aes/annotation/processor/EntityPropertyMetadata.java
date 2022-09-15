package com.ijioio.aes.annotation.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;

import com.ijioio.aes.annotation.processor.exception.ProcessorException;

public class EntityPropertyMetadata {

	public static EntityPropertyMetadata of(ProcessorContext context) throws ProcessorException {
		return new EntityPropertyMetadata(context);
	}

	private final ProcessorContext context;

	private String name;

	private TypeMetadata type;

	private final List<TypeMetadata> parameters = new ArrayList<>();

	private EntityPropertyMetadata(ProcessorContext context) throws ProcessorException {

		this.context = context;

		Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = context.getAnnotationMirror()
				.getElementValues();

		for (Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {

			ExecutableElement key = entry.getKey();
			AnnotationValue value = entry.getValue();

			if (key.getSimpleName().contentEquals("name")) {

				name = value.accept(new SimpleAnnotationValueVisitor8<String, Void>() {

					@Override
					public String visitString(String s, Void p) {
						return s;
					}

				}, null);

			} else if (key.getSimpleName().contentEquals("type")) {

				AnnotationMirror annotationMirror = value
						.accept(new SimpleAnnotationValueVisitor8<AnnotationMirror, Void>() {

							@Override
							public AnnotationMirror visitAnnotation(AnnotationMirror a, Void p) {
								return a;
							}

						}, null);

				type = TypeMetadata.of(context.withAnnotationMirror(annotationMirror));

			} else if (key.getSimpleName().contentEquals("parameters")) {

				List<? extends AnnotationValue> annotationValues = value
						.accept(new SimpleAnnotationValueVisitor8<List<? extends AnnotationValue>, Void>() {

							@Override
							public List<? extends AnnotationValue> visitArray(List<? extends AnnotationValue> vals,
									Void p) {
								return vals;
							}

						}, null);

				parameters.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					AnnotationMirror annotationMirror = annotationValue
							.accept(new SimpleAnnotationValueVisitor8<AnnotationMirror, Void>() {

								@Override
								public AnnotationMirror visitAnnotation(AnnotationMirror a, Void p) {
									return a;
								}

							}, null);

					TypeMetadata type = TypeMetadata.of(context.withAnnotationMirror(annotationMirror));

					parameters.add(type);
				}
			}
		}
	}

	public ProcessorContext getContext() {
		return context;
	}

	public String getName() {
		return name;
	}

	public TypeMetadata getType() {
		return type;
	}

	public List<TypeMetadata> getParameters() {
		return parameters;
	}

	@Override
	public String toString() {
		return "EntityPropertyMetadata [name=" + name + ", type=" + type + ", parameters=" + parameters + "]";
	}
}
