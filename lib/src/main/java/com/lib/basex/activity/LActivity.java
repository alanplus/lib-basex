package com.lib.basex.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;

import com.alan.lib.simple.activity.SimpleActivity;
import com.lib.basex.BR;
import com.lib.basex.R;
import com.lib.basex.utils.LClassUtils;
import com.lib.basex.utils.LResourceUtils;
import com.lib.basex.utils.statusbar.LStatusBar;


/**
 * @author Alan
 * 时 间：2020-09-09
 * 简 述：<功能简述>
 */
public abstract class LActivity<T extends LViewModel, D extends ViewDataBinding> extends SimpleActivity {

    protected T t;
    protected D d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentId());
        initStatusBar();
        d = DataBindingUtil.setContentView(this, getContentId());
        t = (T) new ViewModelProvider(this).get(LClassUtils.getTClassObject(this));

        //所有布局中dababinding对象变量名称都是vm
        d.setVariable(BR.vm, t);
        d.executePendingBindings();//立即更新UI
        getLifecycle().addObserver(t);
    }

    protected void initStatusBar() {
        boolean isWhite = LResourceUtils.getAttrBool(this, R.attr.l_status_bar_text_is_white);
        int color = LResourceUtils.getAttrColor(this, R.attr.l_status_bar_bg_color);
        LStatusBar.getStatusBar().setStatusBarColor(this, color, isWhite);
    }

    public abstract int getContentId();
}
