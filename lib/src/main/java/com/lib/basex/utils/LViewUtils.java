package com.lib.basex.utils;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

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

    /**
     * 设置 TextView的图片
     *
     * @param textView
     * @param leftDrawable
     * @param topDrawable
     * @param rightDrawable
     * @param bottomDrawable
     */
    public static void setTextDrawable(@NonNull TextView textView, Drawable leftDrawable, Drawable topDrawable, Drawable rightDrawable, Drawable bottomDrawable) {
        if (leftDrawable != null) {
            leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
        }

        if (topDrawable != null) {
            topDrawable.setBounds(0, 0, topDrawable.getMinimumWidth(), topDrawable.getMinimumHeight());
        }

        if (rightDrawable != null) {
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
        }

        if (bottomDrawable != null) {
            bottomDrawable.setBounds(0, 0, bottomDrawable.getMinimumWidth(), bottomDrawable.getMinimumHeight());
        }
        textView.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable);
    }

    public static void setBackground(Drawable drawable, @NonNull View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}
