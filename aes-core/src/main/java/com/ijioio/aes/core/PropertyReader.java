package com.ijioio.aes.core;

@FunctionalInterface
public interface PropertyReader<T> {

	public T read();
}
