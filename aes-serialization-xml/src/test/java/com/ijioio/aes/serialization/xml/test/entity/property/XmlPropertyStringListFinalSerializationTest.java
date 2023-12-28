package com.ijioio.aes.serialization.xml.test.entity.property;

import com.ijioio.aes.serialization.test.fixture.entity.property.BasePropertyStringListFinalSerializationTest;
import com.ijioio.aes.serialization.xml.test.XmlHandlerProvider;

public class XmlPropertyStringListFinalSerializationTest extends BasePropertyStringListFinalSerializationTest
		implements XmlHandlerProvider {

	@Override
	protected String getDataFilePath() {
		return "serialization/entity/property/property-string-list-final-serialization.xml";
	}

	@Override
	protected String getNullDataFilePath() {
		throw new UnsupportedOperationException();
	}

	@Override
	protected String getElementsEmptyDataFilePath() {
		return "serialization/entity/property/property-string-list-elements-empty-final-serialization.xml";
	}

	@Override
	protected String getElementsNullDataFilePath() {
		return "serialization/entity/property/property-string-list-elements-null-final-serialization.xml";
	}
}
