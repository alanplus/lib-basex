package com.lib.basex.widget.statelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.lib.basex.widget.statelayout.api.IStateView;


/**
 * Created by Mouse on 2018/9/21.
 */

public class StateFrameLayout extends FrameLayout implements IStateView {

    protected StateHelper stateHelper;

    public StateFrameLayout(Context context) {
        this(context, null);
    }

    public StateFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        stateHelper = new StateHelper(this);
    }

    @Override
    public void showLoadingState(String text) {
        stateHelper.showLoadingState(text);
    }

    @Override
    public void showFailureState(String text, boolean isRetry) {
        stateHelper.showFailureState(text, isRetry);
    }

    @Override
    public void showFailureState(int code, String text, boolean isRetry) {
        stateHelper.showFailureState(code, text, isRetry);
    }

    @Override
    public void showSuccessState() {
        stateHelper.showSuccessState();
    }

    @Override
    public TextView getRetryView() {
        return stateHelper.getRetryView();
    }
}
