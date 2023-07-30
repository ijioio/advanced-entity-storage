package com.ijioio.aes.core;

import java.util.Map;

public interface Writable {

	public Map<Property<?>, PropertyWriter<?>> getWriters();
}
