package com.lib.basex.widget.baselayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author Alan
 * 时 间：2020/9/17
 * 简 述：<功能简述>
 */
public abstract class LFrameLayout<T, D extends ViewDataBinding> extends FrameLayout {

    protected T t;
    protected D d;

    public LFrameLayout(Context context) {
        this(context, null);
    }

    public LFrameLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    protected void initView(@Nullable AttributeSet attrs) {
        d = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getContentId(), this, true);
    }

    public void setData(T t) {
        this.t = t;
    }

    protected abstract int getContentId();
}
