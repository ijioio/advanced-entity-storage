package com.ijioio.aes.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Search criterions are major building blocks of {@link SearchQuery}. There are
 * four types of search criterions:
 *
 * <ul>
 * <li>{@link SimpleSearchCriterion}
 * <li>{@link NotSearchCriterion}
 * <li>{@link AndSearchCriterion}
 * <li>{@link OrSearchCriterion}
 * </ul>
 */
public class SearchCriterion {

	/**
	 * Class represents simple search criterion. It holds simple search criterion
	 * data, i.e. {@code property}, {@code operation} and {@code value}.
	 */
	public static class SimpleSearchCriterion<P, V> extends SearchCriterion {

		public static <P> SimpleSearchCriterion<P, P> eq(Property<P> property, P value) {
			return new SimpleSearchCriterion<>(property, Operation.EQUALS, property.getType(), value);
		}

		public static <P, V> SimpleSearchCriterion<P, V> eq(Property<P> property, TypeReference<V> type, V value) {
			return new SimpleSearchCriterion<>(property, Operation.EQUALS, type, value);
		}

		public static <P> SimpleSearchCriterion<P, P> ne(Property<P> property, P value) {
			return new SimpleSearchCriterion<>(property, Operation.NOT_EQUALS, property.getType(), value);
		}

		public static <P, V> SimpleSearchCriterion<P, V> ne(Property<P> property, TypeReference<V> type, V value) {
			return new SimpleSearchCriterion<>(property, Operation.NOT_EQUALS, type, value);
		}

		public static <P> SimpleSearchCriterion<P, P> gt(Property<P> property, P value) {
			return new SimpleSearchCriterion<>(property, Operation.GREATER, property.getType(), value);
		}

		public static <P, V> SimpleSearchCriterion<P, V> gt(Property<P> property, TypeReference<V> type, V value) {
			return new SimpleSearchCriterion<>(property, Operation.GREATER, type, value);
		}

		public static <P> SimpleSearchCriterion<P, P> ge(Property<P> property, P value) {
			return new SimpleSearchCriterion<>(property, Operation.GREATER_OR_EQUALS, property.getType(), value);
		}

		public static <P, V> SimpleSearchCriterion<P, V> ge(Property<P> property, TypeReference<V> type, V value) {
			return new SimpleSearchCriterion<>(property, Operation.GREATER_OR_EQUALS, type, value);
		}

		public static <P> SimpleSearchCriterion<P, P> lt(Property<P> property, P value) {
			return new SimpleSearchCriterion<>(property, Operation.LOWER, property.getType(), value);
		}

		public static <P, V> SimpleSearchCriterion<P, V> lt(Property<P> property, TypeReference<V> type, V value) {
			return new SimpleSearchCriterion<>(property, Operation.LOWER, type, value);
		}

		public static <P> SimpleSearchCriterion<P, P> le(Property<P> property, P value) {
			return new SimpleSearchCriterion<>(property, Operation.LOWER_OR_EQUALS, property.getType(), value);
		}

		public static <P, V> SimpleSearchCriterion<P, V> le(Property<P> property, TypeReference<V> type, V value) {
			return new SimpleSearchCriterion<>(property, Operation.LOWER_OR_EQUALS, type, value);
		}

		public static <P> SimpleSearchCriterion<P, P> of(Property<P> property, Operation operation, P value) {
			return new SimpleSearchCriterion<>(property, operation, property.getType(), value);
		}

		public static <P, V> SimpleSearchCriterion<P, V> of(Property<P> property, Operation operation,
				TypeReference<V> type, V value) {
			return new SimpleSearchCriterion<>(property, operation, type, value);
		}

		private final Property<P> property;

		private final Operation operation;

		private final TypeReference<V> type;

		private final V value;

		private SimpleSearchCriterion(Property<P> property, Operation operation, TypeReference<V> type, V value) {

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

		public TypeReference<V> getType() {
			return type;
		}

		public V getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "SimpleSearchCriterion [property=" + property + ", operation=" + operation + ", type=" + type
					+ ", value=" + value + "]";
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
