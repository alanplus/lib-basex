package com.lib.basex.widget.statelayout.api;

import android.view.View;

/**
 * @author Alan
 * 时 间：2019-11-21
 * 简 述：<功能简述>
 */
public interface IBaseStateView {

    void showLoadingState();

    void showLoadingState(String text);

    void showFailureState(String text, boolean isRetry);

    void showSuccessState();

    void setRetryListener(View.OnClickListener onClickListener);
}
