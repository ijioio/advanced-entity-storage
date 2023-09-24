package com.ijioio.aes.sandbox.test.persistence;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.ijioio.aes.sandbox.test.BaseTest;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class BasePersistenceTest extends BaseTest {

	protected HikariDataSource dataSource;

	protected Connection connection;

	@BeforeEach
	public void setup() throws Exception {

		HikariConfig config = new HikariConfig();

		config.setJdbcUrl("jdbc:h2:~/test");
		config.setUsername("su");
		config.setPassword("");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		dataSource = new HikariDataSource(config);
		connection = dataSource.getConnection();
	}

	@AfterEach
	public void shutdown() throws Exception {

		if (connection != null) {
			connection.close();
		}

		if (dataSource != null) {
			dataSource.close();
		}
	}

	protected void executeSql(Connection connection, Path path) throws IOException, SQLException {

		String sql = readString(path);

		try (Scanner scanner = new Scanner(sql)) {

			scanner.useDelimiter("(;(\r)?\n)|(--\n)");

			try (Statement statement = connection.createStatement()) {

				while (scanner.hasNext()) {

					String line = scanner.next();

					System.out.println("line -> " + line);

					if (line.startsWith("/*!") && line.endsWith("*/")) {
						int i = line.indexOf(' ');
						line = line.substring(i + 1, line.length() - " */".length());
					}

					if (line.trim().length() > 0) {
						statement.execute(line);
					}
				}
			}
		}
	}

	protected List<?> getArray(Array array) {

		if (array != null) {

			try {
				return Arrays.asList((Object[]) array.getArray());
			} catch (SQLException e) {
				new RuntimeException(e);
			}
		}

		return null;
	}

	protected <C extends Comparable<C>> int compare(C o1, C o2) {

		if (o1 == null && o2 == null) {
			return 0;
		}

		if (o1 == null) {
			return -1;
		}

		if (o2 == null) {
			return 1;
		}

		return o1.compareTo(o2);
	}

	protected <C extends Comparable<C>> int compare(Collection<C> o1, Collection<C> o2) {

		if (o1 == null && o2 == null) {
			return 0;
		}

		if (o1 == null) {
			return -1;
		}

		if (o2 == null) {
			return 1;
		}

		Iterator<C> i1 = o1.iterator();
		Iterator<C> i2 = o2.iterator();

		while (i1.hasNext() && i2.hasNext()) {

			int result = compare(i1.next(), i2.next());

			if (result != 0) {
				return result;
			}
		}

		return Integer.compare(o1.size(), o2.size());
	}
}
