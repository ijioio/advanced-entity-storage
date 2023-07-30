package com.ijioio.aes.core;

@FunctionalInterface
public interface PropertyWriter<T> {

	public void write(T value);
}
