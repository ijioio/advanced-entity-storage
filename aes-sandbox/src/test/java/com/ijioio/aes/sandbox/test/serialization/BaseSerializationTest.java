package com.ijioio.aes.sandbox.test.serialization;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class BaseSerializationTest {

	protected String readString(Path path) throws IOException {
		return Files.lines(path, StandardCharsets.UTF_8).collect(Collectors.joining("\n"));
	}
}
