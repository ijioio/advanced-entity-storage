package com.ijioio.aes.core.persistence.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.Operation;
import com.ijioio.aes.core.Order;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.PropertyReader;
import com.ijioio.aes.core.PropertyWriter;
import com.ijioio.aes.core.SearchCriterion;
import com.ijioio.aes.core.SearchCriterion.AndSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.NotSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.OrSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.SimpleSearchCriterion;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.PersistenceHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcBooleanPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcCharacterPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcClassPersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcEntityReferencePersistenceValueHandler;
import com.ijioio.aes.core.persistence.jdbc.value.handler.JdbcStringPersistenceValueHandler;
import com.ijioio.aes.core.util.TupleUtil.Pair;

public class JdbcPersistenceHandler implements PersistenceHandler<JdbcPersistenceContext> {

	private static final JdbcPersistenceValueHandler<Byte> HANDLER_BYTE = new JdbcPersistenceValueHandler<Byte>() {

		@Override
		public Class<Byte> getType() {
			return Byte.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<Byte> type, boolean search) {
			return Collections.singletonList(name);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Byte> type,
				Byte value, boolean search) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Byte read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Byte> type,
				Byte value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return resultSet.getObject(context.getNextIndex(), Byte.class);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<Short> HANDLER_SHORT = new JdbcPersistenceValueHandler<Short>() {

		@Override
		public Class<Short> getType() {
			return Short.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<Short> type, boolean search) {
			return Collections.singletonList(name);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Short> type,
				Short value, boolean search) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Short read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Short> type,
				Short value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return resultSet.getObject(context.getNextIndex(), Short.class);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<Integer> HANDLER_INTEGER = new JdbcPersistenceValueHandler<Integer>() {

		@Override
		public Class<Integer> getType() {
			return Integer.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<Integer> type, boolean search) {
			return Collections.singletonList(name);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Integer> type,
				Integer value, boolean search) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Integer read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Integer> type,
				Integer value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return resultSet.getObject(context.getNextIndex(), Integer.class);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<Long> HANDLER_LONG = new JdbcPersistenceValueHandler<Long>() {

		@Override
		public Class<Long> getType() {
			return Long.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<Long> type, boolean search) {
			return Collections.singletonList(name);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Long> type,
				Long value, boolean search) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Long read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Long> type,
				Long value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return resultSet.getObject(context.getNextIndex(), Long.class);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<Float> HANDLER_FLOAT = new JdbcPersistenceValueHandler<Float>() {

		@Override
		public Class<Float> getType() {
			return Float.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<Float> type, boolean search) {
			return Collections.singletonList(name);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Float> type,
				Float value, boolean search) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Float read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Float> type,
				Float value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return resultSet.getObject(context.getNextIndex(), Float.class);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<Double> HANDLER_DOUBLE = new JdbcPersistenceValueHandler<Double>() {

		@Override
		public Class<Double> getType() {
			return Double.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<Double> type, boolean search) {
			return Collections.singletonList(name);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Double> type,
				Double value, boolean search) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Double read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Double> type,
				Double value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return resultSet.getObject(context.getNextIndex(), Double.class);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<Instant> HANDLER_INSTANT = new JdbcPersistenceValueHandler<Instant>() {

		@Override
		public Class<Instant> getType() {
			return Instant.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<Instant> type, boolean search) {
			return Collections.singletonList(name);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Instant> type,
				Instant value, boolean search) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Timestamp.from(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Instant read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Instant> type,
				Instant value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), Timestamp.class))
						.map(item -> item.toInstant()).orElse(null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<LocalDate> HANDLER_LOCAL_DATE = new JdbcPersistenceValueHandler<LocalDate>() {

		@Override
		public Class<LocalDate> getType() {
			return LocalDate.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<LocalDate> type, boolean search) {
			return Collections.singletonList(name);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<LocalDate> type,
				LocalDate value, boolean search) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Date.valueOf(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public LocalDate read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				TypeReference<LocalDate> type, LocalDate value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), Date.class))
						.map(item -> item.toLocalDate()).orElse(null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<LocalTime> HANDLER_LOCAL_TIME = new JdbcPersistenceValueHandler<LocalTime>() {

		@Override
		public Class<LocalTime> getType() {
			return LocalTime.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<LocalTime> type, boolean search) {
			return Collections.singletonList(name);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<LocalTime> type,
				LocalTime value, boolean search) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Time.valueOf(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public LocalTime read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				TypeReference<LocalTime> type, LocalTime value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), Time.class))
						.map(item -> item.toLocalTime()).orElse(null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<LocalDateTime> HANDLER_LOCAL_DATE_TIME = new JdbcPersistenceValueHandler<LocalDateTime>() {

		@Override
		public Class<LocalDateTime> getType() {
			return LocalDateTime.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<LocalDateTime> type, boolean search) {
			return Collections.singletonList(name);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				TypeReference<LocalDateTime> type, LocalDateTime value, boolean search) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Timestamp.valueOf(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public LocalDateTime read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				TypeReference<LocalDateTime> type, LocalDateTime value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), Timestamp.class))
						.map(item -> item.toLocalDateTime()).orElse(null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	@SuppressWarnings("rawtypes")
	private static final JdbcPersistenceValueHandler<Enum> HANDLER_ENUM = new JdbcPersistenceValueHandler<Enum>() {

		@Override
		public Class<Enum> getType() {
			return Enum.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<Enum> type, boolean search) {
			return Collections.singletonList(name);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Enum> type,
				Enum value, boolean search) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? value.name() : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public Enum read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, TypeReference<Enum> type,
				Enum value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), String.class))
						.map(item -> Enum.valueOf(type.getRawType(), item)).orElse(null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}
	};

	@SuppressWarnings("rawtypes")
	private static final JdbcPersistenceValueHandler<Collection> HANDLER_COLLECTION = new JdbcPersistenceValueHandler<Collection>() {

		@Override
		public Class<Collection> getType() {
			return Collection.class;
		}

		@SuppressWarnings("unchecked")
		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
				TypeReference<Collection> type, boolean search) throws PersistenceException {

			JdbcPersistenceValueHandler elementHandler = handler
					.getValueHandler(type.getParameterTypes()[0].getRawType());

			return elementHandler.getColumns(context, handler, name, type, search);
		}

		@SuppressWarnings("unchecked")
		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				TypeReference<Collection> type, Collection value, boolean search) throws PersistenceException {

			JdbcPersistenceValueHandler elementHandler = handler
					.getValueHandler(type.getParameterTypes()[0].getRawType());

			elementHandler.writeCollection(context, handler, type, value, search);
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				TypeReference<Collection> type, Collection value) throws PersistenceException {

			JdbcPersistenceValueHandler elementHandler = handler
					.getValueHandler(type.getParameterTypes()[0].getRawType());

			return elementHandler.readCollection(context, handler, type, value);
		}
	};

	private final Map<String, JdbcPersistenceValueHandler<?>> handlers = new HashMap<>();

	public JdbcPersistenceHandler() {

		registerValueHandler(new JdbcBooleanPersistenceValueHandler());
		registerValueHandler(new JdbcCharacterPersistenceValueHandler());
		registerValueHandler(HANDLER_BYTE);
		registerValueHandler(HANDLER_SHORT);
		registerValueHandler(HANDLER_INTEGER);
		registerValueHandler(HANDLER_LONG);
		registerValueHandler(HANDLER_FLOAT);
		registerValueHandler(HANDLER_DOUBLE);
		registerValueHandler(new JdbcStringPersistenceValueHandler());
		registerValueHandler(HANDLER_INSTANT);
		registerValueHandler(HANDLER_LOCAL_DATE);
		registerValueHandler(HANDLER_LOCAL_TIME);
		registerValueHandler(HANDLER_LOCAL_DATE_TIME);
		registerValueHandler(HANDLER_ENUM);
		registerValueHandler(new JdbcClassPersistenceValueHandler());
		registerValueHandler(HANDLER_COLLECTION);
		registerValueHandler(new JdbcEntityReferencePersistenceValueHandler());
	}

	public <T> void registerValueHandler(JdbcPersistenceValueHandler<T> handler) {
		handlers.put(handler.getType().getName(), handler);
	}

	@SuppressWarnings("unchecked")
	public <T> JdbcPersistenceValueHandler<T> getValueHandler(Class<T> type) {

		JdbcPersistenceValueHandler<T> handler = (JdbcPersistenceValueHandler<T>) handlers.get(type.getName());

		if (handler == null) {

			if (Enum.class.isAssignableFrom(type)) {
				handler = (JdbcPersistenceValueHandler<T>) handlers.get(Enum.class.getName());
			} else if (Collection.class.isAssignableFrom(type)) {
				handler = (JdbcPersistenceValueHandler<T>) handlers.get(Collection.class.getName());
			}
		}

		return handler;
	}

	public <T> List<String> getColumns(JdbcPersistenceContext context, String name, TypeReference<T> type,
			boolean search) throws PersistenceException {

		JdbcPersistenceValueHandler<T> handler = getValueHandler(type.getRawType());

		if (handler != null) {
			return handler.getColumns(context, this, name, type, search);
		} else {
			throw new PersistenceException(String.format("type %s is not supported", type));
		}
	}

	public <T> void write(JdbcPersistenceContext context, TypeReference<T> type, T value, boolean search)
			throws PersistenceException {

		JdbcPersistenceValueHandler<T> handler = getValueHandler(type.getRawType());

		if (handler != null) {
			handler.write(context, this, type, value, search);
		} else {
			throw new PersistenceException(String.format("type %s is not supported", type));
		}
	}

	public <T> T read(JdbcPersistenceContext context, TypeReference<T> type, T value) throws PersistenceException {

		JdbcPersistenceValueHandler<T> handler = getValueHandler(type.getRawType());

		if (handler != null) {
			return handler.read(context, this, type, value);
		} else {
			throw new PersistenceException(String.format("type %s is not supported", type));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void create(JdbcPersistenceContext context, EntityIndex<?> index) throws PersistenceException {

		Connection connection = context.getConnection();

		try {

			Collection<Property<?>> properties = index.getProperties();
			Map<Property<?>, PropertyReader<?>> readers = index.getReaders();

			List<String> columns = new ArrayList<>();

			for (Property<?> property : properties) {
				columns.addAll(getColumns(context, property.getName(), property.getType(), false));
			}

			String sql = String.format("insert into %s (%s) values (%s)", index.getClass().getSimpleName(),
					columns.stream().collect(Collectors.joining(", ")),
					columns.stream().map(item -> "?").collect(Collectors.joining(", ")));

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				for (Property<?> property : properties) {

					PropertyReader<?> reader = readers.get(property);

					if (reader == null) {
						throw new PersistenceException(
								String.format("reader for property %s is not found", property.getName()));
					}

					write(context, ((Property) property).getType(), reader.read(), false);
				}

				System.out.println(statement);

				statement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <I extends EntityIndex<?>> void delete(JdbcPersistenceContext context, SearchQuery<I> query)
			throws PersistenceException {

		Connection connection = context.getConnection();

		try {

			List<Pair<TypeReference<?>, PropertyReader<?>>> readers = new ArrayList<>();

			String sql = handleDeleteQuery(context, query, readers);

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				for (Pair<TypeReference<?>, PropertyReader<?>> reader : readers) {
					write(context, ((TypeReference) reader.getFirst()), reader.getSecond().read(), true);
				}

				System.out.println(statement);

				statement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public <I extends EntityIndex<?>> List<I> search(JdbcPersistenceContext context, SearchQuery<I> query)
			throws PersistenceException {

		Connection connection = context.getConnection();

		try {

			List<Pair<TypeReference<?>, PropertyReader<?>>> queryValueReaders = new ArrayList<>();

			String sql = handleSearchQuery(context, query, queryValueReaders);

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				for (Pair<TypeReference<?>, PropertyReader<?>> queryValueReader : queryValueReaders) {
					write(context, (TypeReference) queryValueReader.getFirst(), queryValueReader.getSecond().read(),
							true);
				}

				System.out.println(statement);

				try (ResultSet resultSet = statement.executeQuery()) {

					context.setResultSet(resultSet);

					List<I> indexes = new ArrayList<>();

					while (resultSet.next()) {

						I index = createIndex(query.getType());

						Collection<Property<?>> properties = index.getProperties();

						Map<Property<?>, PropertyWriter<?>> writers = index.getWriters();
						Map<Property<?>, PropertyReader<?>> readers = index.getReaders();

						context.resetIndex();

						for (Property<?> property : properties) {

							PropertyWriter<?> writer = writers.get(property);

							if (writer == null) {
								throw new PersistenceException(
										String.format("writer for property %s is not found", property.getName()));
							}

							PropertyReader<?> reader = readers.get(property);

							if (reader == null) {
								throw new PersistenceException(
										String.format("reader for property %s is not found", property.getName()));
							}

							((PropertyWriter) writer)
									.write(read(context, ((Property) property).getType(), reader.read()));
						}

						indexes.add(index);
					}

					return indexes;
				}
			}

		} catch (SQLException e) {
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
			List<Pair<TypeReference<?>, PropertyReader<?>>> readers) throws PersistenceException {

		String conditions = handleAndCriterion(context, AndSearchCriterion.of(query.getCriterions()), readers);

		StringBuilder sql = new StringBuilder();

		sql.append(String.format("delete from %s", query.getType().getSimpleName()));

		if (conditions != null) {
			sql.append(String.format(" where %s", conditions));
		}

		return sql.toString();
	}

	private <I extends EntityIndex<?>> String handleSearchQuery(JdbcPersistenceContext context, SearchQuery<I> query,
			List<Pair<TypeReference<?>, PropertyReader<?>>> readers) throws PersistenceException {

		I index = createIndex(query.getType());

		Collection<Property<?>> properties = index.getProperties();

		List<String> columns = new ArrayList<>();

		for (Property<?> property : properties) {
			columns.addAll(getColumns(context, property.getName(), property.getType(), false));
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
			List<Pair<TypeReference<?>, PropertyReader<?>>> readers) throws PersistenceException {

		Property<P> property = criterion.getProperty();
		TypeReference<T> type = criterion.getType();
		T value = criterion.getValue();

		boolean skip = value == null
				&& (criterion.getOperation() == Operation.EQUALS || criterion.getOperation() == Operation.NOT_EQUALS);

		if (!skip) {
			readers.add(Pair.of(type, () -> value));
		}

		List<String> columns = getColumns(context, property.getName(), property.getType(), true);

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
			List<Pair<TypeReference<?>, PropertyReader<?>>> readers) throws PersistenceException {

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
			List<Pair<TypeReference<?>, PropertyReader<?>>> readers) throws PersistenceException {

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
			List<Pair<TypeReference<?>, PropertyReader<?>>> readers) throws PersistenceException {

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

	private String handleSortings(JdbcPersistenceContext context, Map<Property<?>, Order> sortings)
			throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (Entry<Property<?>, Order> sorting : sortings.entrySet()) {

			Property<?> property = sorting.getKey();
			String order = getOrder(sorting.getValue());

			List<String> columns = getColumns(context, property.getName(), property.getType(), true);

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
