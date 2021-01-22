package com.lib.basex.thread;

/**
 * @author Alan
 * 时 间：2020-08-13
 * 简 述：<功能简述>
 */
public interface IThreadService<T> {

    /**
     * 在线程中 运行
     *
     * @return
     */
    T runOnThread();

    /**
     * 在住县城中 运行
     *
     * @param t
     * @param e
     */
    void runOnMainThread(T t, Throwable e);
}
