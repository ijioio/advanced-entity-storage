package com.ijioio.aes.core.serialization;

import java.util.Map;

public interface SerializationHandler {

	public void write(SerializationContext context, String name, boolean value) throws SerializationException;

	public void write(SerializationContext context, String name, byte value) throws SerializationException;

	public void write(SerializationContext context, String name, short value) throws SerializationException;

	public void write(SerializationContext context, String name, int value) throws SerializationException;

	public void write(SerializationContext context, String name, long value) throws SerializationException;

	public void write(SerializationContext context, String name, char value) throws SerializationException;

	public void write(SerializationContext context, String name, float value) throws SerializationException;

	public void write(SerializationContext context, String name, double value) throws SerializationException;

	public void write(SerializationContext context, String name, String value) throws SerializationException;

	public <T> void write(SerializationContext context, String name, T value) throws SerializationException;

	public void write(SerializationContext context, Map<String, SerializationWriter> writers)
			throws SerializationException;

	public boolean read(SerializationContext context, boolean value) throws SerializationException;

	public byte read(SerializationContext context, byte value) throws SerializationException;

	public short read(SerializationContext context, short value) throws SerializationException;

	public int read(SerializationContext context, int value) throws SerializationException;

	public long read(SerializationContext context, long value) throws SerializationException;

	public char read(SerializationContext context, char value) throws SerializationException;

	public float read(SerializationContext context, float value) throws SerializationException;

	public double read(SerializationContext context, double value) throws SerializationException;

	public String read(SerializationContext context, String value) throws SerializationException;

	public <T> T read(SerializationContext context, T value) throws SerializationException;

	public void read(SerializationContext context, Map<String, SerializationReader> readers)
			throws SerializationException;
}
