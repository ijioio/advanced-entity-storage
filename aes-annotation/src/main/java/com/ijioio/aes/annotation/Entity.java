package com.ijioio.aes.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface Entity {

	public String name();

	public String parent() default "";

	public Class<?>[] interfaces() default {};

	public Attribute[] attributes() default {};

	public Type[] types() default {};

	public EntityProperty[] properties() default {};

	public EntityIndex[] indexes() default {};
}
