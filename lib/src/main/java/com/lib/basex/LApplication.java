package com.lib.basex;

import android.content.Context;
import android.os.Handler;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.lib.basex.thread.LThreadService;

/**
 * @author Alan
 * 时 间：2020-09-08
 * 简 述：
 * 1. 提供全局应用实例
 * 2. 解决65535问题
 * 3.
 */
public class LApplication extends MultiDexApplication {

    public static LApplication app;
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
