package com.lib.basex.thread;

/**
 * @author Alan
 * 时 间：2020-08-13
 * 简 述：<功能简述>
 */
public interface IThreadService<T> {

    T runOnThread();

    void runOnMainThread(T t);
}
