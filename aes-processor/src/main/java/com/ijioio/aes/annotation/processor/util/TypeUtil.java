package com.ijioio.aes.annotation.processor.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.ijioio.aes.annotation.Type;

/** Helper class for type. */
public class TypeUtil {

	/**
	 * Fully qualified name for boolean type.
	 */
	public static final String BOOLEAN_TYPE_NAME = boolean.class.getName();

	/**
	 * Fully qualified name for char type.
	 */
	public static final String CHAR_TYPE_NAME = char.class.getName();

	/**
	 * Fully qualified name for byte type.
	 */
	public static final String BYTE_TYPE_NAME = byte.class.getName();

	/**
	 * Fully qualified name for short type.
	 */
	public static final String SHORT_TYPE_NAME = short.class.getName();

	/**
	 * Fully qualified name for int type.
	 */
	public static final String INT_TYPE_NAME = int.class.getName();

	/**
	 * Fully qualified name for long type.
	 */
	public static final String LONG_TYPE_NAME = long.class.getName();

	/**
	 * Fully qualified name for float type.
	 */
	public static final String FLOAT_TYPE_NAME = float.class.getName();

	/**
	 * Fully qualified name for double type.
	 */
	public static final String DOUBLE_TYPE_NAME = double.class.getName();

	/**
	 * Fully qualified name for byte array type.
	 */
	public static final String BYTE_ARRAY_TYPE_NAME = byte[].class.getName();

	/**
	 * Fully qualified name for {@link String} type.
	 */
	public static final String STRING_TYPE_NAME = String.class.getName();

	/**
	 * Fully qualified name for {@link Instant} type.
	 */
	public static final String INSTANT_TYPE_NAME = Instant.class.getName();

	/**
	 * Fully qualified name for {@link LocalDate} type.
	 */
	public static final String LOCAL_DATE_TYPE_NAME = LocalDate.class.getName();

	/**
	 * Fully qualified name for {@link LocalTime} type.
	 */
	public static final String LOCAL_TIME_TYPE_NAME = LocalTime.class.getName();

	/**
	 * Fully qualified name for {@link LocalDateTime} type.
	 */
	public static final String LOCAL_DATE_TIME_TYPE_NAME = LocalDateTime.class.getName();

	/**
	 * Fully qualified name for {@link Class} type.
	 */
	public static final String CLASS_TYPE_NAME = Class.class.getName();

	/**
	 * Fully qualified name for {@link List} type.
	 */
	public static final String LIST_TYPE_NAME = List.class.getName();

	/**
	 * Fully qualified name for {@link Set} type.
	 */
	public static final String SET_TYPE_NAME = Set.class.getName();

	/**
	 * Fully qualified name for {@link Map} type.
	 */
	public static final String MAP_TYPE_NAME = Map.class.getName();

	/**
	 * Fully qualified name for base entity type.
	 */
	public static final String BASE_ENTITY_TYPE_NAME = "com.ijioio.aes.core.BaseEntity";

	/**
	 * Fully qualified name for base entity index type.
	 */
	public static final String BASE_ENTITY_INDEX_TYPE_NAME = "com.ijioio.aes.core.BaseEntityIndex";

	/**
	 * Fully qualified name for property type.
	 */
	public static final String PROPERTY_TYPE_NAME = "com.ijioio.aes.core.Property";

	/**
	 * Fully qualified name for property writer.
	 */
	public static final String PROPERTY_WRITER_TYPE_NAME = "com.ijioio.aes.core.PropertyWriter";

	/**
	 * Fully qualified name for property reader.
	 */
	public static final String PROPERTY_READER_TYPE_NAME = "com.ijioio.aes.core.PropertyReader";

	/**
	 * Fully qualified name for entity reference type.
	 */
	public static final String ENTITY_REFERENCE_TYPE_NAME = "com.ijioio.aes.core.EntityReference";

	/**
	 * Fully qualified name for serialization context.
	 */
	public static final String SERIALIZATION_CONTEXT_TYPE_NAME = "com.ijioio.aes.core.serialization.SerializationContext";

