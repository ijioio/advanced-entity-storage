package com.ijioio.aes.core.persistence.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.ijioio.aes.core.Entity;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.IntrospectionException;
import com.ijioio.aes.core.Operation;
import com.ijioio.aes.core.Order;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.SearchCriterion;
import com.ijioio.aes.core.SearchCriterion.AndSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.NotSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.OrSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.SimpleSearchCriterion;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.core.entity.storage.EntityData;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.PersistenceHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcBooleanPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcByteArrayPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcBytePersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcCharacterPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcClassPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcCollectionPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcDoublePersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcEntityReferencePersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcEnumPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcFloatPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcInstantPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcIntegerPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcLocalDatePersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcLocalDateTimePersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcLocalTimePersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcLongPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcShortPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcStringPersistenceValueHandler;
import com.ijioio.aes.core.util.TupleUtil.Pair;

public class JdbcPersistenceHandler implements PersistenceHandler {

	protected final ThreadLocal<Deque<JdbcPersistenceTransaction>> transactions = ThreadLocal
			.withInitial(() -> new LinkedList<>());

	protected final DataSource dataSource;

	protected final Map<String, JdbcPersistenceValueHandler<?>> handlers = new HashMap<>();

	public JdbcPersistenceHandler(DataSource dataSource) {

		this.dataSource = dataSource;

		registerValueHandler(new JdbcBooleanPersistenceValueHandler());
		registerValueHandler(new JdbcCharacterPersistenceValueHandler());
		registerValueHandler(new JdbcBytePersistenceValueHandler());
		registerValueHandler(new JdbcShortPersistenceValueHandler());
		registerValueHandler(new JdbcIntegerPersistenceValueHandler());
		registerValueHandler(new JdbcLongPersistenceValueHandler());
		registerValueHandler(new JdbcFloatPersistenceValueHandler());
		registerValueHandler(new JdbcDoublePersistenceValueHandler());
		registerValueHandler(new JdbcByteArrayPersistenceValueHandler());
		registerValueHandler(new JdbcStringPersistenceValueHandler());
		registerValueHandler(new JdbcInstantPersistenceValueHandler());
		registerValueHandler(new JdbcLocalDatePersistenceValueHandler());
		registerValueHandler(new JdbcLocalTimePersistenceValueHandler());
		registerValueHandler(new JdbcLocalDateTimePersistenceValueHandler());
		registerValueHandler(new JdbcEnumPersistenceValueHandler());
		registerValueHandler(new JdbcClassPersistenceValueHandler());
		registerValueHandler(new JdbcCollectionPersistenceValueHandler());
		registerValueHandler(new JdbcEntityReferencePersistenceValueHandler());
	}

	public <T> void registerValueHandler(JdbcPersistenceValueHandler<T> handler) {
		handlers.put(handler.getType().getName(), handler);
	}

	@SuppressWarnings("unchecked")
	public <T> JdbcPersistenceValueHandler<T> getValueHandler(Class<T> type) throws PersistenceException {

		JdbcPersistenceValueHandler<T> handler = (JdbcPersistenceValueHandler<T>) handlers.get(type.getName());

		if (handler == null) {

			if (Enum.class.isAssignableFrom(type)) {
				handler = (JdbcPersistenceValueHandler<T>) handlers.get(Enum.class.getName());
			} else if (Collection.class.isAssignableFrom(type)) {
				handler = (JdbcPersistenceValueHandler<T>) handlers.get(Collection.class.getName());
			}
		}

		if (handler != null) {
			return handler;
		}

		throw new PersistenceException(String.format("type %s is not supported", type));
	}

	@Override
	public JdbcPersistenceTransaction createTransaction() throws PersistenceException {
		return new JdbcPersistenceTransaction(this);
	}

