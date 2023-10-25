package com.ijioio.aes.annotation.processor.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.ijioio.aes.annotation.Type;
import com.ijioio.aes.annotation.processor.EntityIndexPropertyMetadata;
import com.ijioio.aes.annotation.processor.EntityPropertyMetadata;
import com.ijioio.aes.annotation.processor.Messager;
import com.ijioio.aes.annotation.processor.TypeMetadata;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.WildcardTypeName;

/** Helper class for code gen type. */
public class CodeGenTypeUtil {

	/**
	 * Type name for boolean type.
	 */
	public static final TypeName BOOLEAN_TYPE_NAME = TypeName.BOOLEAN;

	/**
	 * Type name for char type.
	 */
	public static final TypeName CHAR_TYPE_NAME = TypeName.CHAR;

	/**
	 * Type name for byte type.
	 */
	public static final TypeName BYTE_TYPE_NAME = TypeName.BYTE;

	/**
	 * Type name for short type.
	 */
	public static final TypeName SHORT_TYPE_NAME = TypeName.SHORT;

	/**
	 * Type name for int type.
	 */
	public static final TypeName INT_TYPE_NAME = TypeName.INT;

	/**
	 * Type name for long type.
	 */
	public static final TypeName LONG_TYPE_NAME = TypeName.LONG;

	/**
	 * Type name for float type.
	 */
	public static final TypeName FLOAT_TYPE_NAME = TypeName.FLOAT;

	/**
	 * Type name for double type.
	 */
	public static final TypeName DOUBLE_TYPE_NAME = TypeName.DOUBLE;

	/**
	 * Type name for byte array type.
	 */
	public static final ArrayTypeName BYTE_ARRAY_TYPE_NAME = ArrayTypeName.of(TypeName.BYTE);

	/**
	 * Type name for {@link String} type.
	 */
	public static final ClassName STRING_TYPE_NAME = ClassName.get(String.class);

	/**
	 * Type name for {@link Instant} type.
	 */
	public static final ClassName INSTANT_TYPE_NAME = ClassName.get(Instant.class);

	/**
	 * Type name for {@link LocalDateTime} type.
	 */
	public static final ClassName LOCAL_DATE_TIME_TYPE_NAME = ClassName.get(LocalDateTime.class);

	/**
	 * Type name for {@link LocalDate} type.
	 */
	public static final ClassName LOCAL_DATE_TYPE_NAME = ClassName.get(LocalDate.class);

	/**
	 * Type name for {@link LocalTime} type.
	 */
	public static final ClassName LOCAL_TIME_TYPE_NAME = ClassName.get(LocalTime.class);

	/**
	 * Type name for {@link Class} type.
	 */
	public static final ClassName CLASS_TYPE_NAME = ClassName.get(Class.class);

	/**
	 * Type name for {@link List} type.
	 */
	public static final ClassName LIST_TYPE_NAME = ClassName.get(List.class);

	/**
	 * Type name for {@link Set} type.
	 */
	public static final ClassName SET_TYPE_NAME = ClassName.get(Set.class);

	/**
	 * Type name for {@link Map} type.
	 */
	public static final ClassName MAP_TYPE_NAME = ClassName.get(Map.class);

	/**
	 * Type name for {@link ArrayList} type.
	 */
	public static final ClassName ARRAY_LIST_TYPE_NAME = ClassName.get(ArrayList.class);

	/**
	 * Type name for {@link LinkedHashSet} type.
	 */
	public static final ClassName LINKED_HASH_SET_TYPE_NAME = ClassName.get(LinkedHashSet.class);

	/**
	 * Type name for {@link LinkedHashMap} type.
	 */
	public static final ClassName LINKED_HASH_MAP_TYPE_NAME = ClassName.get(LinkedHashMap.class);

	/**
	 * Type name for entity reference type.
	 */
	public static final ClassName ENTITY_REFERENCE_TYPE_NAME = ClassName
			.bestGuess("com.ijioio.aes.core.EntityReference");

