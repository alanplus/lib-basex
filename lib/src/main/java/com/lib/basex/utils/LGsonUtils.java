package com.lib.basex.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Alan
 * 时 间：2020/10/20
 * 简 述：<功能简述>
 */
public class LGsonUtils {

    private static Gson mGson = new Gson();

//    static {
//        GsonBuilder gsonBuilder = new GsonBuilder();
//        gsonBuilder.excludeFieldsWithoutExposeAnnotation();
//        mGson = gsonBuilder.create();
//    }

    /**
     * 将json字符串转化成实体对象
     *
     * @param json
     * @param classOfT
     * @return
     */
    public static <T> T stringToObject(String json, Class<T> classOfT) {
        return mGson.fromJson(json, classOfT);
    }

    /**
     * 将对象准换为json字符串 或者 把list 转化成json
     *
     * @param object
     * @param <T>
     * @return
     */
    public static <T> String objectToString(T object) {
        return mGson.toJson(object);
    }

    /**
     * 把json 字符串转化成list
     *
     * @param json
     * @param cls
     * @param <T>
     * @return
     */
    public static <T> List<T> stringToList(String json, Class<T> cls) {
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for (final JsonElement elem : array) {
            list.add(mGson.fromJson(elem, cls));
        }
        return list;
    }

    public static <T> HashMap<String, T> stringTHashMap(String string, Class<T> clazz) {
        Type type = new TypeToken<HashMap<String, T>>() {
        }.getType();
        return mGson.fromJson(string, type);
    }

    public static <T> String hashMapToString(HashMap<String, T> map) {
        Type type = new TypeToken<HashMap<String, T>>() {
        }.getType();
        return mGson.toJson(map, type);
    }
}
