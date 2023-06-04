package com.ijioio.aes.core.entity.storage.physical.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import com.ijioio.aes.core.entity.storage.EntityData;
import com.ijioio.aes.core.entity.storage.StorageException;
import com.ijioio.aes.core.entity.storage.physical.PhysicalEntityStorage;
import com.ijioio.aes.core.entity.storage.physical.PhysicalTransaction;

public class JdbcPhysicalEntityStorage implements PhysicalEntityStorage {

	private final DataSource dataSource;

	public JdbcPhysicalEntityStorage(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void save(EntityData entityData, PhysicalTransaction transaction) throws StorageException {

		try {

			Connection connection = ((JdbcPhysicalTransaction) transaction).getConnection();

			// TODO: handle create or update!
			String sql = "insert into entitydata (id, entitytype, data) values (?, ?, ?)";

			try (PreparedStatement statement = connection.prepareStatement(sql)) {

				statement.setString(1, entityData.getId());
				statement.setString(2, entityData.getEntityType());
				statement.setBytes(3, entityData.getData());

				statement.executeUpdate();
			}

		} catch (Exception e) {
			throw new StorageException("save failed", e);
		}
	}

	@Override
	public PhysicalTransaction createTransaction() throws StorageException {

		try {

			Connection connection = dataSource.getConnection();

			return new JdbcPhysicalTransaction(connection);

		} catch (Exception e) {
			throw new StorageException("create transaction failed", e);
		}
	}
}
