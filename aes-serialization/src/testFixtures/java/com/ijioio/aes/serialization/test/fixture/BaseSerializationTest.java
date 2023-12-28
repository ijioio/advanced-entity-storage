package com.ijioio.aes.serialization.test.fixture;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;

import com.ijioio.aes.serialization.SerializationHandler;

public abstract class BaseSerializationTest implements HandlerProvider {

	protected final Random random = new Random();

	protected SerializationHandler handler;

	@BeforeEach
	public void setup() throws Exception {

		long seed = System.currentTimeMillis();

		System.out.println(seed);

		random.setSeed(seed);

		handler = createHandler();
	}

	protected String readString(Path path) throws IOException {
		return Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n"));
	}
}
