package com.lib.basex.activity;

import android.view.View;

import androidx.annotation.IntDef;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * @author Alan
 * 时 间：2020-09-10
 * 简 述：<功能简述>
 */
public class LStateViewModel extends LViewModel implements LifecycleObserver, View.OnClickListener {

    public static final int STATE_LOADING = 0;
    public static final int STATE_SUCCESS = 1;
    public static final int STATE_FAILURE = 2;

    public String failureText = "加载失败，请重试";
    public boolean isRetry = true;

    @Override
    public void onClick(View view) {

    }

    @IntDef({STATE_LOADING, STATE_SUCCESS, STATE_FAILURE})
    @interface State {
    }

    public MutableLiveData<Integer> state = new MutableLiveData<>(STATE_SUCCESS);

    public void showLoadingState() {
        state.setValue(STATE_LOADING);
    }

    public void showSuccessState() {
        state.setValue(STATE_SUCCESS);
    }

    public void showFailureState() {
        state.setValue(STATE_FAILURE);
    }

    public String getLoadingText() {
        return "正在加载...";
    }

    public String getFailureText() {
        return failureText;
    }

    public boolean isRetry() {
        return isRetry;
    }

    public View.OnClickListener getRetryOnClickListener() {
        return this;
    }

}
