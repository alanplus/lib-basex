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
import com.lib.basex.widget.statelayout.api.ILoadingView;


/**
 * Created by Mouse on 2018/10/22.
 */
public class LLoadingStateView extends LinearLayout implements ILoadingView {

    private SmileyLoadingView smileyLoadingView;
    private TextView textLoading;

    public LLoadingStateView(Context context) {
        super(context);
        initLayout();
    }

    public LLoadingStateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initLayout();
    }

    private void initLayout() {
        LayoutInflater.from(getContext()).inflate(R.layout.lib_state_loading, this);
        smileyLoadingView = findViewById(R.id.loading_view);
        textLoading = findViewById(R.id.text_loading);
        setBackgroundColor(Color.WHITE);
    }

    @Override
    public void setText(String text) {
        textLoading.setText(text);
    }

    @Override
    public void start() {
        smileyLoadingView.start();
    }

    @Override
    public View getView() {
        return this;
    }
}
