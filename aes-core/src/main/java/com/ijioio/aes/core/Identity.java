package com.ijioio.aes.core;

/**
 * Interface defining identity. Identity guarantees that implementations would
 * expose an {@code id} property.
 */
public interface Identity {

	public String getId();

	public void setId(String id);
}
