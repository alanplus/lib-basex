package com.lib.basex.widget.statelayout;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lib.basex.widget.statelayout.api.IFailureView;
import com.lib.basex.widget.statelayout.api.ILoadingView;
import com.lib.basex.widget.statelayout.api.IStateView;


/**
 * @author Alan
 * 时 间：2019-11-21
 * 简 述：<功能简述>
 */
public class StateHelper implements IStateView {

    private ViewGroup viewGroup;

    protected ILoadingView iLoadingView;
    protected IFailureView iFailureView;

    public StateHelper(@NonNull ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }


    @Override
    public void showLoadingState(String text) {
        reset();
        viewGroup.addView(generateLoadingView(text));
        ViewGroup.LayoutParams layoutParams = iLoadingView.getView().getLayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) layoutParams).gravity = Gravity.CENTER;
        }
        iLoadingView.start();
    }

    @Override
    public void showFailureState(String text, boolean isRetry) {
        reset();
        viewGroup.addView(generateFailureView());
        iFailureView.getView().getLayoutParams().width = -1;
        iFailureView.getView().getLayoutParams().height = -1;
        iFailureView.setText(text);
        iFailureView.setVisible(isRetry);
    }

    @Override
    public void showFailureState(int code, String text, boolean isRetry) {
        showFailureState(text, isRetry);
        iFailureView.setCode(code);
    }

    @Override
    public void showSuccessState() {
        reset();
    }

    @Override
    public TextView getRetryView() {
        return null == iFailureView ? null : iFailureView.getRetryTextView();
    }

    public void reset() {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View v = viewGroup.getChildAt(i);
            if ((null != iLoadingView && iLoadingView.getView() == v) || (null != iFailureView && iFailureView.getView() == v)) {
                viewGroup.removeView(v);
            }
        }
    }

    protected View generateLoadingView(String text) {
        if (null == iLoadingView) {
            iLoadingView = StateViewConfig.getInstance().getLoadingView(viewGroup.getContext());
        }
        iLoadingView.setText(text);
        return (View) iLoadingView;
    }

    protected View generateFailureView() {
        if (null == iFailureView) {
            iFailureView = StateViewConfig.getInstance().getFailureView(viewGroup.getContext());
        }
        return iFailureView.getView();
    }

}
