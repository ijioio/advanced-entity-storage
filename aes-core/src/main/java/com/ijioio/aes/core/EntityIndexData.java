package com.ijioio.aes.core;

import java.util.Set;

/**
 * Interface defining index data of the entity index. Entity index data contains
 * entity index properties for persisting.
 */
public interface EntityIndexData<E extends Entity, I extends EntityIndex<E>> extends Identity {

	public String getSourceId();

	public String getSourceType();

	public String getSourceCaption();

	public EntityReference<E> getSource();

	/**
	 * Creates {@link EntityIndex} based on the current index data. If
	 * {@code properties} set is provided, then index will be populated only with
	 * the indicated properties. Otherwise, if {@code properties} set is
	 * {@code null} or {@code empty}, index will be populated with all the available
	 * properties.
	 * 
	 * @param properties containing the list of properties to populate the index
	 * @return entity index based on the current index data
	 */
	public I toIndex(Set<String> properties);
}