	/**
	 * Fully qualified name for serialization handler.
	 */
	public static final String SERIALIZATION_HANDLER_TYPE_NAME = "com.ijioio.aes.core.serialization.SerializationHandler";

	/**
	 * Fully qualified name for serialization exception.
	 */
	public static final String SERIALIZATION_EXCEPTION_TYPE_NAME = "com.ijioio.aes.core.serialization.SerializationException";

	/**
	 * Fully qualified name for serialization writer.
	 */
	public static final String SERIALIZATION_WRITER_TYPE_NAME = "com.ijioio.aes.core.serialization.SerializationWriter";

	/**
	 * Fully qualified name for serialization reader.
	 */
	public static final String SERIALIZATION_READER_TYPE_NAME = "com.ijioio.aes.core.serialization.SerializationReader";

	/**
	 * Fully qualified name for persistence context.
	 */
	public static final String PERSISTENCE_CONTEXT_TYPE_NAME = "com.ijioio.aes.core.persistence.PersistenceContext";

	/**
	 * Fully qualified name for persistence handler.
	 */
	public static final String PERSISTENCE_HANDLER_TYPE_NAME = "com.ijioio.aes.core.persistence.PersistenceHandler";

	/**
	 * Fully qualified name for persistence exception.
	 */
	public static final String PERSISTENCE_EXCEPTION_TYPE_NAME = "com.ijioio.aes.core.persistence.PersistenceException";

	private static final Pattern pattern = Pattern.compile("\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*"
			+ "(\\.\\p{javaJavaIdentifierStart}\\p{javaJavaIdentifierPart}*)*");

	/**
	 * Type handler providing some various actions.
	 */
	public static interface TypeHandler {

		/**
		 * Defines if type is immutable.
		 * 
		 * @return {@code true} if type is immutable, {@code false} otherwise
		 */
		public boolean isImmutable();

		/**
		 * Defines if type is a valid identifier.
		 * 
		 * @return {@code true} if type is valid identifier, {@code false} otherwise
		 */
		public boolean isValidIdentifier();
	}

	/**
	 * Type handler of boolean type.
	 */
	public static final TypeHandler BOOLEAN_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of char type.
	 */
	public static final TypeHandler CHAR_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of byte type.
	 */
	public static final TypeHandler BYTE_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of short type.
	 */
	public static final TypeHandler SHORT_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of int type.
	 */
	public static final TypeHandler INT_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of long type.
	 */
	public static final TypeHandler LONG_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of float type.
	 */
	public static final TypeHandler FLOAT_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of double type.
	 */
	public static final TypeHandler DOUBLE_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of byte array type.
	 */
	public static final TypeHandler BYTE_ARRAY_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of {@link String} type.
	 */
	public static final TypeHandler STRING_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of {@link Instant} type.
	 */
	public static final TypeHandler INSTANT_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of {@link LocalDate} type.
	 */
	public static final TypeHandler LOCAL_DATE_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of {@link LocalTime} type.
	 */
	public static final TypeHandler LOCAL_TIME_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of {@link LocalDateTime} type.
	 */
	public static final TypeHandler LOCAL_DATE_TIME_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of {@link Class} type.
	 */
	public static final TypeHandler CLASS_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return true;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of {@link List} type.
	 */
	public static final TypeHandler LIST_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return false;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of {@link Set} type.
	 */
	public static final TypeHandler SET_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return false;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of {@link Map} type.
	 */
	public static final TypeHandler MAP_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return false;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Type handler of entity reference type.
	 */
	public static final TypeHandler ENTITY_REFERENCE_TYPE_HANDLER = new TypeHandler() {

		@Override
		public boolean isImmutable() {
			return false;
		}

		@Override
		public boolean isValidIdentifier() {
			return true;
		}
	};

