package com.ijioio.aes.serialization.xml.test.entity.property;

import com.ijioio.aes.serialization.test.fixture.entity.property.BasePropertyStringMapSerializationTest;
import com.ijioio.aes.serialization.xml.test.XmlHandlerProvider;

public class XmlPropertyStringMapSerializationTest extends BasePropertyStringMapSerializationTest
		implements XmlHandlerProvider {

	@Override
	protected String getDataFilePath() {
		return "serialization/entity/property/property-string-map-serialization.xml";
	}

	@Override
	protected String getNullDataFilePath() {
		return "serialization/entity/property/property-string-map-null-serialization.xml";
	}

	@Override
	protected String getEntriesEmptyDataFilePath() {
		return "serialization/entity/property/property-string-map-entries-empty-serialization.xml";
	}

	@Override
	protected String getEntriesNullDataFilePath() {
		return "serialization/entity/property/property-string-map-entries-null-serialization.xml";
	}
}
