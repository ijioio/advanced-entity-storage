package com.ijioio.aes.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.ijioio.aes.core.SearchCriterion.AndSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.ExistsSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.NotSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.OrSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.SimpleSearchCriterion;

/**
 * Class represents search query data. It holds all necessary data required for
 * searching {@link EntityIndex} entries, such as list of
 * {@link SearchCriterion}, sorting values and {@code offset} and {@code
 * limit} for pagination.
 *
 * <p>
 * Search query objects are immutable. To create search query it is preferable
 * to use {@link SearchQueryBuilder}.
 */
public class SearchQuery<I extends EntityIndex<?>> {

	public static <I extends EntityIndex<?>> SearchQuery<I> of(Class<I> type) {
		return new SearchQuery<>(type, null, Collections.emptyList(), Collections.emptyMap(), null, null);
	}

	public static <I extends EntityIndex<?>> SearchQuery<I> of(Class<I> type, String alias) {
		return new SearchQuery<>(type, alias, Collections.emptyList(), Collections.emptyMap(), null, null);
	}

	public static <I extends EntityIndex<?>> SearchQuery<I> of(Class<I> type, List<SearchCriterion> criterions) {
		return new SearchQuery<>(type, null, criterions, Collections.emptyMap(), null, null);
	}

	public static <I extends EntityIndex<?>> SearchQuery<I> of(Class<I> type, String alias,
			List<SearchCriterion> criterions) {
		return new SearchQuery<>(type, alias, criterions, Collections.emptyMap(), null, null);
	}

	public static <I extends EntityIndex<?>> SearchQuery<I> of(Class<I> type, List<SearchCriterion> criterions,
			Map<Property<?>, Order> sortings, Long offset, Long limit) {
		return new SearchQuery<>(type, null, criterions, sortings, offset, limit);
	}

	public static <I extends EntityIndex<?>> SearchQuery<I> of(Class<I> type, String alias,
			List<SearchCriterion> criterions, Map<Property<?>, Order> sortings, Long offset, Long limit) {
		return new SearchQuery<>(type, alias, criterions, sortings, offset, limit);
	}

	private final Class<I> type;

	private final String alias;

	private final List<SearchCriterion> criterions;

	private final Map<Property<?>, Order> sortings;

	private final Long offset;

	private final Long limit;

	private SearchQuery(Class<I> type, String alias, List<SearchCriterion> criterions, Map<Property<?>, Order> sortings,
			Long offset, Long limit) {

		this.type = type;
		this.alias = alias;
		this.criterions = List.copyOf(criterions);
		this.sortings = Map.copyOf(sortings);
		this.offset = offset;
		this.limit = limit;
	}

	public Class<I> getType() {
		return type;
	}

	public String getAlias() {
		return alias;
	}

	public List<SearchCriterion> getCriterions() {
		return criterions;
	}

	public Map<Property<?>, Order> getSortings() {
		return sortings;
	}

	public Long getOffset() {
		return offset;
	}

	public Long getLimit() {
		return limit;
	}

	@Override
	public String toString() {
		return "SearchQuery [type=" + type + ", alias=" + alias + ", criterions=" + criterions + ", sortings="
				+ sortings + ", offset=" + offset + ", limit=" + limit + "]";
	}

	/**
	 * Builder class for {@link SearchQuery} creation utilizing builder pattern.
	 * Used by {@link SearchQueryBuilder} to delegate group building routine.
	 */
	public static class SearchQueryGroupBuilder<P extends SearchQueryGroupBuilder<?>> {

		/**
		 * Creates {@link SearchQueryGroupBuilder} for search query of given
		 * {@code group} and having indicated {@code parent}.
		 *
		 * @param group to use
		 * @param parent builder
		 * @return search query group builder
		 */
		public static <P extends SearchQueryGroupBuilder<?>> SearchQueryGroupBuilder<P> of(P parent,
				Consumer<List<SearchCriterion>> consumer) {
			return new SearchQueryGroupBuilder<>(parent, consumer);
		}

