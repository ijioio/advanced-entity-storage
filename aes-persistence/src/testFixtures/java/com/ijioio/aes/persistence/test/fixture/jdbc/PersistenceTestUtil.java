package com.ijioio.aes.persistence.test.fixture.jdbc;

import java.sql.Array;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.sql.DataSource;

public final class PersistenceTestUtil {

	public interface Callable {
		public void call(Connection connection) throws Exception;
	}

	public static void transactionally(DataSource dataSource, Callable callable) throws Exception {

		Connection connection = dataSource.getConnection();

		connection.setAutoCommit(false);

		try {

			callable.call(connection);

			connection.commit();

		} catch (Exception e) {

			connection.rollback();

			throw e;
		}
	}

	public static void execute(DataSource dataSource, String sql) throws Exception {

		try (Connection connection = dataSource.getConnection()) {

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
	}

	public static <C extends Comparable<C>> int compare(C o1, C o2) {

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

	public static <C extends Comparable<C>> int compare(Collection<C> o1, Collection<C> o2) {

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

	public static List<?> getList(ResultSet resultSet, String name) {

		try {

			Array array = resultSet.getObject(name, Array.class);

			if (array != null) {
				return Arrays.stream((Object[]) array.getArray()).collect(Collectors.toList());
			}

			return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] getBytes(ResultSet resultSet, String name) {

		try {

			Blob blob = resultSet.getObject(name, Blob.class);

			return getBytes(blob);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] getBytes(Blob blob) {

		try {

			if (blob != null) {
				return blob.getBytes(1, (int) blob.length());
			}

			return null;

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public static class ByteArray implements Comparable<ByteArray> {

		public static ByteArray of(byte[] data) {
			return new ByteArray(data);
		}

		private final byte[] data;

		private ByteArray(byte[] data) {
			this.data = data;
		}

		@Override
		public int hashCode() {

			final int prime = 31;

			int result = 1;

			result = prime * result + Arrays.hashCode(data);

			return result;
		}

		@Override
		public boolean equals(Object obj) {

			if (this == obj) {
				return true;
			}

			if (obj == null) {
				return false;
			}

			if (getClass() != obj.getClass()) {
				return false;
			}

			ByteArray other = (ByteArray) obj;

			return Arrays.equals(data, other.data);
		}

		@Override
		public int compareTo(ByteArray o) {

			if (this == o) {
				return 0;
			}

			if (o == null) {
				return 1;
			}

			return Arrays.compare(data, o.data);
		}
	}
}
