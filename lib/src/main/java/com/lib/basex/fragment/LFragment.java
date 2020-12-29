package com.lib.basex.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.lib.basex.BR;
import com.lib.basex.activity.LViewModel;
import com.lib.basex.utils.LActivityUtils;
import com.lib.basex.utils.LClassUtils;

/**
 * @author Alan
 * 时 间：2020/9/15
 * 简 述：<功能简述>
 */
public abstract class LFragment<VM extends LViewModel, D extends ViewDataBinding> extends Fragment {

    protected VM vm;
    protected D d;

    protected Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vm = (VM) new ViewModelProvider(requireActivity()).get(LClassUtils.getTClassObject(this));
        initContentView(inflater, container);
        initView(savedInstanceState);
        return d.getRoot();
    }


    protected void initContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        d = DataBindingUtil.inflate(inflater, getContentId(), container, false);
    }

    public void initView(@Nullable Bundle savedInstanceState) {
        d.setVariable(BR.vm, vm);
    }

    public abstract int getContentId();

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    public void startActivity(Class<? extends Activity> clazz) {
        LActivityUtils.startActivity(activity, clazz);
    }
}
