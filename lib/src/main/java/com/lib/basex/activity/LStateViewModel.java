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


    public static final int DIALOG_LOADING_SHOW = 1;
    public static final int DIALOG_LOADING_HIDDEN = 2;

    public String failureText = "加载失败，请重试";
    public String NO_NET = "网络无效，请重试";
    public boolean isRetry = true;

    @Override
    public void onClick(View view) {

    }

    @IntDef({STATE_LOADING, STATE_SUCCESS, STATE_FAILURE})
    @interface State {
    }

    public MutableLiveData<Integer> state = new MutableLiveData<>(STATE_SUCCESS);
    public MutableLiveData<LLoadingDialogInfo> loadingInfo = new MutableLiveData<>();

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

    public void showLoadingDialog(String text) {
        LLoadingDialogInfo lLoadingDialogInfo = new LLoadingDialogInfo();
        lLoadingDialogInfo.state = 1;
        lLoadingDialogInfo.showText = text;
        loadingInfo.setValue(lLoadingDialogInfo);
    }

    protected void showLoadingDialog() {
        showLoadingDialog("正在加载");
    }

    protected void dismissSuccessDialog(String text) {
        dismissDialog(text, true);
    }

    protected void dismissSuccessDialog() {
        dismissDialog("加载完成", true);
    }

    protected void dismissFailureDialog(String text) {
        dismissDialog(text, false);
    }

    protected void dismissFailureDialog() {
        dismissDialog("加载失败", false);
    }

    protected void dismissDialog(String text, boolean isSuccess) {
        LLoadingDialogInfo lLoadingDialogInfo = new LLoadingDialogInfo();
        lLoadingDialogInfo.state = isSuccess ? 2 : 3;
        lLoadingDialogInfo.showText = text;
        loadingInfo.setValue(lLoadingDialogInfo);
    }


    protected void dismissImmediately() {
        LLoadingDialogInfo lLoadingDialogInfo = new LLoadingDialogInfo();
        lLoadingDialogInfo.state = 4;
        loadingInfo.setValue(lLoadingDialogInfo);
    }
}