		protected final List<SearchCriterion> criterions = new ArrayList<>();

		protected final P parent;

		protected final Consumer<List<SearchCriterion>> consumer;

		private SearchQueryGroupBuilder(P parent, Consumer<List<SearchCriterion>> consumer) {

			this.parent = parent;
			this.consumer = consumer;
		}

		/**
		 * Adds equals search criterion. It allows to check if value of indicated index
		 * {@code property} is equals to indicated {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> eq(Property<T> property, T value) {

			this.criterions.add(SimpleSearchCriterion.eq(property, value));
			return this;
		}

		/**
		 * Adds equals search criterion. It allows to check if value of indicated index
		 * {@code property} is equals to the value of indicated {@code reference}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> eq(Property<T> property, Property<T> reference) {

			this.criterions.add(SimpleSearchCriterion.eq(property, reference));
			return this;
		}

		/**
		 * Adds equals search criterion. It allows to check if value of indicated index
		 * {@code property} is equals to the value of indicated {@code reference}
		 * defined in a scope qualified by indicated {@code alias}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @param alias of the reference scope
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> eq(Property<T> property, Property<T> reference, String alias) {

			this.criterions.add(SimpleSearchCriterion.eq(property, reference, alias));
			return this;
		}

		/**
		 * Adds not equals search criterion. It allows to check if value of indicated
		 * index {@code property} is not equals to indicated reference {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> ne(Property<T> property, T value) {

			this.criterions.add(SimpleSearchCriterion.ne(property, value));
			return this;
		}

		/**
		 * Adds not equals search criterion. It allows to check if value of indicated
		 * index {@code property} is not equals to the value of indicated
		 * {@code reference}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> ne(Property<T> property, Property<T> reference) {

			this.criterions.add(SimpleSearchCriterion.ne(property, reference));
			return this;
		}

		/**
		 * Adds not equals search criterion. It allows to check if value of indicated
		 * index {@code property} is not equals to the value of indicated
		 * {@code reference} defined in a scope qualified by indicated {@code alias}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @param alias of the reference scope
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> ne(Property<T> property, Property<T> reference, String alias) {

			this.criterions.add(SimpleSearchCriterion.ne(property, reference, alias));
			return this;
		}

		/**
		 * Adds lower search criterion. It allows to check if value of indicated index
		 * {@code property} is lower than indicated {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> lt(Property<T> property, T value) {

			this.criterions.add(SimpleSearchCriterion.lt(property, value));
			return this;
		}

		/**
		 * Adds lower search criterion. It allows to check if value of indicated index
		 * {@code property} is lower than value of indicated {@code reference}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> lt(Property<T> property, Property<T> reference) {

			this.criterions.add(SimpleSearchCriterion.lt(property, reference));
			return this;
		}

		/**
		 * Adds lower search criterion. It allows to check if value of indicated index
		 * {@code property} is lower than value of indicated {@code reference} defined
		 * in a scope qualified by indicated {@code alias}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @param alias of the reference scope
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> lt(Property<T> property, Property<T> reference, String alias) {

			this.criterions.add(SimpleSearchCriterion.lt(property, reference, alias));
			return this;
		}

		/**
		 * Adds lower or equals search criterion. It allows to check if value of
		 * indicated index {@code property} is lower or equals than indicated
		 * {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> le(Property<T> property, T value) {

			this.criterions.add(SimpleSearchCriterion.le(property, value));
			return this;
		}

		/**
		 * Adds lower or equals search criterion. It allows to check if value of
		 * indicated index {@code property} is lower or equals than value of indicated
		 * {@code reference}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> le(Property<T> property, Property<T> reference) {

			this.criterions.add(SimpleSearchCriterion.le(property, reference));
			return this;
		}

		/**
		 * Adds lower or equals search criterion. It allows to check if value of
		 * indicated index {@code property} is lower or equals than value of indicated
		 * {@code reference} defined in a scope qualified by indicated {@code alias}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @param alias of the reference scope
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> le(Property<T> property, Property<T> reference, String alias) {

			this.criterions.add(SimpleSearchCriterion.le(property, reference, alias));
			return this;
		}

		/**
		 * Adds greater search criterion. It allows to check if value of indicated index
		 * {@code property} is greater than indicated {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> gt(Property<T> property, T value) {

			this.criterions.add(SimpleSearchCriterion.gt(property, value));
			return this;
		}

		/**
		 * Adds greater search criterion. It allows to check if value of indicated index
		 * {@code property} is greater than value of indicated {@code reference}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> gt(Property<T> property, Property<T> reference) {

			this.criterions.add(SimpleSearchCriterion.gt(property, reference));
			return this;
		}

		/**
		 * Adds greater search criterion. It allows to check if value of indicated index
		 * {@code property} is greater than value of indicated {@code reference} defined
		 * in a scope qualified by indicated {@code alias}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @param alias of the reference scope
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> gt(Property<T> property, Property<T> reference, String alias) {

			this.criterions.add(SimpleSearchCriterion.gt(property, reference, alias));
			return this;
		}

		/**
		 * Adds greater or equals search criterion. It allows to check if value of
		 * indicated index {@code property} is greater or equals than indicated
		 * {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> ge(Property<T> property, T value) {

			this.criterions.add(SimpleSearchCriterion.ge(property, value));
			return this;
		}

		/**
		 * Adds greater or equals search criterion. It allows to check if value of
		 * indicated index {@code property} is greater or equals than value of indicated
		 * {@code reference}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> ge(Property<T> property, Property<T> reference) {

			this.criterions.add(SimpleSearchCriterion.ge(property, reference));
			return this;
		}

		/**
		 * Adds greater or equals search criterion. It allows to check if value of
		 * indicated index {@code property} is greater or equals than value of indicated
		 * {@code reference} defined in a scope qualified by indicated {@code alias}.
		 *
		 * @param property to check
		 * @param reference of value to compare
		 * @param alias of the reference scope
		 * @return this builder object for chaining
		 */
		public <T> SearchQueryGroupBuilder<P> ge(Property<T> property, Property<T> reference, String alias) {

			this.criterions.add(SimpleSearchCriterion.ge(property, reference, alias));
			return this;
		}

