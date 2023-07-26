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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.Operation;
import com.ijioio.aes.core.Order;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.SearchCriterion;
import com.ijioio.aes.core.SearchCriterion.AndSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.NotSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.OrSearchCriterion;
import com.ijioio.aes.core.SearchCriterion.SimpleSearchCriterion;
import com.ijioio.aes.core.SearchQuery;
import com.ijioio.aes.core.persistence.PersistenceColumnProvider;
import com.ijioio.aes.core.persistence.PersistenceContext;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.PersistenceHandler;
import com.ijioio.aes.core.persistence.PersistenceReader;
import com.ijioio.aes.core.persistence.PersistenceWriter;

public class JdbcPersistenceHandler implements PersistenceHandler {

	private static final JdbcPersistenceValueHandler<Boolean> HANDLER_BOOLEAN = new JdbcPersistenceValueHandler<Boolean>() {

		@Override
		public Class<Boolean> getType() {
			return Boolean.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Boolean value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Boolean read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Boolean value,
				Class<Boolean> type) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return resultSet.getObject(context.getNextIndex(), Boolean.class);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<Character> HANDLER_CHARACTER = new JdbcPersistenceValueHandler<Character>() {

		@Override
		public Class<Character> getType() {
			return Character.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Character value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Character read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Character value,
				Class<Character> type) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return resultSet.getObject(context.getNextIndex(), Character.class);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<Byte> HANDLER_BYTE = new JdbcPersistenceValueHandler<Byte>() {

		@Override
		public Class<Byte> getType() {
			return Byte.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Byte value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Byte read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Byte value, Class<Byte> type)
				throws PersistenceException {

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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Short value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Short read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Short value,
				Class<Short> type) throws PersistenceException {

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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Integer value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Integer read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Integer value,
				Class<Integer> type) throws PersistenceException {

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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Long value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Long read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Long value, Class<Long> type)
				throws PersistenceException {

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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Float value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Float read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Float value,
				Class<Float> type) throws PersistenceException {

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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Double value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Double read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Double value,
				Class<Double> type) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return resultSet.getObject(context.getNextIndex(), Double.class);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private static final JdbcPersistenceValueHandler<String> HANDLER_STRING = new JdbcPersistenceValueHandler<String>() {

		@Override
		public Class<String> getType() {
			return String.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public String read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String value,
				Class<String> type) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return resultSet.getObject(context.getNextIndex(), String.class);
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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Instant value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Timestamp.from(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Instant read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Instant value,
				Class<Instant> type) throws PersistenceException {

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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, LocalDate value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Date.valueOf(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public LocalDate read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, LocalDate value,
				Class<LocalDate> type) throws PersistenceException {

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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, LocalTime value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Time.valueOf(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public LocalTime read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, LocalTime value,
				Class<LocalTime> type) throws PersistenceException {

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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, LocalDateTime value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Timestamp.valueOf(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public LocalDateTime read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, LocalDateTime value,
				Class<LocalDateTime> type) throws PersistenceException {

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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Collections.singletonList(property);
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Enum value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? value.name() : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public Enum read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Enum value, Class<Enum> type)
				throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), String.class))
						.map(item -> Enum.valueOf(type, item)).orElse(null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}
	};

//	@SuppressWarnings("rawtypes")
//	private static final JdbcPersistenceValueHandler<Collection> HANDLER_COLLECTION = new JdbcPersistenceValueHandler<Collection>() {
//
//		@Override
//		public Class<Collection> getType() {
//			return Collection.class;
//		}
//
//		@Override
//		public Collection<String> getColumnNames(String property) {
//			return Collections.singletonList(property);
//		};
//
//		@Override
//		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Collection values)
//				throws PersistenceException {
//
//			PreparedStatement statement = context.getStatement();
//
//			try {
//				statement.setObject(index,
//						value != null ? statement.getConnection().createArrayOf(String.class, null) : null);
//			} catch (SQLException e) {
//				throw new PersistenceException(e);
//			}
//		}
//
//		@SuppressWarnings("unchecked")
//		@Override
//		public Collection read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
//				Collection values, Class<Collection> type) throws PersistenceException {
//
//			ResultSet resultSet = context.getResultSet();
//
//			try {
//				return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), String.class))
//						.map(item -> Enum.valueOf(type, item)).orElse(null);
//			} catch (SQLException e) {
//				throw new PersistenceException(e);
//			}
//		};
//	};

	@SuppressWarnings("rawtypes")
	private static final JdbcPersistenceValueHandler<EntityReference> HANDLER_ENTITY_REFERENCE = new JdbcPersistenceValueHandler<EntityReference>() {

		@Override
		public Class<EntityReference> getType() {
			return EntityReference.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				String property) {
			return Arrays.asList(String.format("%sId", property), String.format("%sType", property));
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, EntityReference value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {

				statement.setObject(context.getNextIndex(), value != null ? value.getId() : null);
				statement.setObject(context.getNextIndex(), value != null ? value.getType().getName() : null);

			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public EntityReference read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				EntityReference value, Class<EntityReference> type) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {

				String referenceId = resultSet.getObject(context.getNextIndex(), String.class);
				Class referenceIdType = Class.forName(resultSet.getObject(context.getNextIndex(), String.class));

				return EntityReference.of(referenceId, referenceIdType);

			} catch (ClassNotFoundException | SQLException e) {
				throw new PersistenceException(e);
			}
		};
	};

	private final Map<String, JdbcPersistenceValueHandler<?>> handlers = new HashMap<>();

	public JdbcPersistenceHandler() {

		registerValueHandler(HANDLER_BOOLEAN);
		registerValueHandler(HANDLER_CHARACTER);
		registerValueHandler(HANDLER_BYTE);
		registerValueHandler(HANDLER_SHORT);
		registerValueHandler(HANDLER_INTEGER);
		registerValueHandler(HANDLER_LONG);
		registerValueHandler(HANDLER_FLOAT);
		registerValueHandler(HANDLER_DOUBLE);
		registerValueHandler(HANDLER_STRING);
		registerValueHandler(HANDLER_INSTANT);
		registerValueHandler(HANDLER_LOCAL_DATE);
		registerValueHandler(HANDLER_LOCAL_TIME);
		registerValueHandler(HANDLER_LOCAL_DATE_TIME);
		registerValueHandler(HANDLER_ENUM);
//		registerValueHandler(HANDLER_COLLECTION);
		registerValueHandler(HANDLER_ENTITY_REFERENCE);
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

	@Override
	public <T> List<String> getColumns(PersistenceContext context, String name, Class<T> type)
			throws PersistenceException {

		JdbcPersistenceValueHandler<T> handler = getValueHandler(type);

		if (handler != null) {
			return handler.getColumns((JdbcPersistenceContext) context, this, name);
		} else {
			throw new PersistenceException(String.format("type %s is not supported", type));
		}
	}

	@Override
	public <T> void write(PersistenceContext context, String name, T value, Class<T> type) throws PersistenceException {

		JdbcPersistenceValueHandler<T> handler = getValueHandler(type);

		if (handler != null) {
			handler.write((JdbcPersistenceContext) context, this, value);
		} else {
			throw new PersistenceException(String.format("type %s is not supported", type));
		}
	}

	@Override
	public <T> T read(PersistenceContext context, String name, T value, Class<T> type) throws PersistenceException {

		JdbcPersistenceValueHandler<T> handler = getValueHandler(type);

		if (handler != null) {
			return handler.read((JdbcPersistenceContext) context, this, value, type);
		} else {
			throw new PersistenceException(String.format("type %s is not supported", type));
		}
	}

	@Override
	public void read(PersistenceContext context, Collection<Property<?>> properties,
			Map<String, PersistenceReader> readers) throws PersistenceException {

		((JdbcPersistenceContext) context).resetIndex();

		for (Property<?> property : properties) {

			PersistenceReader reader = readers.get(property.getName());

			if (reader == null) {
				throw new PersistenceException(
						String.format("reader for property %s is not found", property.getName()));
			}

			reader.read();
		}
	}

	@Override
	public void create(PersistenceContext context, String table, Collection<Property<?>> properties,
			Map<String, PersistenceColumnProvider> columnProviders, Map<String, PersistenceWriter> writers)
			throws PersistenceException {

		((JdbcPersistenceContext) context).resetIndex();

		Connection connection = ((JdbcPersistenceContext) context).getConnection();

		try {

			List<String> columns = new ArrayList<>();

			for (Property<?> property : properties) {

				PersistenceColumnProvider columnProvider = columnProviders.get(property.getName());

				if (columnProvider == null) {
					throw new PersistenceException(
							String.format("column provider for property %s is not found", property.getName()));
				}

				columns.addAll(columnProvider.getColumns());
			}

			String sql = String.format("insert into %s (%s) values (%s)", table,
					columns.stream().collect(Collectors.joining(", ")),
					columns.stream().map(item -> "?").collect(Collectors.joining(", ")));

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				((JdbcPersistenceContext) context).setStatement(statement);

				for (Property<?> property : properties) {

					PersistenceWriter writer = writers.get(property.getName());

					if (writer == null) {
						throw new PersistenceException(
								String.format("writer for property %s is not found", property.getName()));
					}

					writer.write();
				}

				statement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void update(PersistenceContext context, String table, Property<?> idProperty,
			Collection<Property<?>> properties, Map<String, PersistenceColumnProvider> columnProviders,
			Map<String, PersistenceWriter> writers) throws PersistenceException {

		((JdbcPersistenceContext) context).resetIndex();

		Connection connection = ((JdbcPersistenceContext) context).getConnection();

		try {

			List<String> idColumns = new ArrayList<>();

			PersistenceColumnProvider idColumnProvider = columnProviders.get(idProperty.getName());

			if (idColumnProvider == null) {
				throw new PersistenceException(
						String.format("column provider for property %s is not found", idProperty.getName()));
			}

			idColumns.addAll(idColumnProvider.getColumns());

			List<String> columns = new ArrayList<>();

			for (Property<?> property : properties) {

				PersistenceColumnProvider columnProvider = columnProviders.get(property.getName());

				if (columnProvider == null) {
					throw new PersistenceException(
							String.format("column provider for property %s is not found", property.getName()));
				}

				columns.addAll(columnProvider.getColumns());
			}

			String sql = String.format("update %s set %s where %s", table,
					columns.stream().map(item -> String.format("%s = ?", item)).collect(Collectors.joining(", ")),
					idColumns.stream().map(item -> String.format("%s = ?", item)).collect(Collectors.joining(" and ")));

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				((JdbcPersistenceContext) context).setStatement(statement);

				for (Property<?> property : properties) {

					PersistenceWriter writer = writers.get(property.getName());

					if (writer == null) {
						throw new PersistenceException(
								String.format("writer for property %s is not found", property.getName()));
					}

					writer.write();
				}

				PersistenceWriter idWriter = writers.get(idProperty.getName());

				if (idWriter == null) {
					throw new PersistenceException(
							String.format("writer for property %s is not found", idProperty.getName()));
				}

				idWriter.write();

				statement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void delete(PersistenceContext context, String table, Property<?> idProperty,
			Map<String, PersistenceColumnProvider> columnProviders, Map<String, PersistenceWriter> writers)
			throws PersistenceException {

		((JdbcPersistenceContext) context).resetIndex();

		Connection connection = ((JdbcPersistenceContext) context).getConnection();

		try {

			List<String> idColumns = new ArrayList<>();

			PersistenceColumnProvider idColumnProvider = columnProviders.get(idProperty.getName());

			if (idColumnProvider == null) {
				throw new PersistenceException(
						String.format("column provider for property %s is not found", idProperty.getName()));
			}

			idColumns.addAll(idColumnProvider.getColumns());

			String sql = String.format("delete from %s where %s", table,
					idColumns.stream().map(item -> String.format("%s = ?", item)).collect(Collectors.joining(" and ")));

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				((JdbcPersistenceContext) context).setStatement(statement);

				PersistenceWriter idWriter = writers.get(idProperty.getName());

				if (idWriter == null) {
					throw new PersistenceException(
							String.format("writer for property %s is not found", idProperty.getName()));
				}

				idWriter.write();

				statement.executeUpdate();
			}

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public <I extends EntityIndex<?>> List<I> search(PersistenceContext context, SearchQuery<I> query)
			throws PersistenceException {

		((JdbcPersistenceContext) context).resetIndex();

		Connection connection = ((JdbcPersistenceContext) context).getConnection();

		try {

			List<PersistenceWriter> writers = new ArrayList<>();

			String sql = handleSearchQuery(context, query, writers);

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				((JdbcPersistenceContext) context).setStatement(statement);
				((JdbcPersistenceContext) context).resetIndex();

				for (PersistenceWriter writer : writers) {
					writer.write();
				}

				System.out.println(statement);

				try (ResultSet resultSet = statement.executeQuery()) {

					((JdbcPersistenceContext) context).setResultSet(resultSet);

					List<I> indexes = new ArrayList<>();

					while (resultSet.next()) {

						I index = query.getType().newInstance();

						index.read(this, context);

						indexes.add(index);
					}

					return indexes;
				}
			}

		} catch (IllegalAccessException | InstantiationException | SQLException e) {
			throw new PersistenceException(e);
		}
	}

	private <I extends EntityIndex<?>> String handleSearchQuery(PersistenceContext context, SearchQuery<I> query,
			List<PersistenceWriter> writers) throws PersistenceException {

		String conditions = handleAndCriterion(context, AndSearchCriterion.of(query.getCriterions()), writers);
		String sortings = handleSortings(context, query.getSortings());

		StringBuilder sql = new StringBuilder();

		sql.append(String.format("select * from %s", query.getType().getSimpleName()));

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

	private <T> String handleSimpleCriterion(PersistenceContext context, SimpleSearchCriterion<T> criterion,
			List<PersistenceWriter> writers) throws PersistenceException {

		Property<T> property = criterion.getProperty();

		writers.add(() -> write(context, property.getName(), criterion.getValue(), property.getType()));

		List<String> columns = getColumns(context, property.getName(), property.getType());

		String operation = getOperation(criterion.getOperation());

		return columns.stream().map(item -> String.format("%s %s ?", item, operation))
				.collect(Collectors.joining(" and "));
	}

	private String handleNotCriterion(PersistenceContext context, NotSearchCriterion groupCriterion,
			List<PersistenceWriter> writers) throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (SearchCriterion criterion : groupCriterion.getCriterions()) {

			String sql = null;

			if (criterion instanceof SimpleSearchCriterion) {
				sql = handleSimpleCriterion(context, (SimpleSearchCriterion<?>) criterion, writers);
			} else if (criterion instanceof NotSearchCriterion) {
				sql = handleNotCriterion(context, (NotSearchCriterion) criterion, writers);
			} else if (criterion instanceof AndSearchCriterion) {
				sql = handleAndCriterion(context, (AndSearchCriterion) criterion, writers);
			} else if (criterion instanceof OrSearchCriterion) {
				sql = handleOrCriterion(context, (OrSearchCriterion) criterion, writers);
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

	private String handleAndCriterion(PersistenceContext context, AndSearchCriterion groupCriterion,
			List<PersistenceWriter> writers) throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (SearchCriterion criterion : groupCriterion.getCriterions()) {

			String sql = null;

			if (criterion instanceof SimpleSearchCriterion) {
				sql = handleSimpleCriterion(context, (SimpleSearchCriterion<?>) criterion, writers);
			} else if (criterion instanceof NotSearchCriterion) {
				sql = handleNotCriterion(context, (NotSearchCriterion) criterion, writers);
			} else if (criterion instanceof AndSearchCriterion) {
				sql = handleAndCriterion(context, (AndSearchCriterion) criterion, writers);
			} else if (criterion instanceof OrSearchCriterion) {
				sql = handleOrCriterion(context, (OrSearchCriterion) criterion, writers);
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

	private String handleOrCriterion(PersistenceContext context, OrSearchCriterion groupCriterion,
			List<PersistenceWriter> writers) throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (SearchCriterion criterion : groupCriterion.getCriterions()) {

			String sql = null;

			if (criterion instanceof SimpleSearchCriterion) {
				sql = handleSimpleCriterion(context, (SimpleSearchCriterion<?>) criterion, writers);
			} else if (criterion instanceof NotSearchCriterion) {
				sql = handleNotCriterion(context, (NotSearchCriterion) criterion, writers);
			} else if (criterion instanceof AndSearchCriterion) {
				sql = handleAndCriterion(context, (AndSearchCriterion) criterion, writers);
			} else if (criterion instanceof OrSearchCriterion) {
				sql = handleOrCriterion(context, (OrSearchCriterion) criterion, writers);
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

	private String handleSortings(PersistenceContext context, Map<Property<?>, Order> sortings)
			throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (Entry<Property<?>, Order> sorting : sortings.entrySet()) {

			Property<?> property = sorting.getKey();
			String order = getOrder(sorting.getValue());

			List<String> columns = getColumns(context, property.getName(), property.getType());

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

	private String getOperation(Operation operation) throws PersistenceException {

		if (operation == Operation.EQUALS) {
			return "=";
		} else if (operation == Operation.NOT_EQUALS) {
			return "!=";
		} else if (operation == Operation.LOWER) {
			return "<";
		} else if (operation == Operation.LOWER_OR_EQUALS) {
			return "<=";
		} else if (operation == Operation.GREATER) {
			return ">";
		} else if (operation == Operation.GREATER_OR_EQUALS) {
			return ">=";
		} else if (operation == Operation.IN) {
			return "in";
		}

		throw new PersistenceException(String.format("operation %s is not supported", operation));
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
