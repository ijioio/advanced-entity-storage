package com.ijioio.aes.persistence.jdbc.value.handler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ijioio.aes.core.EntityReference;
import com.ijioio.aes.core.TypeReference;
import com.ijioio.aes.persistence.PersistenceException;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceContext;
import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;

@SuppressWarnings("rawtypes")
public class JdbcEntityReferencePersistenceValueHandler extends BaseJdbcPersistenceValueHandler<EntityReference> {

	protected final TypeReference<String> idType = TypeReference.of(String.class);

	protected final TypeReference<Class> typeType = TypeReference.of(Class.class);

	protected final TypeReference<List<String>> idListType = new TypeReference<List<String>>() {
	};

	protected final TypeReference<List<Class>> typeListType = new TypeReference<List<Class>>() {
	};

	@Override
	public Class<EntityReference> getType() {
		return EntityReference.class;
	}

	@Override
	public List<String> getColumns(JdbcPersistenceContext context, JdbcPersistenceHandler handler, String name,
			TypeReference<EntityReference> type, boolean search) throws PersistenceException {
		return Stream
				.of(handler.getValueHandler(idType.getRawType()).getColumns(context, handler,
						String.format("%sId", name), idType, search),
						search ? Collections.<String>emptyList()
								: handler.getValueHandler(typeType.getRawType()).getColumns(context, handler,
										String.format("%sType", name), typeType, search))
				.flatMap(item -> item.stream()).collect(Collectors.toList());
	}

	@Override
	public void write(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<EntityReference> type, EntityReference value, boolean search) throws PersistenceException {

		handler.getValueHandler(idType.getRawType()).write(context, handler, idType,
				value != null ? value.getId() : null, search);

		if (!search) {
			handler.getValueHandler(typeType.getRawType()).write(context, handler, typeType,
					value != null ? value.getType() : null, search);
		}
	}

	@Override
	public void writeCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<EntityReference>> type, Collection<EntityReference> values,
			boolean search) throws PersistenceException {

		if (values != null) {

			List<String> idValues = new ArrayList<>();
			List<Class> typeValues = new ArrayList<>();

			for (EntityReference value : values) {

				idValues.add(value != null ? value.getId() : null);
				typeValues.add(value != null ? value.getType() : null);
			}

			handler.getValueHandler(idListType.getRawType()).write(context, handler, idListType, idValues, search);

			if (!search) {
				handler.getValueHandler(typeListType.getRawType()).write(context, handler, typeListType, typeValues,
						search);
			}

		} else {

			handler.getValueHandler(idListType.getRawType()).write(context, handler, idListType, null, search);

			if (!search) {
				handler.getValueHandler(typeListType.getRawType()).write(context, handler, typeListType, null, search);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public EntityReference read(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<EntityReference> type, EntityReference value) throws PersistenceException {

		String idValue = handler.getValueHandler(idType.getRawType()).read(context, handler, idType, null);
		Class typeValue = handler.getValueHandler(typeType.getRawType()).read(context, handler, typeType, null);

		if ((idValue == null && typeValue != null) || (idValue != null && typeValue == null)) {
			throw new PersistenceException(
					String.format("id value %s and type value %s are incosistent", idValue, typeValue));
		}

		return idValue != null && typeValue != null ? EntityReference.of(idValue, typeValue) : null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<EntityReference> readCollection(JdbcPersistenceContext context, JdbcPersistenceHandler handler,
			TypeReference<? extends Collection<EntityReference>> type, Collection<EntityReference> values)
			throws PersistenceException {

		List<String> idValues = handler.getValueHandler(idListType.getRawType()).read(context, handler, idListType,
				null);
		List<Class> typeValues = handler.getValueHandler(typeListType.getRawType()).read(context, handler, typeListType,
				null);

		if ((idValues == null && typeValues != null) || (idValues != null && typeValues == null)
				|| (idValues != null && typeValues != null && idValues.size() != typeValues.size())) {
			throw new PersistenceException(
					String.format("id values %s and type values %s are incosistent", idValues, typeValues));
		}

		if (idValues != null && typeValues != null) {

			Collection<EntityReference> collection = getCollection(type, values);

			collection.clear();

			Iterator<String> idValuesIterator = idValues.iterator();
			Iterator<Class> typeValuesIterator = typeValues.iterator();

			while (idValuesIterator.hasNext()) {

				String idValue = idValuesIterator.next();
				Class typeValue = typeValuesIterator.next();

				if ((idValue == null && typeValue != null) || (idValue != null && typeValue == null)) {
					throw new PersistenceException(
							String.format("id value %s and type value %s are incosistent", idValue, typeValue));
				}

				collection.add(idValue != null && typeValue != null ? EntityReference.of(idValue, typeValue) : null);
			}

			return collection;

		} else {

			if (values != null) {
				values.clear();
			}

			return null;
		}
	}
}
