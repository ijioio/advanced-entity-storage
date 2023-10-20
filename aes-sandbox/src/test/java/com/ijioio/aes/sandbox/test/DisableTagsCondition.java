package com.ijioio.aes.sandbox.test;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.platform.commons.util.AnnotationUtils;

public class DisableTagsCondition implements ExecutionCondition {

	@Override
	public ConditionEvaluationResult evaluateExecutionCondition(ExtensionContext context) {

		AnnotatedElement element = context.getElement().orElse(null);

		if (element == null) {
			throw new IllegalStateException("annotation element is null");
		}

		Set<String> disableTags = AnnotationUtils.findAnnotation(context.getRequiredTestClass(), DisableTags.class)
				.map(item -> Arrays.asList(item.value())).orElse(Collections.emptyList()).stream()
				.collect(Collectors.toSet());
		Set<String> tags = AnnotationUtils.findRepeatableAnnotations(element, Tag.class).stream()
				.map(item -> item.value()).collect(Collectors.toSet());

		boolean disable = tags.stream().anyMatch(item -> disableTags.contains(item));

		return disable ? ConditionEvaluationResult.disabled("skipped due to disable tags")
				: ConditionEvaluationResult.enabled(null);
	}
}