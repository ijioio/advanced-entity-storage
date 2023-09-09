package com.ijioio.aes.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface EntityProperty {

	public String name();

	public String type();

	public boolean list() default false;
	
	public boolean set() default false;
	
	public boolean reference() default false;

	public Attribute[] attributes() default {};
}
