package com.lib.basex.utils;

import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author Alan
 * 时 间：2020/9/16
 * 简 述：<功能简述>
 */
public class LViewUtils {

    public static int getSpecialWidth(int height, int w, int h) {
        return w * height / h;
    }

    public static int getSpecialHeight(int width, int w, int h) {
        return width * h / w;
    }

    public static void updateLayoutParam(int width, int w, int h, ViewGroup.LayoutParams lp) {
        lp.height = getSpecialHeight(width, w, h);
    }
}