		/**
		 * Adds any equals search criterion. It allows to check if any value of
		 * indicated collection index {@code property} is equals to indicated
		 * {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> anyeq(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.anyeq(property, value));
			return this;
		}

		/**
		 * Adds any not equals search criterion. It allows to check if all values of
		 * indicated collection index {@code property} is not equals to indicated
		 * {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> anyne(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.anyne(property, value));
			return this;
		}

		/**
		 * Adds any lower search criterion. It allows to check if any value of indicated
		 * collection index {@code property} is lower than indicated {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> anylt(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.anylt(property, value));
			return this;
		}

		/**
		 * Adds any lower or equals search criterion. It allows to check if any value of
		 * indicated collection index {@code property} is lower or equals to indicated
		 * {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> anyle(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.anyle(property, value));
			return this;
		}

		/**
		 * Adds any greater search criterion. It allows to check if any value of
		 * indicated collection index {@code property} is greater than indicated
		 * {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> anygt(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.anygt(property, value));
			return this;
		}

		/**
		 * Adds any greater or equals search criterion. It allows to check if any value
		 * of indicated collection index {@code property} is greater or equals to
		 * indicated {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> anyge(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.anyge(property, value));
			return this;
		}

		/**
		 * Adds all equals search criterion. It allows to check if all values of
		 * indicated collection index {@code property} are equals to indicated
		 * {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> alleq(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.alleq(property, value));
			return this;
		}

		/**
		 * Adds all not equals search criterion. It allows to check if all values of
		 * indicated collection index {@code property} are not equals to indicated
		 * {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> allne(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.allne(property, value));
			return this;
		}

		/**
		 * Adds all lower search criterion. It allows to check if all values of
		 * indicated collection index {@code property} are lower than indicated
		 * {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> alllt(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.alllt(property, value));
			return this;
		}

		/**
		 * Adds all lower or equals search criterion. It allows to check if all values
		 * of indicated collection index {@code property} are lower or equals to
		 * indicated {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> allle(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.allle(property, value));
			return this;
		}

		/**
		 * Adds all greater search criterion. It allows to check if all values of
		 * indicated collection index {@code property} are greater than indicated
		 * {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> allgt(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.allgt(property, value));
			return this;
		}

		/**
		 * Adds all greater or equals search criterion. It allows to check if all values
		 * of indicated collection index {@code property} are greater or equals to
		 * indicated {@code value}.
		 *
		 * @param property to check
		 * @param value to compare
		 * @return this builder object for chaining
		 */
		public <C extends Collection<T>, T> SearchQueryGroupBuilder<P> allge(Property<C> property, T value) {

			this.criterions.add(SimpleSearchCriterion.allge(property, value));
			return this;
		}

