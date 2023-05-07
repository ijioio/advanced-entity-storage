package com.ijioio.aes.core;

/**
 * Base class for all entity index datas.
 */
public abstract class BaseEntityIndexData<E extends Entity, I extends EntityIndex<E>> implements EntityIndexData<E, I> {

	private String id;

	private String sourceId;

	private String sourceType;

	private String sourceCaption;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	@Override
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	@Override
	public String getSourceCaption() {
		return sourceCaption;
	}

	public void setSourceCaption(String sourceCaption) {
		this.sourceCaption = sourceCaption;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EntityReference<E> getSource() {

		try {
			return EntityReference.of(sourceId, (Class<E>) Class.forName(sourceType));
		} catch (Exception e) {
			return null;
		}
	}

	public void setSource(EntityReference<E> source) {

		sourceId = source.getId();
		sourceType = source.getType().getName();
	}
}
