package com.ijioio.aes.serialization.xml.test.entity.property;

import com.ijioio.aes.serialization.test.fixture.entity.property.BasePropertyStringListSerializationTest;
import com.ijioio.aes.serialization.xml.test.XmlHandlerProvider;

public class XmlPropertyStringListSerializationTest extends BasePropertyStringListSerializationTest
		implements XmlHandlerProvider {

	@Override
	protected String getDataFilePath() {
		return "serialization/entity/property/property-string-list-serialization.xml";
	}

	@Override
	protected String getNullDataFilePath() {
		return "serialization/entity/property/property-string-list-null-serialization.xml";
	}

	@Override
	protected String getElementsEmptyDataFilePath() {
		return "serialization/entity/property/property-string-list-elements-empty-serialization.xml";
	}

	@Override
	protected String getElementsNullDataFilePath() {
		return "serialization/entity/property/property-string-list-elements-null-serialization.xml";
	}
}
