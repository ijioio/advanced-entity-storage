package com.ijioio.aes.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Type {

	public static final String BOOLEAN = "BOOLEAN";

	public static final String CHAR = "CHAR";

	public static final String BYTE = "BYTE";

	public static final String SHORT = "SHORT";

	public static final String INT = "INT";

	public static final String LONG = "LONG";

	public static final String FLOAT = "FLOAT";

	public static final String DOUBLE = "DOUBLE";

	public static final String BYTE_ARRAY = "BYTE_ARRAY";

	public static final String STRING = "STRING";

	public static final String INSTANT = "INSTANT";

	public static final String LOCAL_DATE = "LOCAL_DATE";

	public static final String LOCAL_TIME = "LOCAL_TIME";

	public static final String LOCAL_DATE_TIME = "LOCAL_DATE_TIME";

	public static final String CLASS = "CLASS";

	public static final String LIST = "LIST";

	public static final String SET = "SET";

	public static final String MAP = "MAP";

	public static final String ENTITY_REFERENCE = "ENTITY_REFERENCE";

	public String name();

	public String type();

	public Parameter[] parameters() default {};

	public boolean list() default false;

	public boolean set() default false;

	public boolean reference() default false;
}
