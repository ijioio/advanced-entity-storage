package com.ijioio.aes.sandbox.test.persistence.index.property;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class DeleteMeTest {

	@Test
	public void test() throws Exception {

		System.out.println(Collections.singletonList("").getClass());

		byte[] bytes = "".getBytes(StandardCharsets.UTF_8);

		System.out.println(Arrays.toString(bytes));
		System.out.println("\"" + Base64.getEncoder().encodeToString(bytes) + "\"");
		System.out.println(Arrays.toString(Base64.getDecoder().decode("")));
	}
}
