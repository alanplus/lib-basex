package com.lib.basex.widget.baselayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @author Alan
 * 时 间：2020/9/17
 * 简 述：<功能简述>
 */
public abstract class LLinearLayout<T> extends LinearLayout {

    protected T t;

    public LLinearLayout(Context context) {
        this(context, null);
    }

    public LLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    protected void initView(@Nullable AttributeSet attrs) {

    }

    public void setData(T t) {
        this.t = t;
    }
}
