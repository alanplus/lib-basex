package com.lib.basex.thread;

import android.annotation.SuppressLint;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.lib.basex.utils.LUtils;
import com.lib.basex.utils.Logger;

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
    @SuppressLint("CheckResult")
    public static void runOnMainThread(@NonNull Runnable runnable) {
        Observable.just(1).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> runnable.run());
    }

    @SuppressLint("CheckResult")
    public static void runDelay(Runnable runnable, long time) {
        Observable.timer(time, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(aLong -> runnable.run());
    }
}
