package com.ijioio.aes.core.persistence;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ijioio.aes.core.Property;

public interface PersistenceHandler {

	public <T> List<String> getColumns(PersistenceContext context, String name, Class<T> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, Boolean value, Class<Boolean> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, Character value, Class<Character> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, Byte value, Class<Byte> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, Short value, Class<Short> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, Integer value, Class<Integer> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, Long value, Class<Long> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, Float value, Class<Float> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, Double value, Class<Double> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, String value, Class<String> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, Instant value, Class<Instant> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, LocalDate value, Class<LocalDate> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, LocalTime value, Class<LocalTime> type)
			throws PersistenceException;

	public void write(PersistenceContext context, String name, LocalDateTime value, Class<LocalDateTime> type)
			throws PersistenceException;

	public <T> void write(PersistenceContext context, String name, T value, Class<T> type) throws PersistenceException;

	public void insert(PersistenceContext context, String table, Collection<Property<?>> properties,
			Map<String, PersistenceColumnProvider> columnProviders, Map<String, PersistenceWriter> writers)
			throws PersistenceException;

	public boolean read(PersistenceContext context, String name, boolean value, Class<Boolean> type)
			throws PersistenceException;

	public char read(PersistenceContext context, String name, char value, Class<Character> type)
			throws PersistenceException;

	public byte read(PersistenceContext context, String name, byte value, Class<Byte> type) throws PersistenceException;

	public short read(PersistenceContext context, String name, short value, Class<Short> type)
			throws PersistenceException;

	public int read(PersistenceContext context, String name, int value, Class<Integer> type)
			throws PersistenceException;

	public long read(PersistenceContext context, String name, long value, Class<Long> type) throws PersistenceException;

	public float read(PersistenceContext context, String name, float value, Class<Float> type)
			throws PersistenceException;

	public double read(PersistenceContext context, String name, double value, Class<Double> type)
			throws PersistenceException;

	public String read(PersistenceContext context, String name, String value, Class<String> type)
			throws PersistenceException;

	public Instant read(PersistenceContext context, String name, Instant value, Class<Instant> type)
			throws PersistenceException;

	public LocalDate read(PersistenceContext context, String name, LocalDate value, Class<LocalDate> type)
			throws PersistenceException;

	public LocalTime read(PersistenceContext context, String name, LocalTime value, Class<LocalTime> type)
			throws PersistenceException;

	public LocalDateTime read(PersistenceContext context, String name, LocalDateTime value, Class<LocalDateTime> type)
			throws PersistenceException;

	public <T> T read(PersistenceContext context, String name, T value, Class<T> type) throws PersistenceException;
}
