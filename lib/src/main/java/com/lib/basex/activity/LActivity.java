package com.lib.basex.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alan.lib.simple.activity.SimpleActivity;
import com.lib.basex.utils.LClassUtils;


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
        d = DataBindingUtil.setContentView(this, getContentId());
        t = (T) new ViewModelProvider(this).get(LClassUtils.getTClassObject(this));
    }

    public abstract int getContentId();
}
