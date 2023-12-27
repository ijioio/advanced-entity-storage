package com.ijioio.aes.persistence.test.fixture.jdbc;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import com.ijioio.aes.persistence.jdbc.JdbcPersistenceHandler;

public abstract class BasePersistenceTest implements DataSourceProvider, HandlerProvider {

	protected final Random random = new Random();

	protected DataSource dataSource;

	protected JdbcPersistenceHandler handler;

	@BeforeEach
	public void setup() throws Exception {

		long seed = System.currentTimeMillis();

		System.out.println(seed);

		random.setSeed(seed);

		dataSource = getDataSource();

		handler = createHandler(dataSource);
	}

	@AfterEach
	public void shutdown() throws Exception {

		if (dataSource instanceof Closeable) {
			((Closeable) dataSource).close();
		}
	}

	protected String readString(Path path) throws IOException {
		return Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n"));
	}
}
