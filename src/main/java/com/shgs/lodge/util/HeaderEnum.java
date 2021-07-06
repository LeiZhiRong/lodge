package com.shgs.lodge.util;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@Documented
public @interface HeaderEnum {
  String field() default "";

  String title() default "";

  int width() default 100;

  boolean sortable() default true;

  boolean hidden() default false;

  String editor() default "";

  int status() default 1;

  String formatter() default "";

}

