package com.lib.basex.annotation;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Alan
 * 时 间：2020/12/25
 * 简 述：<功能简述>
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface AppAnnotation {
    boolean neverCrash() default true;

    boolean isDebug() default false;

    String log() default "l_base_x";
}
