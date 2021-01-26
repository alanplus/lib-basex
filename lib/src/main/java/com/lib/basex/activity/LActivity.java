package com.lib.basex.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.lib.basex.BR;
import com.lib.basex.R;
import com.lib.basex.utils.LActivityUtils;
import com.lib.basex.utils.LClassUtils;
import com.lib.basex.utils.LResourceUtils;
import com.lib.basex.utils.statusbar.LStatusBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Alan
 * 时 间：2020-09-09
 * 简 述：<功能简述>
 */
public abstract class LActivity<T extends LViewModel, D extends ViewDataBinding> extends AppCompatActivity {

    protected T t;
    protected D d;

    protected static IActivity iActivity;

    public static List<Activity> activityList = new ArrayList<>();
    public boolean isTop;

    public static void register(IActivity iActivity) {
        LActivity.iActivity = iActivity;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityList.add(this);
        initContentView();
        onCreate();
        if (configEvenBus()) {
            EventBus.getDefault().register(this);
        }
    }

    protected void onCreate() {
        IActivity iActivity = getIActivity();
        if (null != iActivity) {
            iActivity.onCreate(this);
        }


        initStatusBar();
        t = (T) createViewModel();
        t.start.observe(this, this::startVmActivity);

        t.isFinish.observe(this, aBoolean -> {
            if (aBoolean) {
                finish();
            }
        });

        //所有布局中dababinding对象变量名称都是vm
        d.setVariable(BR.vm, t);
        d.executePendingBindings();//立即更新UI
        getLifecycle().addObserver(t);
        initView();
    }

    protected void initContentView() {
        d = DataBindingUtil.setContentView(this, getContentId());
    }

    protected T createViewModel() {
        return (T) createViewModel(LClassUtils.getTClassObject(this));
    }

    protected T createViewModel(Class<T> clazz) {
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
        isTop = true;
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
        isTop = false;
        IActivity iActivity = getIActivity();
        if (null != iActivity) {
            iActivity.onStop(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityList.remove(this);
        if (configEvenBus()) {
            EventBus.getDefault().unregister(this);
        }
        IActivity iActivity = getIActivity();
        if (null != iActivity) {
            iActivity.onDestroy(this);
        }
    }

    protected boolean configEvenBus() {
        return false;
    }

    public void startVmActivity(Intent intent) {
        startActivity(intent);
    }
}
