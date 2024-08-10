package com.ijioio.aes.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ijioio.aes.core.SearchCriterion.SimpleSearchCriterion.Value.PlainValue;
import com.ijioio.aes.core.SearchCriterion.SimpleSearchCriterion.Value.ReferenceValue;

/**
 * Search criterions are major building blocks of {@link SearchQuery}. There are
 * following types of search criterions:
 *
 * <ul>
 * <li>{@link SimpleSearchCriterion}
 * <li>{@link ExistsSearchCriterion}
 * <li>{@link NotSearchCriterion}
 * <li>{@link AndSearchCriterion}
 * <li>{@link OrSearchCriterion}
 * </ul>
 */
public class SearchCriterion {

	/**
	 * Class represents simple search criterion. It holds simple search criterion
	 * data, i.e. {@code property}, {@code operation}, {@code type} of the value and
	 * {@code value}.
	 */
	public static class SimpleSearchCriterion<P, T> extends SearchCriterion {

		public static <T> SimpleSearchCriterion<T, T> eq(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.EQUALS, property.getType(), PlainValue.of(value));
		}

		public static <T> SimpleSearchCriterion<T, T> eq(Property<T> property, PropertyReference<T> value) {
			return new SimpleSearchCriterion<>(property, Operation.EQUALS, property.getType(),
					ReferenceValue.of(value));
		}

