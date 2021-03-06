package com.lib.basex.activity;

import android.view.View;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lib.basex.dialog.loading.LLoadDialogView;
import com.lib.basex.widget.statelayout.StateModel;

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

    @Override
    public void onClick(View view) {

    }

    @IntDef({STATE_LOADING, STATE_SUCCESS, STATE_FAILURE})
    public @interface State {
    }

    public MutableLiveData<StateModel> state = new MutableLiveData<>();
    public MutableLiveData<LLoadingDialogInfo> loadingInfo = new MutableLiveData<>();

    public void showLoadingState() {
        showLoadingState("正在加载");
    }

    public void showLoadingState(String text) {
        StateModel stateModel = new StateModel(STATE_LOADING);
        stateModel.text = text;
        state.setValue(stateModel);
    }

    public void showFailureState(String text) {
        showFailureState(0, text, null, 0);
    }

    public void showFailureState(String text, View.OnClickListener onClickListener) {
        showFailureState(0, text, onClickListener, 0);
    }

    public void showFailureState(int code, String text, View.OnClickListener onClickListener, int drawableId) {
        StateModel stateModel = new StateModel(STATE_FAILURE);
        stateModel.code = code;
        stateModel.text = text;
        stateModel.isRetry = null != onClickListener;
        stateModel.onClickListener = onClickListener;
        stateModel.failureDrawable = drawableId;
        state.setValue(stateModel);
    }


    public void showSuccessState() {
        state.setValue(new StateModel(STATE_SUCCESS));
    }


    public String getLoadingText() {
        return "正在加载...";
    }

    public String getFailureText() {
        return failureText;
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

    public void showLoadingDialog() {
        showLoadingDialog("正在加载");
    }

    public void dismissSuccessDialog(String text) {
        dismissDialog(text, true, null);
    }

    public void dismissSuccessDialog() {
        dismissDialog("加载完成", true, null);
    }

    public void dismissFailureDialog(String text) {
        dismissDialog(text, false, null);
    }

    public void dismissFailureDialog() {
        dismissDialog("加载失败", false, null);
    }

    public void dismissDialog(String text, boolean isSuccess, LLoadDialogView.OnFinishListener onFinishListener) {
        LLoadingDialogInfo lLoadingDialogInfo = new LLoadingDialogInfo();
        lLoadingDialogInfo.state = isSuccess ? 2 : 3;
        lLoadingDialogInfo.showText = text;
        lLoadingDialogInfo.onFinishListener = onFinishListener;
        loadingInfo.setValue(lLoadingDialogInfo);
    }


    public void dismissImmediately() {
        LLoadingDialogInfo lLoadingDialogInfo = new LLoadingDialogInfo();
        lLoadingDialogInfo.state = 4;
        loadingInfo.setValue(lLoadingDialogInfo);
    }
}