	/**
	 * Type name for serialization reader.
	 */
	public static final ClassName SERIALIZATION_READER_TYPE_NAME = ClassName
			.bestGuess("com.ijioio.aes.core.serialization.SerializationReader");

	/**
	 * Type name for serialization writer.
	 */
	public static final ClassName SERIALIZATION_WRITER_TYPE_NAME = ClassName
			.bestGuess("com.ijioio.aes.core.serialization.SerializationWriter");

	/**
	 * Type handler providing some various actions.
	 */
	public static interface CodeGenTypeHandler {

		/**
		 * Returns the type name of the type.
		 * 
		 * @return type name
		 */
		public TypeName getType();

		/**
		 * Returns the implementation type name of the type.
		 * 
		 * @return type name
		 */
		public TypeName getImplementationType();
	}

	public static CodeGenTypeHandler getTypeHandler(EntityPropertyMetadata property, Map<String, TypeMetadata> types,
			Messager messager) {

		return getTypeHandler(TypeMetadata.of(property.getName(), property.getType(), property.isReference(),
				property.isList(), property.isSet(), Collections.emptyList()), types, messager);
	}

	public static CodeGenTypeHandler getTypeHandler(EntityIndexPropertyMetadata property,
			Map<String, TypeMetadata> types, Messager messager) {

		return getTypeHandler(TypeMetadata.of(property.getName(), property.getType(), property.isReference(),
				property.isList(), property.isSet(), Collections.emptyList()), types, messager);
	}

