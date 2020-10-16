package com.lib.basex.widget.statelayout.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lib.basex.R;
import com.lib.basex.widget.statelayout.api.IFailureView;


/**
 * Created by Mouse on 2018/10/22.
 */
public class LFailureStateView extends LinearLayout implements IFailureView {

    private TextView textFailure, textRetry;

    public LFailureStateView(Context context) {
        super(context);
        initLayout();
    }

    public LFailureStateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    private void initLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.l_state_failure, this);
        textFailure = findViewById(R.id.view_state_text);
        textRetry = findViewById(R.id.retry);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public void setText(String text) {
        textFailure.setText(text);
    }

    @Override
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        textRetry.setOnClickListener(onRetryClickListener);
    }

    @Override
    public void setVisible(boolean isShow) {
        textRetry.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public TextView getRetryTextView() {
        return textRetry;
    }
}
