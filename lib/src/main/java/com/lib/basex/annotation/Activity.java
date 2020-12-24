package com.lib.basex.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alan
 * 时 间：2020/12/24
 * 简 述：<功能简述>
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
//@Target({ElementType.ANNOTATION_TYPE})
public @interface Activity {

    String value();
}
