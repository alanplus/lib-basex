package com.lib.basex;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.king.thread.nevercrash.NeverCrash;
import com.lib.basex.global.LActivityLifecycleManager;
import com.lib.basex.global.OnActivityListener;
import com.lib.basex.utils.LClassUtils;
import com.lib.basex.utils.LUtils;
import com.lib.basex.utils.Logger;

import java.util.HashMap;
import java.util.List;

/**
 * @author Alan
 * 时 间：2020-09-08
 * 简 述：
 * 1. 提供全局应用实例
 * 2. 解决65535问题
 * 3.
 */
public abstract class LApplication extends MultiDexApplication implements NeverCrash.CrashHandler, OnActivityListener {

    public static LApplication app;
    public Handler handler;

    public boolean mInitSuccess;

    protected HashMap<String, Class> mClassList;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        handler = new Handler();
        mInitSuccess = true;
        initClassList();
        NeverCrash.init(this);
        registerActivityManager();
        try {
            init();
        } catch (Throwable e) {
            uncaughtException(null, e);
            mInitSuccess = false;
        }
    }

    /**
     * 注册
     */
    protected void registerActivityManager() {
        LActivityLifecycleManager lActivityLifecycleManager = new LActivityLifecycleManager();
        lActivityLifecycleManager.setOnActivityListener(this);
        registerActivityLifecycleCallbacks(lActivityLifecycleManager);
    }

    public abstract void init();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * 是否打印日志 也可用于其他地方
     *
     * @return
     */
    public boolean isDebug() {
        return true;
    }

    /**
     * 打印日志的全局logTag
     *
     * @return
     */
    public String getLogTag() {
        return "l_base_x";
    }

    /**
     * 捕获到异常
     *
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger.error(e);
    }

    /**
     * 从后台拉到 前台
     *
     * @param activity
     */
    @Override
    public void onForegroundListener(Activity activity) {

    }

    /**
     * 拉到后台
     */
    @Override
    public void onBackgroundListener() {

    }

    /**
     * 创建页面
     *
     * @param activity
     */
    @Override
    public void onCreateListener(Activity activity) {

    }

    public Class getActivity(String tag) {
        if (null == mClassList) {
            initClassList();
        }
        return mClassList.get(tag);
    }

    private void initClassList() {
        if (null == mClassList) {
            mClassList = new HashMap<>();
            String data = LUtils.getMetaData(this, "activity_package");
            if (!TextUtils.isEmpty(data)) {
                List<String> list = LClassUtils.getAndroidClasses(this, data);
                for (String s : list) {
                    try {
                        Class<?> aClass = Class.forName(s);
                        com.lib.basex.annotation.Activity annotation = aClass.getAnnotation(com.lib.basex.annotation.Activity.class);
                        if (null != annotation) {
                            String value = annotation.value();
                            mClassList.put(value, aClass);
                        }
                    } catch (Exception e) {
                        Logger.error(e);
                    }
                }
            }
        }
    }
}
