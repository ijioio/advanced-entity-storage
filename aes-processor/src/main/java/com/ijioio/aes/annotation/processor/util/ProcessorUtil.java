package com.ijioio.aes.annotation.processor.util;

import java.util.List;
import java.util.Optional;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;
import javax.lang.model.util.SimpleElementVisitor8;
import javax.lang.model.util.SimpleTypeVisitor8;

/** Helper class for processor. */
public class ProcessorUtil {

	public static final ElementVisitor<TypeElement, Void> typeElementVisitor = new SimpleElementVisitor8<TypeElement, Void>() {

		@Override
		public TypeElement visitType(TypeElement value, Void parameter) {
			return value;
		};
	};

	public static final TypeVisitor<DeclaredType, Void> declaredTypeVisitor = new SimpleTypeVisitor8<DeclaredType, Void>() {

		@Override
		public DeclaredType visitDeclared(DeclaredType value, Void parameter) {
			return value;
		};
	};

	public static final AnnotationValueVisitor<Boolean, Void> booleanVisitor = new SimpleAnnotationValueVisitor8<Boolean, Void>() {

		@Override
		public Boolean visitBoolean(boolean value, Void parameter) {
			return Boolean.valueOf(value);
		}
	};

	public static final AnnotationValueVisitor<String, Void> stringVisitor = new SimpleAnnotationValueVisitor8<String, Void>() {

		@Override
		public String visitString(String value, Void parameter) {
			return value;
		}
	};

	public static final AnnotationValueVisitor<TypeMirror, Void> typeVisitor = new SimpleAnnotationValueVisitor8<TypeMirror, Void>() {

		@Override
		public TypeMirror visitType(TypeMirror value, Void parameter) {
			return value;
		};
	};

	public static final AnnotationValueVisitor<VariableElement, Void> enumVisitor = new SimpleAnnotationValueVisitor8<VariableElement, Void>() {

		@Override
		public VariableElement visitEnumConstant(VariableElement value, Void parameter) {
			return value;
		};
	};

	public static final AnnotationValueVisitor<AnnotationMirror, Void> annotationVisitor = new SimpleAnnotationValueVisitor8<AnnotationMirror, Void>() {

		@Override
		public AnnotationMirror visitAnnotation(AnnotationMirror value, Void parameter) {
			return value;
		}
	};

	public static final AnnotationValueVisitor<List<? extends AnnotationValue>, Void> arrayVisitor = new SimpleAnnotationValueVisitor8<List<? extends AnnotationValue>, Void>() {

		@Override
		public List<? extends AnnotationValue> visitArray(List<? extends AnnotationValue> value, Void parameter) {
			return value;
		}
	};

	public static Optional<Boolean> isSubtype(TypeMirror type, String name) {

		TypeMirror parentTypeMirror = type;

		while (true) {

			TypeElement typeElement = ProcessorUtil.typeElementVisitor
					.visit(ProcessorUtil.declaredTypeVisitor.visit(parentTypeMirror).asElement());

			if (typeElement.getQualifiedName().toString().equals(name)) {
				return Optional.of(Boolean.TRUE);
			}

			parentTypeMirror = ProcessorUtil.typeElementVisitor
					.visit(ProcessorUtil.declaredTypeVisitor.visit(parentTypeMirror).asElement()).getSuperclass();

			if (parentTypeMirror.getKind() == TypeKind.NONE) {
				return Optional.of(Boolean.FALSE);
			}

			if (parentTypeMirror.getKind() == TypeKind.ERROR) {
				return Optional.empty();
			}
		}
	}
}