		/**
		 * Adds exists search criterion. It allows to check if search subquery has non
		 * empty results.
		 *
		 * @param type of the search subquery
		 * @return this builder object for chaining
		 */
		public <I extends EntityIndex<?>> SearchQueryGroupBuilder<? extends SearchQueryGroupBuilder<P>> exists(
				Class<I> type) {
			return SearchQueryGroupBuilder.of(this,
					items -> criterions.add(ExistsSearchCriterion.of(type, null, items)));
		}

		/**
		 * Adds exists search criterion. It allows to check if search subquery has non
		 * empty results.
		 *
		 * @param type of the search subquery
		 * @param alias of the type
		 * @return this builder object for chaining
		 */
		public <I extends EntityIndex<?>> SearchQueryGroupBuilder<? extends SearchQueryGroupBuilder<P>> exists(
				Class<I> type, String alias) {
			return SearchQueryGroupBuilder.of(this,
					items -> criterions.add(ExistsSearchCriterion.of(type, alias, items)));
		}

		/**
		 * Adds {@code NOT} search criterion populated with indicated
		 * {@code criterions}.
		 *
		 * @param criterions to add
		 * @return builder object for chaining
		 */
		public SearchQueryGroupBuilder<P> not(Collection<SearchCriterion> criterions) {

			this.criterions.add(NotSearchCriterion.of(criterions));
			return this;
		}

		/**
		 * Adds {@code NOT} search criterion.
		 *
		 * <p>
		 * Note that new group builder would be returned to build the complex search
		 * criterion. To return back to original builder call {@code end()} method
		 *
		 * @return builder object for chaining
		 */
		public SearchQueryGroupBuilder<? extends SearchQueryGroupBuilder<P>> not() {
			return SearchQueryGroupBuilder.of(this, items -> criterions.add(NotSearchCriterion.of(items)));
		}

		/**
		 * Adds {@code AND} search criterion populated with indicated
		 * {@code criterions}.
		 *
		 * @param criterions to add
		 * @return builder object for chaining
		 */
		public SearchQueryGroupBuilder<P> and(Collection<SearchCriterion> criterions) {

			this.criterions.add(AndSearchCriterion.of(criterions));
			return this;
		}

		/**
		 * Adds {@code AND} search criterion.
		 *
		 * <p>
		 * Note that new group builder would be returned to build the complex search
		 * criterion. To return back to original builder call {@code end()} method
		 *
		 * @return builder object for chaining
		 */
		public SearchQueryGroupBuilder<? extends SearchQueryGroupBuilder<P>> and() {
			return SearchQueryGroupBuilder.of(this, items -> criterions.add(AndSearchCriterion.of(items)));
		}

