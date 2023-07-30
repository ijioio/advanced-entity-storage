package com.ijioio.aes.core;

import java.util.Map;

public interface Readable {

	public Map<Property<?>, PropertyReader<?>> getReaders();
}
