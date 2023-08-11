package com.ijioio.aes.core;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Objects;

public abstract class TypeReference<T> {

	public static <T> TypeReference<T> of(Class<T> type) {
		return new SimpleTypeReference<>(type);
	}

	public static <T> TypeReference<T> of(Type type) {
		return new SimpleTypeReference<>(type);
	}

	public static <T> TypeReference<Class<T>> classOf(TypeReference<T> type) {
		return new SimpleTypeReference<>(new WrappedParameterizedType(null, Class.class, type.getType()));
	}

	private final Type type;

	protected TypeReference() {

		Type superType = getClass().getGenericSuperclass();

		if (superType instanceof Class<?>) {
			throw new IllegalArgumentException("generic parameters for type reference is not indicated");
		}

		this.type = ((ParameterizedType) superType).getActualTypeArguments()[0];

//		checkType(type);
	}

	protected TypeReference(Type type) {

		this.type = type;

//		checkType(type);
	}

	private void checkType(Type type) {

		new TypeVisitor<Void>() {

			@Override
			Void visitTypeVariable(TypeVariable<?> type) {
				return visit(type.getBounds()[0]);
			}

			@Override
			Void visitWildcardType(WildcardType type) {
				return visit(type.getUpperBounds()[0]);
			}

			@Override
			Void visitParameterizedType(ParameterizedType type) {

				for (Type parameterType : type.getActualTypeArguments()) {
					visit(parameterType);
				}

				return null;
			}

			@Override
			Void visitClass(Class<?> type) {

				if (type.isArray()) {

					visit(type.getComponentType());

				} else {

					if (type.getTypeParameters().length > 0) {
						throw new IllegalArgumentException(
								String.format("generic parameters for %s type are not indicated", type.getTypeName()));
					}
				}

				return null;
			}

			@Override
			Void visitGenericArrayType(GenericArrayType type) {
				return visit(type.getGenericComponentType());
			}

		}.visit(type);
	}

	public Type getType() {
		return type;
	}

	@SuppressWarnings("unchecked")
	public Class<T> getRawType() {

		return (Class<T>) new TypeVisitor<Class<?>>() {

			@Override
			Class<?> visitTypeVariable(TypeVariable<?> type) {
				return visit(type.getBounds()[0]);
			}

			@Override
			Class<?> visitWildcardType(WildcardType type) {
				return visit(type.getUpperBounds()[0]);
			}

			@Override
			Class<?> visitParameterizedType(ParameterizedType type) {
				return (Class<?>) type.getRawType();
			}

			@Override
			Class<?> visitClass(Class<?> type) {
				return type;
			}

			@Override
			Class<?> visitGenericArrayType(GenericArrayType type) {
				return Array.newInstance(TypeReference.of(type.getGenericComponentType()).getRawType(), 0).getClass();
			}

		}.visit(type);
	}

	@SuppressWarnings("rawtypes")
	public TypeReference[] getParameterTypes() {

		return new TypeVisitor<TypeReference<?>[]>() {

			@Override
			TypeReference[] visitTypeVariable(TypeVariable<?> type) {
				return visit(type.getBounds()[0]);
			}

			@Override
			TypeReference[] visitWildcardType(WildcardType type) {
				return visit(type.getUpperBounds()[0]);
			}

			@Override
			TypeReference[] visitParameterizedType(ParameterizedType type) {
				return Arrays.stream(type.getActualTypeArguments()).map(item -> TypeReference.of(item))
						.toArray(size -> new TypeReference[size]);
			}

			@Override
			TypeReference[] visitClass(Class<?> type) {
				return new TypeReference[0];
			}

			@Override
			TypeReference[] visitGenericArrayType(GenericArrayType type) {
				return visit(type.getGenericComponentType());
			}

		}.visit(type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		TypeReference<?> other = (TypeReference<?>) obj;

		return Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "TypeReference [type=" + type + "]";
	}

	private static final class SimpleTypeReference<T> extends TypeReference<T> {

		private SimpleTypeReference(Type type) {
			super(type);
		}
	}

	private static class TypeVisitor<R> {

		public final R visit(Type type) {

			if (type instanceof TypeVariable) {
				return visitTypeVariable((TypeVariable<?>) type);
			} else if (type instanceof WildcardType) {
				return visitWildcardType((WildcardType) type);
			} else if (type instanceof ParameterizedType) {
				return visitParameterizedType((ParameterizedType) type);
			} else if (type instanceof Class) {
				return visitClass((Class<?>) type);
			} else if (type instanceof GenericArrayType) {
				return visitGenericArrayType((GenericArrayType) type);
			} else {
				throw new AssertionError(String.format("type %s is not supported", type));
			}
		}

		R visitTypeVariable(TypeVariable<?> type) {
			return getDefaultValue();
		}

		R visitWildcardType(WildcardType type) {
			return getDefaultValue();
		}

		R visitParameterizedType(ParameterizedType type) {
			return getDefaultValue();
		}

		R visitClass(Class<?> type) {
			return getDefaultValue();
		}

		R visitGenericArrayType(GenericArrayType type) {
			return getDefaultValue();
		}

		R getDefaultValue() {
			return null;
		}
	}

	private static class WrappedParameterizedType implements ParameterizedType {

		private final Type ownerType;

		private final Class<?> rawType;

		private final Type[] typeArguments;

		private WrappedParameterizedType(Type ownerType, Class<?> rawType, Type... typeArguments) {

			this.ownerType = ownerType;
			this.rawType = rawType;
			this.typeArguments = typeArguments;
		}

		@Override
		public Type[] getActualTypeArguments() {
			return typeArguments;
		}

		@Override
		public Type getRawType() {
			return rawType;
		}

		@Override
		public Type getOwnerType() {
			return ownerType;
		}

		@Override
		public int hashCode() {

			final int prime = 31;

			int result = 1;

			result = prime * result + Arrays.hashCode(typeArguments);
			result = prime * result + Objects.hash(ownerType, rawType);

			return result;
		}

		@Override
		public boolean equals(Object obj) {

			if (this == obj) {
				return true;
			}

			if (obj == null) {
				return false;
			}

			if (getClass() != obj.getClass()) {
				return false;
			}

			ParameterizedType other = (ParameterizedType) obj;

			return Objects.equals(ownerType, other.getOwnerType()) && Objects.equals(rawType, other.getRawType())
					&& Arrays.equals(typeArguments, other.getActualTypeArguments());
		}

		@Override
		public String toString() {
			return "WrappedParameterizedType [ownerType=" + ownerType + ", rawType=" + rawType + ", typeArguments="
					+ Arrays.toString(typeArguments) + "]";
		}
	}
}
