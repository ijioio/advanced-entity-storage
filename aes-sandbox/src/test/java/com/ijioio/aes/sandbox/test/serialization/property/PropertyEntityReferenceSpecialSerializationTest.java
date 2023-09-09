package com.ijioio.aes.sandbox.test.serialization.property;

import com.ijioio.aes.sandbox.test.serialization.BaseSerializationTest;

public class PropertyEntityReferenceSpecialSerializationTest extends BaseSerializationTest {

//	public static class Some extends BaseEntity {
//		// Empty
//	}
//
//	@Entity( //
//			name = PropertyEntityReferenceSpecialSerializationPrototype.NAME, //
//			properties = { //
//					@EntityProperty(name = "valueEntityReference", type = @Type(name = "com.ijioio.aes.sandbox.test.serialization.property.PropertyEntityReferenceSpecialSerializationTest.Some", reference = true)) //
//			} //
//	)
//	public static interface PropertyEntityReferenceSpecialSerializationPrototype {
//
//		public static final String NAME = "com.ijioio.test.model.PropertyEntityReferenceSpecialSerialization";
//	}
//
//	private Path path;
//
//	private PropertyEntityReferenceSpecialSerialization model;
//
//	@BeforeEach
//	public void before() throws Exception {
//
//		path = Paths.get(
//				getClass().getClassLoader().getResource("property-entity-reference-special-serialization.xml").toURI());
//
//		model = new PropertyEntityReferenceSpecialSerialization();
//
//		model.setId("property-entity-reference-special-serialization");
//		model.setValueEntityReference(EntityReference.of("some", Some.class));
//	}
//
//	@Test
//	public void testWrite() throws Exception {
//
//		XmlSerializationHandler handler = new XmlSerializationHandler();
//
//		String actual = XmlUtil.write(handler, model);
//		String expected = readString(path);
//
//		Assertions.assertEquals(expected, actual);
//	}
//
//	@Test
//	public void testRead() throws Exception {
//
//		XmlSerializationHandler handler = new XmlSerializationHandler();
//
//		PropertyEntityReferenceSpecialSerialization actual = XmlUtil.read(handler,
//				PropertyEntityReferenceSpecialSerialization.class, readString(path));
//		PropertyEntityReferenceSpecialSerialization expected = model;
//
//		Assertions.assertEquals(expected.getId(), actual.getId());
//		Assertions.assertEquals(expected.getValueEntityReference(), actual.getValueEntityReference());
//	}
}