	public static CodeGenTypeHandler getTypeHandler(TypeMetadata type, Map<String, TypeMetadata> types,
			Messager messager) {

		return new CodeGenTypeHandler() {

			@Override
			public TypeName getType() {

				String name = type.getType();

				TypeName typeName = null;

				if (types.containsKey(name)) {

					typeName = getTypeHandler(types.get(name), types, messager).getType();

				} else {

					if (name.equals(Type.BOOLEAN) || name.equals(TypeUtil.BOOLEAN_TYPE_NAME)) {
						typeName = BOOLEAN_TYPE_NAME;
					} else if (name.equals(Type.CHAR) || name.equals(TypeUtil.CHAR_TYPE_NAME)) {
						typeName = CHAR_TYPE_NAME;
					} else if (name.equals(Type.BYTE) || name.equals(TypeUtil.BYTE_TYPE_NAME)) {
						typeName = BYTE_TYPE_NAME;
					} else if (name.equals(Type.SHORT) || name.equals(TypeUtil.SHORT_TYPE_NAME)) {
						typeName = SHORT_TYPE_NAME;
					} else if (name.equals(Type.INT) || name.equals(TypeUtil.INT_TYPE_NAME)) {
						typeName = INT_TYPE_NAME;
					} else if (name.equals(Type.LONG) || name.equals(TypeUtil.LONG_TYPE_NAME)) {
						typeName = LONG_TYPE_NAME;
					} else if (name.equals(Type.FLOAT) || name.equals(TypeUtil.FLOAT_TYPE_NAME)) {
						typeName = FLOAT_TYPE_NAME;
					} else if (name.equals(Type.DOUBLE) || name.equals(TypeUtil.DOUBLE_TYPE_NAME)) {
						typeName = DOUBLE_TYPE_NAME;
					} else if (name.equals(Type.BYTE_ARRAY) || name.equals(TypeUtil.BYTE_ARRAY_TYPE_NAME)) {
						typeName = BYTE_ARRAY_TYPE_NAME;
					} else if (name.equals(Type.STRING) || name.equals(TypeUtil.STRING_TYPE_NAME)) {
						typeName = STRING_TYPE_NAME;
					} else if (name.equals(Type.INSTANT) || name.equals(TypeUtil.INSTANT_TYPE_NAME)) {
						typeName = INSTANT_TYPE_NAME;
					} else if (name.equals(Type.LOCAL_DATE_TIME) || name.equals(TypeUtil.LOCAL_DATE_TIME_TYPE_NAME)) {
						typeName = LOCAL_DATE_TIME_TYPE_NAME;
					} else if (name.equals(Type.LOCAL_DATE) || name.equals(TypeUtil.LOCAL_DATE_TYPE_NAME)) {
						typeName = LOCAL_DATE_TYPE_NAME;
					} else if (name.equals(Type.LOCAL_TIME) || name.equals(TypeUtil.LOCAL_TIME_TYPE_NAME)) {
						typeName = LOCAL_TIME_TYPE_NAME;
					} else if (name.equals(Type.CLASS) || name.equals(TypeUtil.CLASS_TYPE_NAME)) {
						typeName = CLASS_TYPE_NAME;
					} else if (name.equals(Type.LIST) || name.equals(TypeUtil.LIST_TYPE_NAME)) {
						typeName = LIST_TYPE_NAME;
					} else if (name.equals(Type.SET) || name.equals(TypeUtil.SET_TYPE_NAME)) {
						typeName = SET_TYPE_NAME;
					} else if (name.equals(Type.MAP) || name.equals(TypeUtil.MAP_TYPE_NAME)) {
						typeName = MAP_TYPE_NAME;
					} else if (name.equals(Type.ENTITY_REFERENCE) || name.equals(TypeUtil.ENTITY_REFERENCE_TYPE_NAME)) {
						typeName = ENTITY_REFERENCE_TYPE_NAME;
					} else {
						typeName = ClassName.bestGuess(name);
					}

					if (!typeName.isPrimitive() && (typeName instanceof ClassName)) {

						List<TypeName> parameters = type.getParameters().stream()
								.map(item -> Optional
										.of(getTypeHandler(TypeMetadata.of(item.getName(), item.getName(), false, false,
												false, Collections.emptyList()), types, messager).getType())
										.map(type -> item.isWildcard() ? WildcardTypeName.subtypeOf(type) : type).get())
								.collect(Collectors.toList());

						if (parameters.size() > 0) {

							typeName = ParameterizedTypeName.get((ClassName) typeName,
									parameters.stream().toArray(size -> new TypeName[size]));
						}
					}
				}

				if (!typeName.isPrimitive()) {

					if (type.isReference()) {
						typeName = ParameterizedTypeName.get(ENTITY_REFERENCE_TYPE_NAME, typeName);
					}

					if (type.isList()) {
						typeName = ParameterizedTypeName.get(LIST_TYPE_NAME, typeName);
					} else if (type.isSet()) {
						typeName = ParameterizedTypeName.get(SET_TYPE_NAME, typeName);
					}
				}

				return typeName;
			}

			@Override
			public TypeName getImplementationType() {

				TypeName typeName = getType();

				if (typeName instanceof ParameterizedTypeName) {

					ParameterizedTypeName parameterizedTypeName = (ParameterizedTypeName) typeName;

					if (parameterizedTypeName.rawType.equals(LIST_TYPE_NAME)) {
						typeName = ParameterizedTypeName.get(ARRAY_LIST_TYPE_NAME, parameterizedTypeName.typeArguments
								.toArray(new TypeName[parameterizedTypeName.typeArguments.size()]));
					} else if (parameterizedTypeName.rawType.equals(SET_TYPE_NAME)) {
						typeName = ParameterizedTypeName.get(LINKED_HASH_SET_TYPE_NAME,
								parameterizedTypeName.typeArguments
										.toArray(new TypeName[parameterizedTypeName.typeArguments.size()]));
					} else if (parameterizedTypeName.rawType.equals(MAP_TYPE_NAME)) {
						typeName = ParameterizedTypeName.get(LINKED_HASH_MAP_TYPE_NAME,
								parameterizedTypeName.typeArguments
										.toArray(new TypeName[parameterizedTypeName.typeArguments.size()]));
					}

				} else {

					if (typeName.equals(LIST_TYPE_NAME)) {
						typeName = ARRAY_LIST_TYPE_NAME;
					} else if (typeName.equals(SET_TYPE_NAME)) {
						typeName = LINKED_HASH_SET_TYPE_NAME;
					} else if (typeName.equals(MAP_TYPE_NAME)) {
						typeName = LINKED_HASH_MAP_TYPE_NAME;
					}
				}

				return typeName;
			}
		};
	}
}
