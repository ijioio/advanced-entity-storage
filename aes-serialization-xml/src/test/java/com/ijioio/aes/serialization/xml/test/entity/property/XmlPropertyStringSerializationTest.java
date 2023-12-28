package com.ijioio.aes.serialization.xml.test.entity.property;

import com.ijioio.aes.serialization.test.fixture.entity.property.BasePropertyStringSerializationTest;
import com.ijioio.aes.serialization.xml.test.XmlHandlerProvider;

public class XmlPropertyStringSerializationTest extends BasePropertyStringSerializationTest implements XmlHandlerProvider {

	@Override
	protected String getDataFilePath() {
		return "serialization/entity/property/property-string-serialization.xml";
	}

	@Override
	protected String getNullDataFilePath() {
		return "serialization/entity/property/property-string-null-serialization.xml";
	}
}
