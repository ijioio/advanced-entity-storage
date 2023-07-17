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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.Property;
import com.ijioio.aes.core.persistence.PersistenceColumnProvider;
import com.ijioio.aes.core.persistence.PersistenceContext;
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.PersistenceHandler;
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
				return (JdbcPersistenceValueHandler<T>) handlers.get(Enum.class.getName());
			}

			if (Collection.class.isAssignableFrom(type)) {
				return (JdbcPersistenceValueHandler<T>) handlers.get(Collection.class.getName());
			}
		}

		return null;
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
	public void insert(PersistenceContext context, String table, Collection<Property<?>> properties,
			Map<String, PersistenceColumnProvider> columnProviders, Map<String, PersistenceWriter> writers)
			throws PersistenceException {

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
					Stream.generate(() -> "?").limit(columns.size()).collect(Collectors.joining(", ")));

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
	public <T> T read(PersistenceContext context, String name, T value, Class<T> type) throws PersistenceException {

		JdbcPersistenceValueHandler<T> handler = getValueHandler(type);

		if (handler != null) {
			return handler.read((JdbcPersistenceContext) context, this, value, type);
		} else {
			throw new PersistenceException(String.format("type %s is not supported", type));
		}
	}
}
