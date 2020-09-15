package com.lib.basex.utils;

import androidx.annotation.NonNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Alan
 * 时 间：2020-09-09
 * 简 述：<功能简述>
 */
public class LClassUtils {


    public static Class getTClassObject(@NonNull Object obj) {
        try {
            Class<?> aClass = obj.getClass();
            Type genericSuperclass = aClass.getGenericSuperclass();
            Type[] types = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            return (Class) types[0];
        } catch (Exception e) {
            Logger.error(e);

        }
        return null;
    }
}
