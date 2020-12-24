package com.lib.basex.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.lib.basex.LApplication;

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

    public static void startActivity(Context context, String tagName) {
        Intent intent = getIntent(context, tagName);
        context.startActivity(intent);
    }

    public static Intent getIntent(Context context, String tagName) {
        Intent intent = new Intent();
        try {
            Class aClass = LApplication.app.getActivity(tagName);
            intent.setClass(context, aClass);
            return intent;
        } catch (Exception e) {
            Logger.error(e);
        }
        return intent;
    }
}
