package com.lib.basex.activity;

import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.lib.basex.R;
import com.lib.basex.databinding.LActivityStateBinding;

/**
 * @author Alan
 * 时 间：2020/10/16
 * 简 述：<功能简述>
 */
public abstract class LStateActivity<T extends LViewModel, D extends ViewDataBinding> extends LActivity<T, D> {

    protected LActivityStateBinding binding;

    @Override
    protected void initContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.l_activity_state);
        d = DataBindingUtil.inflate(LayoutInflater.from(this), getContentId(), null, false);
        binding.stateLayout.addView(d.getRoot(), new RelativeLayout.LayoutParams(-1, -1));
    }

    public void setTitleBar(String title) {
        binding.titleBar.setTitle(title);
    }
}
