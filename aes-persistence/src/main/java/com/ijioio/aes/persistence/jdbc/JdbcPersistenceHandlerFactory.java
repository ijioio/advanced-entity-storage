package com.ijioio.aes.persistence.jdbc;

import java.sql.SQLException;
import java.util.ServiceLoader;

import javax.sql.DataSource;

import com.ijioio.aes.persistence.PersistenceException;

public class JdbcPersistenceHandlerFactory {

	public static JdbcPersistenceHandler create(DataSource dataSource) throws PersistenceException {

		try {

			String productName = dataSource.getConnection().getMetaData().getDatabaseProductName();

			ServiceLoader<JdbcPersistenceHandlerProvider> providers = ServiceLoader
					.load(JdbcPersistenceHandlerProvider.class);

			for (JdbcPersistenceHandlerProvider provider : providers) {

				if (provider.accept(productName)) {
					return provider.create(dataSource);
				}
			}

			throw new PersistenceException(
					String.format("unable to find persistence handler for product name %s", productName));

		} catch (SQLException e) {
			throw new PersistenceException(e);
		}
	}
}
