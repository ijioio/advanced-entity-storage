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
	 * data, i.e. {@code column}, {@code operation} and {@code value}.
	 */
	public static class SimpleSearchCriterion<T> extends SearchCriterion {

		public static <T> SimpleSearchCriterion<T> eq(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.EQUALS, value);
		}

		public static <T> SimpleSearchCriterion<T> ne(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.NOT_EQUALS, value);
		}

		public static <T> SimpleSearchCriterion<T> gt(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.GREATER, value);
		}

		public static <T> SimpleSearchCriterion<T> ge(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.GREATER_OR_EQUALS, value);
		}

		public static <T> SimpleSearchCriterion<T> lt(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.LOWER, value);
		}

		public static <T> SimpleSearchCriterion<T> le(Property<T> property, T value) {
			return new SimpleSearchCriterion<>(property, Operation.LOWER_OR_EQUALS, value);
		}

		public static <T> SimpleSearchCriterion<T> of(Property<T> property, Operation operation, T value) {
			return new SimpleSearchCriterion<>(property, operation, value);
		}

		private final Property<T> property;

		private final Operation operation;

		private final T value;

		private SimpleSearchCriterion(Property<T> property, Operation operation, T value) {

			this.property = property;
			this.operation = operation;
			this.value = value;
		}

		public Property<T> getProperty() {
			return property;
		}

		public Operation getOperation() {
			return operation;
		}

		public T getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "SimpleSearchCriterion [property=" + property + ", operation=" + operation + ", value=" + value
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
