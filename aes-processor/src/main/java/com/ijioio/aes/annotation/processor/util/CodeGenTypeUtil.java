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
import com.ijioio.aes.annotation.processor.TypeMetadata;
import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

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
	public static final TypeName BYTE_ARRAY_TYPE_NAME = ArrayTypeName.of(TypeName.BYTE);

	/**
	 * Type name for {@link String} type.
	 */
	public static final TypeName STRING_TYPE_NAME = ClassName.get(String.class);

	/**
	 * Type name for {@link Instant} type.
	 */
	public static final TypeName INSTANT_TYPE_NAME = ClassName.get(Instant.class);

	/**
	 * Type name for {@link LocalDateTime} type.
	 */
	public static final TypeName LOCAL_DATE_TIME_TYPE_NAME = ClassName.get(LocalDateTime.class);

	/**
	 * Type name for {@link LocalDate} type.
	 */
	public static final TypeName LOCAL_DATE_TYPE_NAME = ClassName.get(LocalDate.class);

	/**
	 * Type name for {@link LocalTime} type.
	 */
	public static final TypeName LOCAL_TIME_TYPE_NAME = ClassName.get(LocalTime.class);

	/**
	 * Type name for {@link Class} type.
	 */
	public static final TypeName CLASS_TYPE_NAME = ClassName.get(Class.class);

	/**
	 * Type name for {@link List} type.
	 */
	public static final TypeName LIST_TYPE_NAME = ClassName.get(List.class);

	/**
	 * Type name for {@link Set} type.
	 */
	public static final TypeName SET_TYPE_NAME = ClassName.get(Set.class);

	/**
	 * Type name for {@link Map} type.
	 */
	public static final TypeName MAP_TYPE_NAME = ClassName.get(Map.class);

	/**
	 * Type name for {@link ArrayList} type.
	 */
	public static final TypeName ARRAY_LIST_TYPE_NAME = ClassName.get(ArrayList.class);

	/**
	 * Type name for {@link LinkedHashSet} type.
	 */
	public static final TypeName LINKED_HASH_SET_TYPE_NAME = ClassName.get(LinkedHashSet.class);

	/**
	 * Type name for {@link LinkedHashMap} type.
	 */
	public static final TypeName LINKED_HASH_MAP_TYPE_NAME = ClassName.get(LinkedHashMap.class);

	/**
	 * Type name for base entity type.
	 */
	public static final TypeName BASE_ENTITY_TYPE_NAME = ClassName.bestGuess("com.ijioio.aes.core.BaseEntity");

	/**
	 * Type name for base entity index type.
	 */
	public static final TypeName BASE_ENTITY_INDEX_TYPE_NAME = ClassName
			.bestGuess("com.ijioio.aes.core.BaseEntityIndex");

	/**
	 * Type name for entity reference type.
	 */
	public static final TypeName ENTITY_REFERENCE_TYPE_NAME = ClassName
			.bestGuess("com.ijioio.aes.core.EntityReference");

	/**
	 * Type name for serialization context.
	 */
	public static final TypeName SERIALIZATION_CONTEXT_TYPE_NAME = ClassName
			.bestGuess("com.ijioio.aes.core.serialization.SerializationContext");

	/**
	 * Type name for serialization handler.
	 */
	public static final TypeName SERIALIZATION_HANDLER_TYPE_NAME = ClassName
			.bestGuess("com.ijioio.aes.core.serialization.SerializationHandler");

	/**
	 * Type name for serialization exception.
	 */
	public static final TypeName SERIALIZATION_EXCEPTION_TYPE_NAME = ClassName
			.bestGuess("com.ijioio.aes.core.serialization.SerializationException");

	/**
	 * Type name for serialization writer.
	 */
	public static final TypeName SERIALIZATION_WRITER_TYPE_NAME = ClassName
			.bestGuess("com.ijioio.aes.core.serialization.SerializationWriter");

	/**
	 * Type name for serialization reader.
	 */
	public static final TypeName SERIALIZATION_READER_TYPE_NAME = ClassName
			.bestGuess("com.ijioio.aes.core.serialization.SerializationReader");

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
		public default TypeName getImplementationType() {
			return getType();
		}

		/**
		 * Returns list of parameter type names of the type.
		 * 
		 * @return list of parameter type names
		 */
		public default List<TypeName> getParameters() {
			return Collections.emptyList();
		}

		public default List<TypeName> getParameterizedParameters() {
			return Collections.emptyList();
		}

		public default TypeName getParameterizedType() {

			TypeName type = getType();
			List<TypeName> parameters = getParameterizedParameters();

			return parameters.size() > 0 ? ParameterizedTypeName.get((ClassName) type,
					parameters.stream().toArray(size -> new TypeName[size])) : type;
		}

		public default TypeName getParameterizedImplementationType() {

			TypeName type = getImplementationType();
			List<TypeName> parameters = getParameterizedParameters();

			return parameters.size() > 0 ? ParameterizedTypeName.get((ClassName) type,
					parameters.stream().toArray(size -> new TypeName[size])) : type;
		}

		public default boolean isBoolean() {
			return getType() == BOOLEAN_TYPE_NAME;
		}

		public default boolean isReference() {
			return getType() == ENTITY_REFERENCE_TYPE_NAME;
		}
	}

	/**
	 * Type handler of boolean type.
	 */
	public static final CodeGenTypeHandler BOOLEAN_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return BOOLEAN_TYPE_NAME;
		}
	};

	/**
	 * Type handler of char type.
	 */
	public static final CodeGenTypeHandler CHAR_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return CHAR_TYPE_NAME;
		}
	};

	/**
	 * Type handler of byte type.
	 */
	public static final CodeGenTypeHandler BYTE_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return BYTE_TYPE_NAME;
		}
	};

	/**
	 * Type handler of short type.
	 */
	public static final CodeGenTypeHandler SHORT_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return SHORT_TYPE_NAME;
		}
	};

	/**
	 * Type handler of int type.
	 */
	public static final CodeGenTypeHandler INT_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return INT_TYPE_NAME;
		}
	};

	/**
	 * Type handler of long type.
	 */
	public static final CodeGenTypeHandler LONG_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return LONG_TYPE_NAME;
		}
	};

	/**
	 * Type handler of float type.
	 */
	public static final CodeGenTypeHandler FLOAT_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return FLOAT_TYPE_NAME;
		}
	};

	/**
	 * Type handler of double type.
	 */
	public static final CodeGenTypeHandler DOUBLE_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return DOUBLE_TYPE_NAME;
		}
	};

	/**
	 * Type handler of byte array type.
	 */
	public static final CodeGenTypeHandler BYTE_ARRAY_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return BYTE_ARRAY_TYPE_NAME;
		}
	};

	/**
	 * Type handler of {@link String} type.
	 */
	public static final CodeGenTypeHandler STRING_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return STRING_TYPE_NAME;
		}
	};

	/**
	 * Type handler of {@link Instant} type.
	 */
	public static final CodeGenTypeHandler INSTANT_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return INSTANT_TYPE_NAME;
		}
	};

	/**
	 * Type handler of {@link LocalDateTime} type.
	 */
	public static final CodeGenTypeHandler LOCAL_DATE_TIME_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return LOCAL_DATE_TIME_TYPE_NAME;
		}
	};

	/**
	 * Type handler of {@link LocalDate} type.
	 */
	public static final CodeGenTypeHandler LOCAL_DATE_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return LOCAL_DATE_TYPE_NAME;
		}
	};

	/**
	 * Type handler of {@link LocalTime} type.
	 */
	public static final CodeGenTypeHandler LOCAL_TIME_TYPE_HANDLER = new CodeGenTypeHandler() {

		@Override
		public TypeName getType() {
			return LOCAL_TIME_TYPE_NAME;
		}
	};

	public static TypeMetadata resolveType(String type, Map<String, TypeMetadata> types) {

		return Optional.ofNullable(resolveType(types.get(type), types))
				.orElse(TypeMetadata.of(type, type, Collections.emptyList()));
	}

	private static TypeMetadata resolveType(TypeMetadata type, Map<String, TypeMetadata> types) {

		return Optional.ofNullable(type).map(item -> resolveType(types.get(item.getType()), types)).orElse(type);
	}

	public static CodeGenTypeHandler getTypeHandler(String type, Map<String, TypeMetadata> types) {

		return getTypeHandler(resolveType(type, types), types);
	}

	public static CodeGenTypeHandler getTypeHandler(TypeMetadata type, Map<String, TypeMetadata> types) {

		String name = type.getType();

		if (name.equals(Type.BOOLEAN) || name.equals(TypeUtil.BOOLEAN_TYPE_NAME)) {
			return BOOLEAN_TYPE_HANDLER;
		}

		if (name.equals(Type.CHAR) || name.equals(TypeUtil.CHAR_TYPE_NAME)) {
			return CHAR_TYPE_HANDLER;
		}

		if (name.equals(Type.BYTE) || name.equals(TypeUtil.BYTE_TYPE_NAME)) {
			return BYTE_TYPE_HANDLER;
		}

		if (name.equals(Type.SHORT) || name.equals(TypeUtil.SHORT_TYPE_NAME)) {
			return SHORT_TYPE_HANDLER;
		}

		if (name.equals(Type.INT) || name.equals(TypeUtil.INT_TYPE_NAME)) {
			return INT_TYPE_HANDLER;
		}

		if (name.equals(Type.LONG) || name.equals(TypeUtil.LONG_TYPE_NAME)) {
			return LONG_TYPE_HANDLER;
		}

		if (name.equals(Type.FLOAT) || name.equals(TypeUtil.FLOAT_TYPE_NAME)) {
			return FLOAT_TYPE_HANDLER;
		}

		if (name.equals(Type.DOUBLE) || name.equals(TypeUtil.DOUBLE_TYPE_NAME)) {
			return DOUBLE_TYPE_HANDLER;
		}

		if (name.equals(Type.BYTE_ARRAY) || name.equals(TypeUtil.BYTE_ARRAY_TYPE_NAME)) {
			return BYTE_ARRAY_TYPE_HANDLER;
		}

		if (name.equals(Type.STRING) || name.equals(TypeUtil.STRING_TYPE_NAME)) {
			return STRING_TYPE_HANDLER;
		}

		if (name.equals(Type.INSTANT) || name.equals(TypeUtil.INSTANT_TYPE_NAME)) {
			return INSTANT_TYPE_HANDLER;
		}

		if (name.equals(Type.LOCAL_DATE_TIME) || name.equals(TypeUtil.LOCAL_DATE_TIME_TYPE_NAME)) {
			return LOCAL_DATE_TIME_TYPE_HANDLER;
		}

		if (name.equals(Type.LOCAL_DATE) || name.equals(TypeUtil.LOCAL_DATE_TYPE_NAME)) {
			return LOCAL_DATE_TYPE_HANDLER;
		}

		if (name.equals(Type.LOCAL_TIME) || name.equals(TypeUtil.LOCAL_TIME_TYPE_NAME)) {
			return LOCAL_TIME_TYPE_HANDLER;
		}

		return new CodeGenTypeHandler() {

			@Override
			public TypeName getType() {

				if (name.equals(Type.CLASS) || name.equals(TypeUtil.CLASS_TYPE_NAME)) {
					return CLASS_TYPE_NAME;
				}

				if (name.equals(Type.LIST) || name.equals(TypeUtil.LIST_TYPE_NAME)) {
					return LIST_TYPE_NAME;
				}

				if (name.equals(Type.SET) || name.equals(TypeUtil.SET_TYPE_NAME)) {
					return SET_TYPE_NAME;
				}

				if (name.equals(Type.MAP) || name.equals(TypeUtil.MAP_TYPE_NAME)) {
					return MAP_TYPE_NAME;
				}

				if (name.equals(Type.ENTITY_REFERENCE) || name.equals(TypeUtil.ENTITY_REFERENCE_TYPE_NAME)) {
					return ENTITY_REFERENCE_TYPE_NAME;
				}

				return ClassName.bestGuess(name);
			}

			@Override
			public TypeName getImplementationType() {

				if (name.equals(Type.LIST) || name.equals(TypeUtil.LIST_TYPE_NAME)) {
					return ARRAY_LIST_TYPE_NAME;
				}

				if (name.equals(Type.SET) || name.equals(TypeUtil.SET_TYPE_NAME)) {
					return LINKED_HASH_SET_TYPE_NAME;
				}

				if (name.equals(Type.MAP) || name.equals(TypeUtil.MAP_TYPE_NAME)) {
					return LINKED_HASH_MAP_TYPE_NAME;
				}

				return CodeGenTypeHandler.super.getImplementationType();
			}

			@Override
			public List<TypeName> getParameters() {

				return type.getParameters().stream().map(item -> resolveType(item, types))
						.map(item -> getTypeHandler(item, types).getType()).collect(Collectors.toList());
			}

			@Override
			public List<TypeName> getParameterizedParameters() {

				return type.getParameters().stream().map(item -> resolveType(item, types))
						.map(item -> getTypeHandler(item, types).getParameterizedType()).collect(Collectors.toList());
			}
		};
	}
}
