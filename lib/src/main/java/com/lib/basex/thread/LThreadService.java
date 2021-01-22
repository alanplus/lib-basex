package com.lib.basex.thread;

import android.annotation.SuppressLint;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.lib.basex.utils.LUtils;
import com.lib.basex.utils.Logger;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Alan
 * 时 间：2020-08-13
 * 简 述：<功能简述>
 */
public class LThreadService {

    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("alan-pool-%d").build();

    private static ExecutorService customThreadPool = new ThreadPoolExecutor(10, Integer.MAX_VALUE, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    private static List<WeakReference<Disposable>> list = new ArrayList<>();

    /**
     * 主线程 与子线程 交互
     *
     * @param iThreadService
     * @param <T>
     */
    public static <T> void run(@NonNull IThreadService<T> iThreadService) {
        Observable.create((ObservableOnSubscribe<RxCache<T>>) emitter -> {
            T t = iThreadService.runOnThread();
            emitter.onNext(new RxCache<>(t));
        }).compose(upstream -> upstream.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()))
                .doOnNext(t -> iThreadService.runOnMainThread(t.t, null))
                .onErrorReturn(throwable -> {
                    iThreadService.runOnMainThread(null, throwable);
                    return new RxCache<>(null);
                }).subscribe();
    }

    /**
     * 在子线程工作
     * <p>
     * 线程资源必须通过线程池提供，不允许在应用中自行显式创建线程。
     * 说明：使用线程池的好处是减少在创建和销毁线程上所花的时间以及系统资源的开销，解决资源不足的问题。
     * 如果不使用线程池，有可能造成系统创建大量同类线程而导致消耗完内存或者“过度切换”的问题。
     *
     * @param runnable
     */
    public static void runOnThread(@NonNull Runnable runnable) {
        customThreadPool.execute(runnable);
    }

    /**
     * 在主线程工作
     *
     * @param runnable
     */
    public static void runOnMainThread(@NonNull Runnable runnable, boolean isSave) {
        Disposable subscribe = Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> runnable.run());
        if (isSave) {
            list.add(new WeakReference<>(subscribe));
        }
    }

    public static void runOnMainThread(@NonNull Runnable runnable) {
        runOnMainThread(runnable, false);
    }

    public static void runDelay(Runnable runnable, long time, boolean isSave) {
        Disposable subscribe = Observable.timer(time, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> runnable.run());
        if (isSave) {
            list.add(new WeakReference<>(subscribe));
        }
    }

    public static void runDelay(Runnable runnable, long time) {
        runDelay(runnable, time, false);
    }

    public static void clear() {
        for (WeakReference<Disposable> weakReference : list) {
            Disposable disposable = weakReference.get();
            if (null != disposable && !disposable.isDisposed()) {
                disposable.dispose();
            }
        }
        list.clear();
    }
}
