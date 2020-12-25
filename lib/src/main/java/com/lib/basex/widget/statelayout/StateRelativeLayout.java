package com.lib.basex.widget.statelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lib.basex.widget.statelayout.api.IFailureView;
import com.lib.basex.widget.statelayout.api.ILoadingView;
import com.lib.basex.widget.statelayout.api.IStateView;


/**
 * Created by Mouse on 2018/9/21.
 */

public class StateRelativeLayout extends RelativeLayout implements IStateView {

    protected StateHelper stateHelper;

    public StateRelativeLayout(Context context) {
        this(context, null);
    }

    public StateRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    protected void init() {
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
