package com.lib.basex.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

/**
 * @author Alan
 * 时 间：2020/9/16
 * 简 述：<功能简述>
 */
public class LActivityUtils {

    public static void startActivity(Context context, Class<? extends Activity> clazz) {
        Intent intent = new Intent();
        intent.setClass(context, clazz);
        context.startActivity(intent);
    }
}