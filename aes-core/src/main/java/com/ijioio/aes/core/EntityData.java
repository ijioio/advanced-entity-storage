package com.ijioio.aes.core;

public class EntityData<E extends Entity> {

	private String id;

	private Class<E> entityType;

	private byte[] data;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Class<E> getEntityType() {
		return entityType;
	}

	public void setEntityType(Class<E> entityType) {
		this.entityType = entityType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
