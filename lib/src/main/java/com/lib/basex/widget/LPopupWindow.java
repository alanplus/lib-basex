package com.lib.basex.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.databinding.ViewDataBinding;

import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;
import com.lib.basex.utils.LUtils;

/**
 * @author Alan
 * 时 间：2021/3/2
 * 简 述：<功能简述>
 */
public abstract class LPopupWindow<T extends ViewDataBinding> extends PopupWindow {

    protected T binding;

    public LPopupWindow(Context context) {

        binding = LUtils.getViewDataBinding(context, getContentId());
        addShadow(context);
        binding.getRoot().setBackgroundColor(Color.WHITE);
        setContentView(binding.getRoot());
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setOutsideTouchable(true);
        setFocusable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    /**
     * 返回布局ID
     *
     * @return 返回布局ID
     */
    protected abstract int getContentId();

    protected void addShadow(Context context) {
        new CrazyShadow.Builder().setContext(context)
                .setDirection(CrazyShadowDirection.ALL)
//                .setCorner(LUtils.dip2px(5))
                .setBaseShadowColor(Color.parseColor("#D7D7D7"))
                .setShadowRadius(LUtils.dip2px(5))
                .setBackground(Color.WHITE)
                .setImpl(CrazyShadow.IMPL_WRAP)
                .action(binding.getRoot());
    }
}