	@Override
	public JdbcPersistenceTransaction obtainTransaction() throws PersistenceException {

		JdbcPersistenceTransaction transaction = transactions.get().peekLast();

		if (transaction == null) {
			transaction = createTransaction();
		}

		return transaction;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <E extends Entity> void create(EntityData<E> data) throws PersistenceException {

		JdbcPersistenceTransaction transaction = obtainTransaction();

		try {

			transaction.begin();

			Connection connection = transaction.getConnection();

			JdbcPersistenceContext context = new JdbcPersistenceContext();

			List<String> columns = new ArrayList<>();

			columns.add("id");
			columns.add("entityType");
			columns.add("data");

			String sql = String.format("insert into %s (%s) values (%s)", EntityData.class.getSimpleName(),
					columns.stream().collect(Collectors.joining(", ")),
					columns.stream().map(item -> "?").collect(Collectors.joining(", ")));

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				TypeReference<String> idType = TypeReference.of(String.class);
				TypeReference<Class> entityTypeType = TypeReference.of(Class.class);
				TypeReference<byte[]> dataType = TypeReference.of(byte[].class);

				getValueHandler(String.class).write(context, this, idType, data.getId(), false);
				getValueHandler(Class.class).write(context, this, entityTypeType, data.getEntityType(), false);
				getValueHandler(byte[].class).write(context, this, dataType, data.getData(), false);

				System.out.println(statement);

				statement.executeUpdate();
			}

			transaction.commit();

		} catch (SQLException e) {

			transaction.rollback();

			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public <E extends Entity> void update(EntityData<E> data) throws PersistenceException {

		JdbcPersistenceTransaction transaction = obtainTransaction();

		try {

			transaction.begin();

			Connection connection = transaction.getConnection();

			JdbcPersistenceContext context = new JdbcPersistenceContext();

			List<String> columns = new ArrayList<>();

			columns.add("id");
			columns.add("entityType");
			columns.add("data");

			String sql = String.format("update %s set %s where id = ?", EntityData.class.getSimpleName(),
					columns.stream().map(item -> String.format("%s = ?", item)).collect(Collectors.joining(", ")));

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				TypeReference<String> idType = TypeReference.of(String.class);
				TypeReference<Class> entityTypeType = TypeReference.of(Class.class);
				TypeReference<byte[]> dataType = TypeReference.of(byte[].class);

				getValueHandler(String.class).write(context, this, idType, data.getId(), false);
				getValueHandler(Class.class).write(context, this, entityTypeType, data.getEntityType(), false);
				getValueHandler(byte[].class).write(context, this, dataType, data.getData(), false);
				getValueHandler(String.class).write(context, this, idType, data.getId(), true);

				System.out.println(statement);

				statement.executeUpdate();
			}

			transaction.commit();

		} catch (SQLException e) {

			transaction.rollback();

			throw new PersistenceException(e);
		}
	}

	@Override
	public <E extends Entity> void delete(EntityData<E> data) throws PersistenceException {

		JdbcPersistenceTransaction transaction = obtainTransaction();

		try {

			transaction.begin();

			Connection connection = transaction.getConnection();

			JdbcPersistenceContext context = new JdbcPersistenceContext();

			String sql = String.format("delete from %s where id = ?", EntityData.class.getSimpleName());

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				TypeReference<String> idType = TypeReference.of(String.class);

				getValueHandler(String.class).write(context, this, idType, data.getId(), true);

				System.out.println(statement);

				statement.executeUpdate();
			}

			transaction.commit();

		} catch (SQLException e) {

			transaction.rollback();

			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <E extends Entity> EntityData<E> load(EntityReference<E> reference) throws PersistenceException {

		JdbcPersistenceTransaction transaction = obtainTransaction();

		try {

			transaction.begin();

			Connection connection = transaction.getConnection();

			JdbcPersistenceContext context = new JdbcPersistenceContext();

			List<String> columns = new ArrayList<>();

			columns.add("id");
			columns.add("entityType");
			columns.add("data");

			String sql = String.format("select %s from %s where id = ?",
					columns.stream().collect(Collectors.joining(", ")), EntityData.class.getSimpleName());

			System.out.println(sql);

			EntityData<E> entityData = null;

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				TypeReference<String> idType = TypeReference.of(String.class);
				TypeReference<Class> entityTypeType = TypeReference.of(Class.class);
				TypeReference<byte[]> dataType = TypeReference.of(byte[].class);

				getValueHandler(String.class).write(context, this, idType, reference.getId(), true);

				try (ResultSet resultSet = statement.executeQuery()) {

					context.setResultSet(resultSet);

					while (resultSet.next()) {

						context.resetIndex();

						entityData = new EntityData<>();

						entityData.setId(getValueHandler(String.class).read(context, this, idType, entityData.getId()));
						entityData.setEntityType(getValueHandler(Class.class).read(context, this, entityTypeType,
								entityData.getEntityType()));
						entityData.setData(
								getValueHandler(byte[].class).read(context, this, dataType, entityData.getData()));

						break;
					}
				}
			}

			transaction.commit();

			return entityData;

		} catch (SQLException e) {

			transaction.rollback();

			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void create(EntityIndex<?> index) throws PersistenceException {

		JdbcPersistenceTransaction transaction = obtainTransaction();

		try {

			transaction.begin();

			Connection connection = transaction.getConnection();

			JdbcPersistenceContext context = new JdbcPersistenceContext();

			Collection<Property<?>> properties = index.getProperties();

			List<String> columns = new ArrayList<>();

			for (Property<?> property : properties) {
				columns.addAll(((JdbcPersistenceValueHandler) getValueHandler(property.getType().getRawType()))
						.getColumns(context, this, property.getName(), property.getType(), false));
			}

			String sql = String.format("insert into %s (%s) values (%s)", index.getClass().getSimpleName(),
					columns.stream().collect(Collectors.joining(", ")),
					columns.stream().map(item -> "?").collect(Collectors.joining(", ")));

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				for (Property<?> property : properties) {
					((JdbcPersistenceValueHandler) getValueHandler(property.getType().getRawType())).write(context,
							this, ((Property) property).getType(), index.read(property), false);
				}

				System.out.println(statement);

				statement.executeUpdate();
			}

			transaction.commit();

		} catch (SQLException | IntrospectionException e) {

			transaction.rollback();

			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <I extends EntityIndex<?>> void delete(SearchQuery<I> query) throws PersistenceException {

		JdbcPersistenceTransaction transaction = obtainTransaction();
		try {

			transaction.begin();

			Connection connection = transaction.getConnection();

			JdbcPersistenceContext context = new JdbcPersistenceContext();

			List<Pair<TypeReference<?>, Supplier<?>>> readers = new ArrayList<>();

			String sql = handleDeleteQuery(context, query, readers);

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				for (Pair<TypeReference<?>, Supplier<?>> reader : readers) {
					((JdbcPersistenceValueHandler) getValueHandler(reader.getFirst().getRawType())).write(context, this,
							reader.getFirst(), reader.getSecond().get(), true);
				}

				System.out.println(statement);

				statement.executeUpdate();
			}

			transaction.commit();

		} catch (SQLException e) {

			transaction.rollback();

			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <I extends EntityIndex<?>> List<I> search(SearchQuery<I> query) throws PersistenceException {

		JdbcPersistenceTransaction transaction = obtainTransaction();

		try {

			transaction.begin();

			Connection connection = transaction.getConnection();

			JdbcPersistenceContext context = new JdbcPersistenceContext();

			List<Pair<TypeReference<?>, Supplier<?>>> readers = new ArrayList<>();

			String sql = handleSearchQuery(context, query, readers);

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				for (Pair<TypeReference<?>, Supplier<?>> reader : readers) {
					((JdbcPersistenceValueHandler) getValueHandler(reader.getFirst().getRawType())).write(context, this,
							reader.getFirst(), reader.getSecond().get(), true);
				}

				System.out.println(statement);

				try (ResultSet resultSet = statement.executeQuery()) {

					context.setResultSet(resultSet);

					List<I> indexes = new ArrayList<>();

					while (resultSet.next()) {

						I index = createIndex(query.getType());

						Collection<Property<?>> properties = index.getProperties();

						context.resetIndex();

						for (Property<?> property : properties) {
							index.write((Property) property,
									((JdbcPersistenceValueHandler) getValueHandler(property.getType().getRawType()))
											.read(context, this, ((Property) property).getType(),
													index.read(property)));
						}

						indexes.add(index);
					}

					transaction.commit();

					return indexes;
				}
			}

		} catch (SQLException | IntrospectionException e) {

			transaction.rollback();

			throw new PersistenceException(e);
		}
	}

	private <I extends EntityIndex<?>> I createIndex(Class<I> type) throws PersistenceException {

		try {
			return type.newInstance();
		} catch (IllegalAccessException | InstantiationException e) {
			throw new PersistenceException(e);
		}
	}

	private <I extends EntityIndex<?>> String handleDeleteQuery(JdbcPersistenceContext context, SearchQuery<I> query,
			List<Pair<TypeReference<?>, Supplier<?>>> readers) throws PersistenceException {

		String conditions = handleAndCriterion(context, AndSearchCriterion.of(query.getCriterions()), readers);

		StringBuilder sql = new StringBuilder();

		sql.append(String.format("delete from %s", query.getType().getSimpleName()));

		if (conditions != null) {
			sql.append(String.format(" where %s", conditions));
		}

		return sql.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private <I extends EntityIndex<?>> String handleSearchQuery(JdbcPersistenceContext context, SearchQuery<I> query,
			List<Pair<TypeReference<?>, Supplier<?>>> readers) throws PersistenceException {

		I index = createIndex(query.getType());

		Collection<Property<?>> properties = index.getProperties();

		List<String> columns = new ArrayList<>();

		for (Property<?> property : properties) {
			columns.addAll(((JdbcPersistenceValueHandler) getValueHandler(property.getType().getRawType()))
					.getColumns(context, this, property.getName(), property.getType(), false));
		}

		String conditions = handleAndCriterion(context, AndSearchCriterion.of(query.getCriterions()), readers);
		String sortings = handleSortings(context, query.getSortings());

		StringBuilder sql = new StringBuilder();

		sql.append(String.format("select %s from %s", columns.stream().collect(Collectors.joining(", ")),
				query.getType().getSimpleName()));

		if (conditions != null) {
			sql.append(String.format(" where %s", conditions));
		}

		if (sortings != null) {
			sql.append(String.format(" order by %s", sortings));
		}

		if (query.getOffset() != null) {
			sql.append(String.format(" offset %s", query.getOffset()));
		}

		if (query.getLimit() != null) {
			sql.append(String.format(" limit %s", query.getLimit()));
		}

		return sql.toString();
	}

	private <P, T> String handleSimpleCriterion(JdbcPersistenceContext context, SimpleSearchCriterion<P, T> criterion,
			List<Pair<TypeReference<?>, Supplier<?>>> readers) throws PersistenceException {

		Property<P> property = criterion.getProperty();
		TypeReference<T> type = criterion.getType();
		T value = criterion.getValue();

		boolean skip = value == null
				&& (criterion.getOperation() == Operation.EQUALS || criterion.getOperation() == Operation.NOT_EQUALS);

		if (!skip) {
			readers.add(Pair.of(type, () -> value));
		}

		List<String> columns = getValueHandler(property.getType().getRawType()).getColumns(context, this,
				property.getName(), property.getType(), true);

		String operation = getOperation(criterion.getOperation(), value);

		if (criterion.getOperation().name().startsWith("ANY")) {
			return columns.stream().map(item -> String.format("? %s any (%s)", operation, item))
					.collect(Collectors.joining(" and "));
		}

		if (criterion.getOperation().name().startsWith("ALL")) {
			return columns.stream().map(item -> String.format("? %s all (%s)", operation, item))
					.collect(Collectors.joining(" and "));
		}

		return columns.stream()
				.map(item -> skip ? String.format("%s %s", item, operation) : String.format("%s %s ?", item, operation))
				.collect(Collectors.joining(" and "));
	}

	private String handleNotCriterion(JdbcPersistenceContext context, NotSearchCriterion groupCriterion,
			List<Pair<TypeReference<?>, Supplier<?>>> readers) throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (SearchCriterion criterion : groupCriterion.getCriterions()) {

			String sql = null;

			if (criterion instanceof SimpleSearchCriterion) {
				sql = handleSimpleCriterion(context, (SimpleSearchCriterion<?, ?>) criterion, readers);
			} else if (criterion instanceof NotSearchCriterion) {
				sql = handleNotCriterion(context, (NotSearchCriterion) criterion, readers);
			} else if (criterion instanceof AndSearchCriterion) {
				sql = handleAndCriterion(context, (AndSearchCriterion) criterion, readers);
			} else if (criterion instanceof OrSearchCriterion) {
				sql = handleOrCriterion(context, (OrSearchCriterion) criterion, readers);
			} else {
				throw new PersistenceException(String.format("criterion %s is not supported", criterion));
			}

			if (sql != null) {
				sqls.add(sql);
			}
		}

		if (sqls.size() > 1) {
			return String.format("not (%s)", sqls.stream().collect(Collectors.joining(" and ")));
		} else if (sqls.size() == 1) {
			return String.format("not (%s)", sqls.get(0));
		} else {
			return null;
		}
	}

	private String handleAndCriterion(JdbcPersistenceContext context, AndSearchCriterion groupCriterion,
			List<Pair<TypeReference<?>, Supplier<?>>> readers) throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (SearchCriterion criterion : groupCriterion.getCriterions()) {

			String sql = null;

			if (criterion instanceof SimpleSearchCriterion) {
				sql = handleSimpleCriterion(context, (SimpleSearchCriterion<?, ?>) criterion, readers);
			} else if (criterion instanceof NotSearchCriterion) {
				sql = handleNotCriterion(context, (NotSearchCriterion) criterion, readers);
			} else if (criterion instanceof AndSearchCriterion) {
				sql = handleAndCriterion(context, (AndSearchCriterion) criterion, readers);
			} else if (criterion instanceof OrSearchCriterion) {
				sql = handleOrCriterion(context, (OrSearchCriterion) criterion, readers);
			} else {
				throw new PersistenceException(String.format("criterion %s is not supported", criterion));
			}

			if (sql != null) {
				sqls.add(sql);
			}
		}

		if (sqls.size() > 1) {
			return String.format("(%s)", sqls.stream().collect(Collectors.joining(" and ")));
		} else if (sqls.size() == 1) {
			return sqls.get(0);
		} else {
			return null;
		}
	}

	private String handleOrCriterion(JdbcPersistenceContext context, OrSearchCriterion groupCriterion,
			List<Pair<TypeReference<?>, Supplier<?>>> readers) throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (SearchCriterion criterion : groupCriterion.getCriterions()) {

			String sql = null;

			if (criterion instanceof SimpleSearchCriterion) {
				sql = handleSimpleCriterion(context, (SimpleSearchCriterion<?, ?>) criterion, readers);
			} else if (criterion instanceof NotSearchCriterion) {
				sql = handleNotCriterion(context, (NotSearchCriterion) criterion, readers);
			} else if (criterion instanceof AndSearchCriterion) {
				sql = handleAndCriterion(context, (AndSearchCriterion) criterion, readers);
			} else if (criterion instanceof OrSearchCriterion) {
				sql = handleOrCriterion(context, (OrSearchCriterion) criterion, readers);
			} else {
				throw new PersistenceException(String.format("criterion %s is not supported", criterion));
			}

			if (sql != null) {
				sqls.add(sql);
			}
		}

		if (sqls.size() > 1) {
			return String.format("(%s)", sqls.stream().collect(Collectors.joining(" or ")));
		} else if (sqls.size() == 1) {
			return sqls.get(0);
		} else {
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private String handleSortings(JdbcPersistenceContext context, Map<Property<?>, Order> sortings)
			throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (Entry<Property<?>, Order> sorting : sortings.entrySet()) {

			Property<?> property = sorting.getKey();
			String order = getOrder(sorting.getValue());

			List<String> columns = ((JdbcPersistenceValueHandler) getValueHandler(property.getType().getRawType()))
					.getColumns(context, this, property.getName(), property.getType(), true);

			for (String column : columns) {
				sqls.add(String.format("%s %s", column, order));
			}
		}

		if (sqls.size() > 0) {
			return sqls.stream().collect(Collectors.joining(", "));
		} else {
			return null;
		}
	}

	private String getOperation(Operation operation, Object value) throws PersistenceException {

		if (value != null) {

			if (operation == Operation.EQUALS || operation == Operation.ANY_EQUALS
					|| operation == Operation.ALL_EQUALS) {
				return "=";
			} else if (operation == Operation.NOT_EQUALS || operation == Operation.ANY_NOT_EQUALS
					|| operation == Operation.ALL_NOT_EQUALS) {
				return "!=";
			} else if (operation == Operation.LOWER || operation == Operation.ANY_GREATER
					|| operation == Operation.ALL_GREATER) {
				return "<";
			} else if (operation == Operation.LOWER_OR_EQUALS || operation == Operation.ANY_GREATER_OR_EQUALS
					|| operation == Operation.ALL_GREATER_OR_EQUALS) {
				return "<=";
			} else if (operation == Operation.GREATER || operation == Operation.ANY_LOWER
					|| operation == Operation.ALL_LOWER) {
				return ">";
			} else if (operation == Operation.GREATER_OR_EQUALS || operation == Operation.ANY_LOWER_OR_EQUALS
					|| operation == Operation.ALL_LOWER_OR_EQUALS) {
				return ">=";
			}

		} else {

			if (operation == Operation.EQUALS) {
				return "is null";
			} else if (operation == Operation.NOT_EQUALS) {
				return "is not null";
			}
		}

		throw new PersistenceException(String.format("operation %s for value %s is not supported", operation, value));
	}

	private String getOrder(Order order) throws PersistenceException {

		if (order == Order.ASC) {
			return "asc";
		} else if (order == Order.DESC) {
			return "desc";
		}

		throw new PersistenceException(String.format("order %s is not supported", order));
	}
}
