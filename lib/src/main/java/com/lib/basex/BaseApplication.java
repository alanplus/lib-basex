package com.lib.basex;

import android.content.Context;
import android.os.Handler;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.lib.basex.thread.LThreadService;

/**
 * @author Alan
 * 时 间：2020-09-08
 * 简 述：<功能简述>
 */
public class BaseApplication extends MultiDexApplication {

    public BaseApplication app;
    public Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        handler = new Handler();
        LThreadService.register(handler);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
