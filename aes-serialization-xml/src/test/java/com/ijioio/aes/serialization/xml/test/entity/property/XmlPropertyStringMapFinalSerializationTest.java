package com.ijioio.aes.serialization.xml.test.entity.property;

import com.ijioio.aes.serialization.test.fixture.entity.property.BasePropertyStringMapFinalSerializationTest;
import com.ijioio.aes.serialization.xml.test.XmlHandlerProvider;

public class XmlPropertyStringMapFinalSerializationTest extends BasePropertyStringMapFinalSerializationTest
		implements XmlHandlerProvider {

	@Override
	protected String getDataFilePath() {
		return "serialization/entity/property/property-string-map-final-serialization.xml";
	}

	@Override
	protected String getNullDataFilePath() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getEntriesEmptyDataFilePath() {
		return "serialization/entity/property/property-string-map-entries-empty-final-serialization.xml";
	}

	@Override
	protected String getEntriesNullDataFilePath() {
		return "serialization/entity/property/property-string-map-entries-null-final-serialization.xml";
	}
}
