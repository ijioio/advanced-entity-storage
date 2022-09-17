package com.ijioio.aes.annotation.processor.util;

import java.util.List;
import java.util.regex.Pattern;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;

/** Helper class for processor. */
public class ProcessorUtil {

	public static final SimpleAnnotationValueVisitor8<Boolean, Void> booleanVisitor = new SimpleAnnotationValueVisitor8<Boolean, Void>() {

		@Override
		public Boolean visitBoolean(boolean value, Void parameter) {
			return Boolean.valueOf(value);
		}
	};

	public static final SimpleAnnotationValueVisitor8<String, Void> stringVisitor = new SimpleAnnotationValueVisitor8<String, Void>() {

		@Override
		public String visitString(String value, Void parameter) {
			return value;
		}
	};

	public static final SimpleAnnotationValueVisitor8<AnnotationMirror, Void> annotationVisitor = new SimpleAnnotationValueVisitor8<AnnotationMirror, Void>() {

		@Override
		public AnnotationMirror visitAnnotation(AnnotationMirror value, Void parameter) {
			return value;
		}
	};

	public static final SimpleAnnotationValueVisitor8<List<? extends AnnotationValue>, Void> arrayVisitor = new SimpleAnnotationValueVisitor8<List<? extends AnnotationValue>, Void>() {

		@Override
		public List<? extends AnnotationValue> visitArray(List<? extends AnnotationValue> value, Void parameter) {
			return value;
		}
	};

	private static final Pattern pattern = Pattern.compile("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*"
			+ "(\\.\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*");

	public static boolean isJavaIdentifier(String value) {
		return pattern.matcher(value).matches();
	}
}
