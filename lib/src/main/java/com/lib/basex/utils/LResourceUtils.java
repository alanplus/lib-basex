package com.lib.basex.utils;

import android.content.Context;
import android.util.TypedValue;


/**
 * @author Alan
 * 时 间：2020-09-10
 * 简 述：<功能简述>
 */
public class LResourceUtils {

    public static boolean getAttrBool(Context context, int id) {
        TypedValue typedValue = new TypedValue();
        Logger.d(typedValue.data);
        context.getTheme().resolveAttribute(id, typedValue, true);
        return typedValue.data == -1;
    }

    public static int getAttrColor(Context context, int id) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(id, typedValue, true);
        return typedValue.data;
    }
}
