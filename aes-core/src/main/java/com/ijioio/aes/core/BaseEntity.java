package com.ijioio.aes.core;

/**
 * Base class for all entities.
 */
public abstract class BaseEntity extends BaseIdentity implements Entity {

  @Override
  public String toString() {
    return "BaseEntity [id=" + getId() + ", type=" + getClass().getName() + "]";
  }
}