		/**
		 * Adds {@code OR} search criterion populated with indicated {@code criterions}.
		 *
		 * @param criterions to add
		 * @return builder object for chaining
		 */
		public SearchQueryGroupBuilder<P> or(Collection<SearchCriterion> criterions) {

			this.criterions.add(OrSearchCriterion.of(criterions));
			return this;
		}

		/**
		 * Adds {@code OR} search criterion.
		 *
		 * <p>
		 * Note that new group builder would be returned to build the complex search
		 * criterion. To return back to original builder call {@code end()} method
		 *
		 * @return builder object for chaining
		 */
		public SearchQueryGroupBuilder<? extends SearchQueryGroupBuilder<P>> or() {
			return SearchQueryGroupBuilder.of(this, items -> criterions.add(OrSearchCriterion.of(items)));
		}

		public P end() {

			consumer.accept(criterions);
			return parent;
		}
	}

	/**
	 * Builder class for {@link SearchQuery} creation utilizing builder pattern. Use
	 * series of {@code
	 * of(...)} methods to get builder first, then use {@code build()} method to
	 * build the {@link SearchQuery}. For example:
	 *
	 * <pre>
	 * SearchQueryBuilder.of(FooIndex.class) //
	 * 		.eq(Properties.type.name(), type) //
	 * 		.gt(Properties.amount.name(), amount) //
	 * 		.build();
	 * </pre>
	 */
	public static class SearchQueryBuilder<I extends EntityIndex<?>>
			extends SearchQueryGroupBuilder<SearchQueryGroupBuilder<?>> {

		/**
		 * Creates {@link SearchQueryBuilder} for search query of given {@code type}.
		 *
		 * @param type of the search query
		 * @return search query builder
		 */
		public static <I extends EntityIndex<?>> SearchQueryBuilder<I> of(Class<I> type) {
			return new SearchQueryBuilder<>(type, null, Collections.emptyList(), Collections.emptyMap(), null, null);
		}

		/**
		 * Creates {@link SearchQueryBuilder} for search query of given {@code type}
		 * with scope qualified by {@code alias}.
		 *
		 * @param type of the search query
		 * @param alias of the type scope
		 * @return search query builder
		 */
		public static <I extends EntityIndex<?>> SearchQueryBuilder<I> of(Class<I> type, String alias) {
			return new SearchQueryBuilder<>(type, alias, Collections.emptyList(), Collections.emptyMap(), null, null);
		}

		/**
		 * Creates {@link SearchQueryBuilder} for search query of given {@code type} and
		 * initialized with indicated {@code criterions}.
		 *
		 * @param type of the search query
		 * @param criterions value
		 * @return search query builder
		 */
		public static <I extends EntityIndex<?>> SearchQueryBuilder<I> of(Class<I> type,
				List<SearchCriterion> criterions) {
			return new SearchQueryBuilder<>(type, null, criterions, Collections.emptyMap(), null, null);
		}

		/**
		 * Creates {@link SearchQueryBuilder} for search query of given {@code type}
		 * with scope qualified by {@code alias} and initialized with indicated
		 * {@code criterions}.
		 *
		 * @param type of the search query
		 * @param alias of the type scope
		 * @param criterions value
		 * @return search query builder
		 */
		public static <I extends EntityIndex<?>> SearchQueryBuilder<I> of(Class<I> type, String alias,
				List<SearchCriterion> criterions) {
			return new SearchQueryBuilder<>(type, alias, criterions, Collections.emptyMap(), null, null);
		}

		/**
		 * Creates {@link SearchQueryBuilder} for search query of given {@code type} and
		 * initialized with indicated {@code criterions}, {@code sortings},
		 * {@code offset} and {@code limit}.
		 *
		 * @param type of the search query
		 * @param criterions value
		 * @param sortings value
		 * @param offset value
		 * @param limit value
		 * @return search query builder
		 */
		public static <I extends EntityIndex<?>> SearchQueryBuilder<I> of(Class<I> type,
				List<SearchCriterion> criterions, Map<Property<?>, Order> sortings, Long offset, Long limit) {
			return new SearchQueryBuilder<>(type, null, criterions, sortings, offset, limit);
		}

		/**
		 * Creates {@link SearchQueryBuilder} for search query of given {@code type}
		 * with scope qualified by {@code alias} and initialized with indicated
		 * {@code criterions}, {@code sortings}, {@code offset} and {@code limit}.
		 *
		 * @param type of the search query
		 * @param alias of the type scope
		 * @param criterions value
		 * @param sortings value
		 * @param offset value
		 * @param limit value
		 * @return search query builder
		 */
		public static <I extends EntityIndex<?>> SearchQueryBuilder<I> of(Class<I> type, String alias,
				List<SearchCriterion> criterions, Map<Property<?>, Order> sortings, Long offset, Long limit) {
			return new SearchQueryBuilder<>(type, alias, criterions, sortings, offset, limit);
		}

		/**
		 * Creates {@link SearchQueryBuilder} based on the given {@code query}.
		 *
		 * @param query to use as a base
		 * @return search query builder
		 */
		public static <I extends EntityIndex<?>> SearchQueryBuilder<I> of(SearchQuery<I> query) {
			return new SearchQueryBuilder<>(query.getType(), query.getAlias(), query.getCriterions(),
					query.getSortings(), query.getOffset(), query.getLimit());
		}

		private final Class<I> type;

		private final String alias;

		private final Map<Property<?>, Order> sortings = new LinkedHashMap<>();

		private Long offset;

		private Long limit;

		private SearchQueryBuilder(Class<I> type, String alias, List<SearchCriterion> criterions,
				Map<Property<?>, Order> sortings, Long offset, Long limit) {

			super(null, null);

			this.type = type;
			this.alias = alias;

			this.criterions.clear();
			this.criterions.addAll(criterions);

			this.sortings.clear();
			this.sortings.putAll(sortings);

			this.offset = offset;
			this.limit = limit;
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> eq(Property<T> property, T value) {
			return (SearchQueryBuilder<I>) super.eq(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> eq(Property<T> property, Property<T> reference) {
			return (SearchQueryBuilder<I>) super.eq(property, reference);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> eq(Property<T> property, Property<T> reference, String alias) {
			return (SearchQueryBuilder<I>) super.eq(property, reference, alias);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> ne(Property<T> property, T value) {
			return (SearchQueryBuilder<I>) super.ne(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> ne(Property<T> property, Property<T> reference) {
			return (SearchQueryBuilder<I>) super.ne(property, reference);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> ne(Property<T> property, Property<T> reference, String alias) {
			return (SearchQueryBuilder<I>) super.ne(property, reference, alias);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> lt(Property<T> property, T value) {
			return (SearchQueryBuilder<I>) super.lt(property, value);
		}

		@Override
		@SuppressWarnings("unchecked")
		public <T> SearchQueryBuilder<I> lt(Property<T> property, Property<T> reference) {
			return (SearchQueryBuilder<I>) super.lt(property, reference);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> lt(Property<T> property, Property<T> reference, String alias) {
			return (SearchQueryBuilder<I>) super.lt(property, reference, alias);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> le(Property<T> property, T value) {
			return (SearchQueryBuilder<I>) super.le(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> le(Property<T> property, Property<T> reference) {
			return (SearchQueryBuilder<I>) super.le(property, reference);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> le(Property<T> property, Property<T> reference, String alias) {
			return (SearchQueryBuilder<I>) super.le(property, reference, alias);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> gt(Property<T> property, T value) {
			return (SearchQueryBuilder<I>) super.gt(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> gt(Property<T> property, Property<T> reference) {
			return (SearchQueryBuilder<I>) super.gt(property, reference);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> gt(Property<T> property, Property<T> reference, String alias) {
			return (SearchQueryBuilder<I>) super.gt(property, reference, alias);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> ge(Property<T> property, T value) {
			return (SearchQueryBuilder<I>) super.ge(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> ge(Property<T> property, Property<T> reference) {
			return (SearchQueryBuilder<I>) super.ge(property, reference);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> SearchQueryBuilder<I> ge(Property<T> property, Property<T> reference, String alias) {
			return (SearchQueryBuilder<I>) super.ge(property, reference, alias);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> anyeq(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.anyeq(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> anyne(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.anyne(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> anylt(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.anylt(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> anyle(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.anyle(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> anygt(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.anygt(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> anyge(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.anyge(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> alleq(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.alleq(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> allne(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.allne(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> alllt(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.alllt(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> allle(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.allle(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> allgt(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.allgt(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <C extends Collection<T>, T> SearchQueryBuilder<I> allge(Property<C> property, T value) {
			return (SearchQueryBuilder<I>) super.allge(property, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <S extends EntityIndex<?>> SearchQueryGroupBuilder<SearchQueryBuilder<I>> exists(Class<S> type) {
			return (SearchQueryGroupBuilder<SearchQueryBuilder<I>>) super.exists(type);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <S extends EntityIndex<?>> SearchQueryGroupBuilder<SearchQueryBuilder<I>> exists(Class<S> type,
				String alias) {
			return (SearchQueryGroupBuilder<SearchQueryBuilder<I>>) super.exists(type, alias);
		}

		@SuppressWarnings("unchecked")
		@Override
		public SearchQueryBuilder<I> not(Collection<SearchCriterion> criterions) {
			return (SearchQueryBuilder<I>) super.not(criterions);
		}

		@SuppressWarnings("unchecked")
		@Override
		public SearchQueryGroupBuilder<SearchQueryBuilder<I>> not() {
			return (SearchQueryGroupBuilder<SearchQueryBuilder<I>>) super.not();
		}

		@SuppressWarnings("unchecked")
		@Override
		public SearchQueryBuilder<I> and(Collection<SearchCriterion> criterions) {
			return (SearchQueryBuilder<I>) super.and(criterions);
		}

		@SuppressWarnings("unchecked")
		@Override
		public SearchQueryGroupBuilder<SearchQueryBuilder<I>> and() {
			return (SearchQueryGroupBuilder<SearchQueryBuilder<I>>) super.and();
		}

		@SuppressWarnings("unchecked")
		@Override
		public SearchQueryBuilder<I> or(Collection<SearchCriterion> criterions) {
			return (SearchQueryBuilder<I>) super.or(criterions);
		}

		@SuppressWarnings("unchecked")
		@Override
		public SearchQueryGroupBuilder<SearchQueryBuilder<I>> or() {
			return (SearchQueryGroupBuilder<SearchQueryBuilder<I>>) super.or();
		}

		/**
		 * Adds sorting entry.
		 *
		 * @param property for sort
		 * @param order for sort
		 * @return builder object for chaining
		 */
		public SearchQueryBuilder<I> sorting(Property<?> property, Order order) {

			this.sortings.put(property, order);
			return this;
		}

		/**
		 * Adds offset.
		 *
		 * @param offset for pagination
		 * @return builder object for chaining
		 */
		public SearchQueryBuilder<I> offset(Long offset) {

			this.offset = offset;
			return this;
		}

		/**
		 * Adds limit.
		 *
		 * @param limit for pagination
		 * @return builder object for chaining
		 */
		public SearchQueryBuilder<I> limit(Long limit) {

			this.limit = limit;
			return this;
		}

		@Override
		public SearchQueryGroupBuilder<?> end() {
			throw new UnsupportedOperationException();
		}

		/**
		 * Builds the search query.
		 *
		 * @return search query
		 */
		public SearchQuery<I> build() {
			return SearchQuery.of(type, alias, criterions, sortings, offset, limit);
		}
	}
}
