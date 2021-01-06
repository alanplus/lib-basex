package com.lib.basex.widget.baselayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

/**
 * @author Alan
 * 时 间：2020/9/17
 * 简 述：<功能简述>
 */
public abstract class LRelativeLayout<T, D extends ViewDataBinding> extends RelativeLayout {

    protected T t;
    protected D d;

    public LRelativeLayout(Context context) {
        this(context, null);
    }

    public LRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    protected void initView(@Nullable AttributeSet attrs) {
        d = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getContentId(), this, true);
    }

    public void setData(T t) {
        this.t = t;
    }

    public abstract int getContentId();

    @Override
    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if(null!=d){
            d.getRoot().setVisibility(visibility);
        }
    }
}
