package com.ijioio.aes.annotation.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

import com.ijioio.aes.annotation.processor.exception.EntityPropertyIllegalStateException;
import com.ijioio.aes.annotation.processor.exception.ProcessorException;
import com.ijioio.aes.annotation.processor.util.ProcessorUtil;
import com.ijioio.aes.annotation.processor.util.TextUtil;

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

				name = value.accept(ProcessorUtil.stringVisitor, null);

			} else if (key.getSimpleName().contentEquals("type")) {

				AnnotationMirror annotationMirror = value.accept(ProcessorUtil.annotationVisitor, null);

				type = TypeMetadata.of(context.withAnnotationMirror(annotationMirror));

			} else if (key.getSimpleName().contentEquals("parameters")) {

				List<? extends AnnotationValue> annotationValues = value.accept(ProcessorUtil.arrayVisitor, null);

				parameters.clear();

				for (AnnotationValue annotationValue : annotationValues) {

					AnnotationMirror annotationMirror = annotationValue.accept(ProcessorUtil.annotationVisitor, null);

					TypeMetadata type = TypeMetadata.of(context.withAnnotationMirror(annotationMirror));

					parameters.add(type);
				}
			}
		}

		if (TextUtil.isBlank(name)) {
			throw new EntityPropertyIllegalStateException(String.format("Name of the entity property is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
		}

		if (type == null) {
			throw new EntityPropertyIllegalStateException(String.format("Type of the entity property is not defined"),
					MessageContext.of(context.getElement(), context.getAnnotationMirror(), null));
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