		public static <T> SimpleSearchCriterion<T, T> ne(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.NOT_EQUALS, property.getType(),
					PlainValue.of(value));
		}

		public static <T> SimpleSearchCriterion<T, T> ne(Property<T> property, PropertyReference<T> value) {
			return new SimpleSearchCriterion<>(property, Operation.NOT_EQUALS, property.getType(),
					ReferenceValue.of(value));
		}

		public static <T> SimpleSearchCriterion<T, T> lt(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.LOWER, property.getType(), PlainValue.of(value));
		}

		public static <T> SimpleSearchCriterion<T, T> lt(Property<T> property, PropertyReference<T> value) {
			return new SimpleSearchCriterion<>(property, Operation.LOWER, property.getType(), ReferenceValue.of(value));
		}

		public static <T> SimpleSearchCriterion<T, T> le(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.LOWER_OR_EQUALS, property.getType(),
					PlainValue.of(value));
		}

		public static <T> SimpleSearchCriterion<T, T> le(Property<T> property, PropertyReference<T> value) {
			return new SimpleSearchCriterion<>(property, Operation.LOWER_OR_EQUALS, property.getType(),
					ReferenceValue.of(value));
		}

		public static <T> SimpleSearchCriterion<T, T> gt(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.GREATER, property.getType(), PlainValue.of(value));
		}

		public static <T> SimpleSearchCriterion<T, T> gt(Property<T> property, PropertyReference<T> value) {
			return new SimpleSearchCriterion<>(property, Operation.GREATER, property.getType(),
					ReferenceValue.of(value));
		}

		public static <T> SimpleSearchCriterion<T, T> ge(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.GREATER_OR_EQUALS, property.getType(),
					PlainValue.of(value));
		}

		public static <T> SimpleSearchCriterion<T, T> ge(Property<T> property, PropertyReference<T> value) {
			return new SimpleSearchCriterion<>(property, Operation.GREATER_OR_EQUALS, property.getType(),
					ReferenceValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> anyeq(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ANY_EQUALS,
					property.getType().getParameterTypes()[0], PlainValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> anyne(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ANY_NOT_EQUALS,
					property.getType().getParameterTypes()[0], PlainValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> anylt(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ANY_LOWER, property.getType().getParameterTypes()[0],
					PlainValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> anyle(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ANY_LOWER_OR_EQUALS,
					property.getType().getParameterTypes()[0], PlainValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> anygt(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ANY_GREATER,
					property.getType().getParameterTypes()[0], PlainValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> anyge(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ANY_GREATER_OR_EQUALS,
					property.getType().getParameterTypes()[0], PlainValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> alleq(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ALL_EQUALS,
					property.getType().getParameterTypes()[0], PlainValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> allne(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ALL_NOT_EQUALS,
					property.getType().getParameterTypes()[0], PlainValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> alllt(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ALL_LOWER, property.getType().getParameterTypes()[0],
					PlainValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> allle(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ALL_LOWER_OR_EQUALS,
					property.getType().getParameterTypes()[0], PlainValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> allgt(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ALL_GREATER,
					property.getType().getParameterTypes()[0], PlainValue.of(value));
		}

		@SuppressWarnings("unchecked")
		public static <C extends Collection<T>, T> SimpleSearchCriterion<C, T> allge(Property<C> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.ALL_GREATER_OR_EQUALS,
					property.getType().getParameterTypes()[0], PlainValue.of(value));
		}

		public static class Value<T> {

			public static class PlainValue<T> extends Value<T> {

				public static <T> PlainValue<T> of(T value) {
					return new PlainValue<>(value);
				}

				private final T value;

				private PlainValue(T value) {
					this.value = value;
				}

				public T getValue() {
					return value;
				}

				@Override
				public String toString() {
					return "PlainValue [value=" + value + "]";
				}
			}

			public static class ReferenceValue<T> extends Value<T> {

				public static <T> ReferenceValue<T> of(PropertyReference<T> value) {
					return new ReferenceValue<>(value);
				}

				private final PropertyReference<T> value;

				private ReferenceValue(PropertyReference<T> value) {
					this.value = value;
				}

				public PropertyReference<T> getValue() {
					return value;
				}

				@Override
				public String toString() {
					return "ReferenceValue [value=" + value + "]";
				}
			}
		}

		private final Property<P> property;

		private final Operation operation;

		private final TypeReference<T> type;

		private final Value<T> value;

		private SimpleSearchCriterion(Property<P> property, Operation operation, TypeReference<T> type,
				Value<T> value) {

			this.property = property;
			this.operation = operation;
			this.type = type;
			this.value = value;
		}

		public Property<P> getProperty() {
			return property;
		}

		public Operation getOperation() {
			return operation;
		}

		public TypeReference<T> getType() {
			return type;
		}

		public Value<T> getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "SimpleSearchCriterion [property=" + property + ", operation=" + operation + ", type=" + type
					+ ", value=" + value + "]";
		}
	}

	/**
	 * Class represents complex search criterion using {@code EXISTS} semantics. It
	 * holds subquery data, i.e. {@code type} and {@code criterions}.
	 */
	public static class ExistsSearchCriterion<I extends EntityIndex<?>> extends SearchCriterion {

		public static <I extends EntityIndex<?>> ExistsSearchCriterion<I> of(Class<I> type, String namespace,
				Collection<SearchCriterion> criterions) {
			return new ExistsSearchCriterion<>(type, namespace, criterions);
		}

		private final Class<I> type;

		private final String namespace;

		private final List<SearchCriterion> criterions = new ArrayList<>();

		private ExistsSearchCriterion(Class<I> type, String namespace, Collection<SearchCriterion> criterions) {

			this.type = type;
			this.namespace = namespace;

			this.criterions.clear();
			this.criterions.addAll(criterions);
		}

		public Class<I> getType() {
			return type;
		}

		public String getNamespace() {
			return namespace;
		}

		public List<SearchCriterion> getCriterions() {
			return criterions;
		}

		@Override
		public String toString() {
			return "ExistsSearchCriterion [type=" + type + ", namespace=" + namespace + ", criterions=" + criterions
					+ "]";
		}
	}

	/**
	 * Class represents complex search criterion using {@code NOT} semantics. It
	 * holds list of other search criterions.
	 */
	public static class NotSearchCriterion extends SearchCriterion {

		public static NotSearchCriterion of(Collection<SearchCriterion> criterions) {
			return new NotSearchCriterion(criterions);
		}

		private final List<SearchCriterion> criterions = new ArrayList<>();

		private NotSearchCriterion(Collection<SearchCriterion> criterions) {

			this.criterions.clear();
			this.criterions.addAll(criterions);
		}

		public List<SearchCriterion> getCriterions() {
			return criterions;
		}

		@Override
		public String toString() {
			return "NotSearchCriterion [criterions=" + criterions + "]";
		}
	}

	/**
	 * Class represents complex search criterion using {@code AND} semantics. It
	 * holds list of other search criterions.
	 */
	public static class AndSearchCriterion extends SearchCriterion {

		public static AndSearchCriterion of(Collection<SearchCriterion> criterions) {
			return new AndSearchCriterion(criterions);
		}

		private final List<SearchCriterion> criterions = new ArrayList<>();

		private AndSearchCriterion(Collection<SearchCriterion> criterions) {

			this.criterions.clear();
			this.criterions.addAll(criterions);
		}

		public List<SearchCriterion> getCriterions() {
			return criterions;
		}

		@Override
		public String toString() {
			return "AndSearchCriterion [criterions=" + criterions + "]";
		}
	}

	/**
	 * Class represents complex search criterion using {@code OR} semantics. It
	 * holds list of other search criterions.
	 */
	public static class OrSearchCriterion extends SearchCriterion {

		public static OrSearchCriterion of(Collection<SearchCriterion> criterions) {
			return new OrSearchCriterion(criterions);
		}

		private final List<SearchCriterion> criterions = new ArrayList<>();

		private OrSearchCriterion(Collection<SearchCriterion> criterions) {

			this.criterions.clear();
			this.criterions.addAll(criterions);
		}

		public List<SearchCriterion> getCriterions() {
			return criterions;
		}

		@Override
		public String toString() {
			return "OrSearchCriterion [criterions=" + criterions + "]";
		}
	}
}
