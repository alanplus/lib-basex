package com.lib.basex.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewbinding.ViewBinding;


import com.lib.basex.BR;
import com.lib.basex.R;
import com.lib.basex.utils.LActivityUtils;
import com.lib.basex.utils.LClassUtils;
import com.lib.basex.utils.LResourceUtils;
import com.lib.basex.utils.statusbar.LStatusBar;


/**
 * @author Alan
 * 时 间：2020-09-09
 * 简 述：<功能简述>
 */
public abstract class LActivity<T extends LViewModel, D extends ViewDataBinding> extends AppCompatActivity {

    protected T t;
    protected D d;

    protected static IActivity iActivity;

    public static void register(IActivity iActivity) {
        LActivity.iActivity = iActivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView();
        onCreate();
    }

    protected void onCreate(){
        IActivity iActivity = getIActivity();
        if (null != iActivity) {
            iActivity.onCreate(this);
        }

        initStatusBar();
        t = (T) createViewModel();

        //所有布局中dababinding对象变量名称都是vm
        d.setVariable(BR.vm, t);
        d.executePendingBindings();//立即更新UI
        getLifecycle().addObserver(t);
        initView();
    }

    protected void initContentView(){
        d = DataBindingUtil.setContentView(this, getContentId());
    }

    protected ViewModel createViewModel() {
        return createViewModel(LClassUtils.getTClassObject(this));
    }

    protected ViewModel createViewModel(Class<? extends LViewModel> clazz) {
        return new ViewModelProvider(this).get(clazz);
    }

    protected void initStatusBar() {
        boolean isWhite = LResourceUtils.getAttrBool(this, R.attr.l_status_bar_text_is_white);
        int color = LResourceUtils.getAttrColor(this, R.attr.l_status_bar_bg_color);
        LStatusBar.getStatusBar().setStatusBarColor(this, color, isWhite);
    }

    public abstract int getContentId();

    public abstract void initView();

    public Activity getActivity() {
        return this;
    }

    public void startActivity(Class<? extends Activity> clazz) {
        LActivityUtils.startActivity(this, clazz);
    }

    public IActivity getIActivity() {
        return iActivity;
    }

    @Override
    protected void onStart() {
        super.onStart();
        IActivity iActivity = getIActivity();
        if (null != iActivity) {
            iActivity.onStart(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IActivity iActivity = getIActivity();
        if (null != iActivity) {
            iActivity.onResume(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        IActivity iActivity = getIActivity();
        if (null != iActivity) {
            iActivity.onPause(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        IActivity iActivity = getIActivity();
        if (null != iActivity) {
            iActivity.onStop(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IActivity iActivity = getIActivity();
        if (null != iActivity) {
            iActivity.onDestroy(this);
        }
    }
}
