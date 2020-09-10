package com.lib.basex;

import android.content.Context;
import android.os.Handler;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.king.thread.nevercrash.NeverCrash;
import com.lib.basex.thread.LThreadService;
import com.lib.basex.utils.Logger;

/**
 * @author Alan
 * 时 间：2020-09-08
 * 简 述：
 * 1. 提供全局应用实例
 * 2. 解决65535问题
 * 3.
 */
public abstract class LApplication extends MultiDexApplication implements NeverCrash.CrashHandler {

    public static LApplication app;
    public Handler handler;

    public boolean mInitSuccess;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        handler = new Handler();
        mInitSuccess = true;
        NeverCrash.init(this);
        try {
            init();
        } catch (Throwable e) {
            uncaughtException(null, e);
            mInitSuccess = false;
        }
    }

    public abstract void init();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public boolean isDebug() {
        return true;
    }

    public String getLogTag() {
        return "l_base_x";
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Logger.error(e);
    }
}
