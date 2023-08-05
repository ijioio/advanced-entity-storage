package com.ijioio.aes.core.persistence.jdbc;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ijioio.aes.core.CollectionProperty;
import com.ijioio.aes.core.EntityIndex;
import com.ijioio.aes.core.EntityReference;
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
import com.ijioio.aes.core.persistence.PersistenceException;
import com.ijioio.aes.core.persistence.PersistenceHandler;
import com.ijioio.aes.core.util.TupleUtil.Pair;

public class JdbcPersistenceHandler implements PersistenceHandler<JdbcPersistenceContext> {

	private static final JdbcPersistenceValueHandler<Boolean> HANDLER_BOOLEAN = new JdbcPersistenceValueHandler<Boolean>() {

		@Override
		public Class<Boolean> getType() {
			return Boolean.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<Boolean> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Boolean> property,
				Boolean value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Boolean read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Boolean> property,
				Boolean value) throws PersistenceException {

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
				Property<Character> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Character> property,
				Character value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Character read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<Character> property, Character value) throws PersistenceException {

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
				Property<Byte> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Byte> property,
				Byte value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Byte read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Byte> property,
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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<Short> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Short> property,
				Short value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Short read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Short> property,
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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<Integer> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Integer> property,
				Integer value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Integer read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Integer> property,
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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<Long> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Long> property,
				Long value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Long read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Long> property,
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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<Float> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Float> property,
				Float value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Float read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Float> property,
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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<Double> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Double> property,
				Double value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Double read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Double> property,
				Double value) throws PersistenceException {

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
				Property<String> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<String> property,
				String value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value, Types.VARCHAR);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				CollectionProperty<? extends Collection<String>, String> property, Collection<String> value)
				throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setArray(context.getNextIndex(), statement.getConnection()
						.createArrayOf(JDBCType.valueOf(Types.VARCHAR).getName(), value.toArray()));
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};

		@Override
		public String read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<String> property,
				String value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return resultSet.getObject(context.getNextIndex(), String.class);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		};

		@Override
		public Collection<String> read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				CollectionProperty<? extends Collection<String>, String> property, Collection<String> value)
				throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {

				Collection<String> collection = List.class.isAssignableFrom(property.getType()) ? new ArrayList<>()
						: new LinkedHashSet<>();

				collection.clear();
				collection.addAll(
						Arrays.asList((String[]) resultSet.getObject(context.getNextIndex(), Array.class).getArray()));

				return collection;

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
				Property<Instant> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Instant> property,
				Instant value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Timestamp.from(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public Instant read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Instant> property,
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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<LocalDate> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<LocalDate> property,
				LocalDate value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Date.valueOf(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public LocalDate read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<LocalDate> property, LocalDate value) throws PersistenceException {

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
				Property<LocalTime> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<LocalTime> property,
				LocalTime value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Time.valueOf(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public LocalTime read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<LocalTime> property, LocalTime value) throws PersistenceException {

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
				Property<LocalDateTime> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<LocalDateTime> property, LocalDateTime value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? Timestamp.valueOf(value) : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@Override
		public LocalDateTime read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<LocalDateTime> property, LocalDateTime value) throws PersistenceException {

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
				Property<Enum> property) {
			return Collections.singletonList(property.getName());
		};

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Enum> property,
				Enum value) throws PersistenceException {

			PreparedStatement statement = context.getStatement();

			try {
				statement.setObject(context.getNextIndex(), value != null ? value.name() : null);
			} catch (SQLException e) {
				throw new PersistenceException(e);
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public Enum read(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Enum> property,
				Enum value) throws PersistenceException {

			ResultSet resultSet = context.getResultSet();

			try {
				return Optional.ofNullable(resultSet.getObject(context.getNextIndex(), String.class))
						.map(item -> Enum.valueOf(property.getType(), item)).orElse(null);
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
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<Collection> property) {

			CollectionProperty<Collection, ?> collectionProperty = (CollectionProperty<Collection, ?>) property;

			JdbcPersistenceValueHandler elementHandler = handler.getValueHandler(collectionProperty.getElementType());

			return elementHandler.getColumns(context, handler,
					Property.of(collectionProperty.getName(), collectionProperty.getElementType()));
		}

		@SuppressWarnings("unchecked")
		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler, Property<Collection> property,
				Collection value) throws PersistenceException {

			CollectionProperty<Collection, ?> collectionProperty = (CollectionProperty<Collection, ?>) property;

			JdbcPersistenceValueHandler elementHandler = handler.getValueHandler(collectionProperty.getElementType());

			elementHandler.write(context, handler, collectionProperty, value);
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<Collection> property, Collection value) throws PersistenceException {

			CollectionProperty<Collection, ?> collectionProperty = (CollectionProperty<Collection, ?>) property;

			JdbcPersistenceValueHandler elementHandler = handler.getValueHandler(collectionProperty.getElementType());

			return elementHandler.read(context, handler, collectionProperty, value);
		}
	};

	@SuppressWarnings("rawtypes")
	private static final JdbcPersistenceValueHandler<EntityReference> HANDLER_ENTITY_REFERENCE = new JdbcPersistenceValueHandler<EntityReference>() {

		@Override
		public Class<EntityReference> getType() {
			return EntityReference.class;
		}

		@Override
		public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<EntityReference> property) {

			Property<String> idProperty = Property.of(String.format("%sId", property.getName()), String.class);
			Property<String> typeProperty = Property.of(String.format("%sType", property.getName()), String.class);

			JdbcPersistenceValueHandler<String> idHandler = handler.getValueHandler(idProperty.getType());
			JdbcPersistenceValueHandler<String> typeHandler = handler.getValueHandler(typeProperty.getType());

			return Stream
					.of(idHandler.getColumns(context, handler, idProperty),
							typeHandler.getColumns(context, handler, typeProperty))
					.flatMap(item -> item.stream()).collect(Collectors.toList());
		}

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<EntityReference> property, EntityReference value) throws PersistenceException {

			Property<String> idProperty = Property.of(String.format("%sId", property.getName()), String.class);
			Property<String> typeProperty = Property.of(String.format("%sType", property.getName()), String.class);

			JdbcPersistenceValueHandler<String> idHandler = handler.getValueHandler(idProperty.getType());
			JdbcPersistenceValueHandler<String> typeHandler = handler.getValueHandler(typeProperty.getType());

			idHandler.write(context, handler, idProperty, value != null ? value.getId() : null);
			typeHandler.write(context, handler, typeProperty, value != null ? value.getType().getName() : null);
		}

		@Override
		public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				CollectionProperty<? extends Collection<EntityReference>, EntityReference> property,
				Collection<EntityReference> values) throws PersistenceException {

			CollectionProperty<List<String>, String> idProperty = CollectionProperty
					.of(String.format("%sId", property.getName()), List.class, String.class);
			CollectionProperty<List<String>, String> typeProperty = CollectionProperty
					.of(String.format("%sType", property.getName()), List.class, String.class);

			JdbcPersistenceValueHandler<String> idHandler = handler.getValueHandler(idProperty.getElementType());
			JdbcPersistenceValueHandler<String> typeHandler = handler.getValueHandler(typeProperty.getElementType());

			List<String> idValues = new ArrayList<>();
			List<String> typeValues = new ArrayList<>();

			for (EntityReference value : values) {

				idValues.add(value != null ? value.getId() : null);
				typeValues.add(value != null ? value.getType().getName() : null);
			}

			idHandler.write(context, handler, idProperty, idValues);
			typeHandler.write(context, handler, typeProperty, typeValues);
		}

		@SuppressWarnings("unchecked")
		@Override
		public EntityReference read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				Property<EntityReference> property, EntityReference value) throws PersistenceException {

			try {

				Property<String> idProperty = Property.of(String.format("%sId", property.getName()), String.class);
				Property<String> typeProperty = Property.of(String.format("%sType", property.getName()), String.class);

				JdbcPersistenceValueHandler<String> idHandler = handler.getValueHandler(idProperty.getType());
				JdbcPersistenceValueHandler<String> typeHandler = handler.getValueHandler(typeProperty.getType());

				String idValue = idHandler.read(context, handler, idProperty, null);
				Class typeValue = Class.forName(typeHandler.read(context, handler, typeProperty, null));

				return EntityReference.of(idValue, typeValue);

			} catch (ClassNotFoundException e) {
				throw new PersistenceException(e);
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		public Collection<EntityReference> read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
				CollectionProperty<? extends Collection<EntityReference>, EntityReference> property,
				Collection<EntityReference> value) throws PersistenceException {

			try {

				CollectionProperty<List<String>, String> idProperty = CollectionProperty
						.of(String.format("%sId", property.getName()), List.class, String.class);
				CollectionProperty<List<String>, String> typeProperty = CollectionProperty
						.of(String.format("%sType", property.getName()), List.class, String.class);

				JdbcPersistenceValueHandler<String> idHandler = handler.getValueHandler(idProperty.getElementType());
				JdbcPersistenceValueHandler<String> typeHandler = handler
						.getValueHandler(typeProperty.getElementType());

				List<String> idValues = (List<String>) idHandler.read(context, handler, idProperty, null);
				List<String> typeValues = (List<String>) typeHandler.read(context, handler, typeProperty, null);

				// TODO: check on size equality!

				Collection<EntityReference> collection = List.class.isAssignableFrom(property.getType())
						? new ArrayList<>()
						: new LinkedHashSet<>();

				for (int i = 0; i < idValues.size(); i++) {

					String idValue = idValues.get(i);
					Class typeValue = Class.forName(typeValues.get(i));

					collection.add(EntityReference.of(idValue, typeValue));
				}

				return collection;

			} catch (ClassNotFoundException e) {
				throw new PersistenceException(e);
			}
		}
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
		registerValueHandler(HANDLER_COLLECTION);
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void create(JdbcPersistenceContext context, EntityIndex<?> index) throws PersistenceException {

		Connection connection = context.getConnection();

		try {

			Collection<Property<?>> properties = index.getProperties();
			Map<Property<?>, PropertyReader<?>> readers = index.getReaders();

			List<String> columns = new ArrayList<>();

			for (Property<?> property : properties) {
				columns.addAll(getColumns(context, property));
			}

			String sql = String.format("insert into %s (%s) values (%s)", index.getClass().getSimpleName(),
					columns.stream().collect(Collectors.joining(", ")),
					columns.stream().map(item -> "?").collect(Collectors.joining(", ")));

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				for (Property<?> property : properties) {

					PropertyReader<?> reader = readers.get(property);

					if (reader == null) {
						throw new PersistenceException(
								String.format("reader for property %s is not found", property.getName()));
					}

					write(context, (Property) property, reader.read());
				}

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

			List<Pair<Property<?>, PropertyReader<?>>> readers = new ArrayList<>();

			String sql = handleDeleteQuery(context, query, readers);

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				for (Pair<Property<?>, PropertyReader<?>> reader : readers) {
					write(context, (Property) reader.getFirst(), reader.getSecond());
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

			List<Pair<Property<?>, PropertyReader<?>>> queryValueReaders = new ArrayList<>();

			String sql = handleSearchQuery(context, query, queryValueReaders);

			System.out.println(sql);

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				context.setStatement(statement);
				context.resetIndex();

				for (Pair<Property<?>, PropertyReader<?>> queryValueReader : queryValueReaders) {
					write(context, (Property) queryValueReader.getFirst(), queryValueReader.getSecond().read());
				}

				System.out.println(statement);

				try (ResultSet resultSet = statement.executeQuery()) {

					context.setResultSet(resultSet);

					List<I> indexes = new ArrayList<>();

					while (resultSet.next()) {

						I index = query.getType().newInstance();

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

							((PropertyWriter) writer).write(read(context, (Property) property, reader.read()));
						}

						indexes.add(index);
					}

					return indexes;
				}
			}

		} catch (IllegalAccessException | InstantiationException | SQLException e) {
			throw new PersistenceException(e);
		}
	}

	private <T> List<String> getColumns(JdbcPersistenceContext context, Property<T> property)
			throws PersistenceException {

		JdbcPersistenceValueHandler<T> handler = getValueHandler(property.getType());

		if (handler != null) {
			return handler.getColumns(context, this, property);
		} else {
			throw new PersistenceException(String.format("type %s is not supported", property.getType()));
		}
	}

	private <T> void write(JdbcPersistenceContext context, Property<T> property, T value) throws PersistenceException {

		JdbcPersistenceValueHandler<T> handler = getValueHandler(property.getType());

		if (handler != null) {
			handler.write(context, this, property, value);
		} else {
			throw new PersistenceException(String.format("type %s is not supported", property.getType()));
		}
	}

	private <T> T read(JdbcPersistenceContext context, Property<T> property, T value) throws PersistenceException {

		JdbcPersistenceValueHandler<T> handler = getValueHandler(property.getType());

		if (handler != null) {
			return handler.read(context, this, property, value);
		} else {
			throw new PersistenceException(String.format("type %s is not supported", property.getType()));
		}
	}

	private <I extends EntityIndex<?>> String handleDeleteQuery(JdbcPersistenceContext context, SearchQuery<I> query,
			List<Pair<Property<?>, PropertyReader<?>>> readers) throws PersistenceException {

		String conditions = handleAndCriterion(context, AndSearchCriterion.of(query.getCriterions()), readers);

		StringBuilder sql = new StringBuilder();

		sql.append(String.format("delete from %s", query.getType().getSimpleName()));

		if (conditions != null) {
			sql.append(String.format(" where %s", conditions));
		}

		return sql.toString();
	}

	private <I extends EntityIndex<?>> String handleSearchQuery(JdbcPersistenceContext context, SearchQuery<I> query,
			List<Pair<Property<?>, PropertyReader<?>>> readers) throws PersistenceException {

		String conditions = handleAndCriterion(context, AndSearchCriterion.of(query.getCriterions()), readers);
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

	private <T> String handleSimpleCriterion(JdbcPersistenceContext context, SimpleSearchCriterion<T> criterion,
			List<Pair<Property<?>, PropertyReader<?>>> readers) throws PersistenceException {

		Property<T> property = criterion.getProperty();

		readers.add(Pair.of(property, () -> criterion.getValue()));

		List<String> columns = getColumns(context, property);

		String operation = getOperation(criterion.getOperation());

		return columns.stream().map(item -> String.format("%s %s ?", item, operation))
				.collect(Collectors.joining(" and "));
	}

	private String handleNotCriterion(JdbcPersistenceContext context, NotSearchCriterion groupCriterion,
			List<Pair<Property<?>, PropertyReader<?>>> readers) throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (SearchCriterion criterion : groupCriterion.getCriterions()) {

			String sql = null;

			if (criterion instanceof SimpleSearchCriterion) {
				sql = handleSimpleCriterion(context, (SimpleSearchCriterion<?>) criterion, readers);
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
			List<Pair<Property<?>, PropertyReader<?>>> readers) throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (SearchCriterion criterion : groupCriterion.getCriterions()) {

			String sql = null;

			if (criterion instanceof SimpleSearchCriterion) {
				sql = handleSimpleCriterion(context, (SimpleSearchCriterion<?>) criterion, readers);
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
			List<Pair<Property<?>, PropertyReader<?>>> readers) throws PersistenceException {

		List<String> sqls = new ArrayList<>();

		for (SearchCriterion criterion : groupCriterion.getCriterions()) {

			String sql = null;

			if (criterion instanceof SimpleSearchCriterion) {
				sql = handleSimpleCriterion(context, (SimpleSearchCriterion<?>) criterion, readers);
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

			List<String> columns = getColumns(context, property);

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