	/**
	 * Defines if type with indicated {@code name} is immutable. The {@code name}
	 * can be either type alias or type fully qualified name.
	 * 
	 * @param name of the type
	 * @return {@code true} if type is immutable, {@code false} otherwise
	 */
	public static boolean isImmutable(String name) {
		return getTypeHandler(name).isImmutable();
	}

	/**
	 * Defines if type with indicated {@code name} is valid identifier. The
	 * {@code name} can be either type alias or type fully qualified name.
	 * 
	 * @param name of the type
	 * @return {@code true} if type is valid identifier, {@code false} otherwise
	 */
	public static boolean isValidIdentifier(String name) {
		return getTypeHandler(name).isValidIdentifier();
	}

	private static TypeHandler getTypeHandler(String name) {

		if (name.equals(Type.BOOLEAN) || name.equals(BOOLEAN_TYPE_NAME)) {
			return BOOLEAN_TYPE_HANDLER;
		}

		if (name.equals(Type.CHAR) || name.equals(CHAR_TYPE_NAME)) {
			return CHAR_TYPE_HANDLER;
		}

		if (name.equals(Type.BYTE) || name.equals(BYTE_TYPE_NAME)) {
			return BYTE_TYPE_HANDLER;
		}

		if (name.equals(Type.SHORT) || name.equals(SHORT_TYPE_NAME)) {
			return SHORT_TYPE_HANDLER;
		}

		if (name.equals(Type.INT) || name.equals(INT_TYPE_NAME)) {
			return INT_TYPE_HANDLER;
		}

		if (name.equals(Type.LONG) || name.equals(LONG_TYPE_NAME)) {
			return LONG_TYPE_HANDLER;
		}

		if (name.equals(Type.FLOAT) || name.equals(FLOAT_TYPE_NAME)) {
			return FLOAT_TYPE_HANDLER;
		}

		if (name.equals(Type.DOUBLE) || name.equals(DOUBLE_TYPE_NAME)) {
			return DOUBLE_TYPE_HANDLER;
		}

		if (name.equals(Type.BYTE_ARRAY) || name.equals(BYTE_ARRAY_TYPE_NAME)) {
			return BYTE_ARRAY_TYPE_HANDLER;
		}

		if (name.equals(Type.STRING) || name.equals(STRING_TYPE_NAME)) {
			return STRING_TYPE_HANDLER;
		}

		if (name.equals(Type.INSTANT) || name.equals(INSTANT_TYPE_NAME)) {
			return INSTANT_TYPE_HANDLER;
		}

		if (name.equals(Type.LOCAL_DATE) || name.equals(LOCAL_DATE_TYPE_NAME)) {
			return LOCAL_DATE_TYPE_HANDLER;
		}

		if (name.equals(Type.LOCAL_TIME) || name.equals(LOCAL_TIME_TYPE_NAME)) {
			return LOCAL_TIME_TYPE_HANDLER;
		}

		if (name.equals(Type.LOCAL_DATE_TIME) || name.equals(LOCAL_DATE_TIME_TYPE_NAME)) {
			return LOCAL_DATE_TIME_TYPE_HANDLER;
		}

		if (name.equals(Type.CLASS) || name.equals(CLASS_TYPE_NAME)) {
			return CLASS_TYPE_HANDLER;
		}

		if (name.equals(Type.LIST) || name.equals(LIST_TYPE_NAME)) {
			return LIST_TYPE_HANDLER;
		}

		if (name.equals(Type.SET) || name.equals(SET_TYPE_NAME)) {
			return SET_TYPE_HANDLER;
		}

		if (name.equals(Type.MAP) || name.equals(MAP_TYPE_NAME)) {
			return MAP_TYPE_HANDLER;
		}

		if (name.equals(Type.ENTITY_REFERENCE) || name.equals(ENTITY_REFERENCE_TYPE_NAME)) {
			return ENTITY_REFERENCE_TYPE_HANDLER;
		}

		return new TypeHandler() {

			@Override
			public boolean isImmutable() {
				return false;
			}

			@Override
			public boolean isValidIdentifier() {
				return pattern.matcher(name).matches();
			}
		};
	}
}
