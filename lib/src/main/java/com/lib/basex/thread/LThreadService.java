package com.lib.basex.thread;

import android.os.Handler;

import androidx.annotation.NonNull;

/**
 * @author Alan
 * 时 间：2020-08-13
 * 简 述：<功能简述>
 */
public class LThreadService {

    private static Handler handler;

    public static void register(Handler handler) {
        LThreadService.handler = handler;

    }

    /**
     * 主线程 与子线程 交互
     *
     * @param iThreadService
     * @param <T>
     */
    public static <T> void run(@NonNull IThreadService<T> iThreadService) {
        new Thread(() -> {
            T t = iThreadService.runOnThread();
            handler.post(() -> iThreadService.runOnMainThread(t));
        }).start();
    }

    /**
     * 在子线程工作
     *
     * @param runnable
     */
    public static void runOnThread(@NonNull Runnable runnable) {
        new Thread(runnable).start();
    }

    /**
     * 在主线程工作
     *
     * @param runnable
     */
    public static void runOnMainThread(@NonNull Runnable runnable) {
        handler.post(runnable);
    }

    public static void runDelay(Runnable runnable, long time) {
        handler.postDelayed(runnable, time);
    }
}
