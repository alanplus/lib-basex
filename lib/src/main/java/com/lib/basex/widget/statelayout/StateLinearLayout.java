package com.lib.basex.widget.statelayout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.lib.basex.widget.statelayout.api.IStateView;


/**
 * Created by Mouse on 2018/9/20.
 */

public class StateLinearLayout extends LinearLayout implements IStateView {

    protected StateHelper stateHelper;

    public StateLinearLayout(@NonNull Context context) {
        this(context, null);
    }

    public StateLinearLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
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
    public void showSuccessState() {
        stateHelper.showSuccessState();
    }

    @Override
    public TextView getRetryView() {
        return stateHelper.getRetryView();
    }


}
