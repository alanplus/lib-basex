package com.lib.basex.widget.statelayout.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.lib.basex.R;
import com.lib.basex.databinding.LStateFailureBinding;
import com.lib.basex.widget.baselayout.LLinearLayout;
import com.lib.basex.widget.statelayout.api.IFailureView;


/**
 * Created by Mouse on 2018/10/22.
 */
public class LFailureStateView extends LLinearLayout<Object, LStateFailureBinding> implements IFailureView {

    public LFailureStateView(Context context) {
        super(context);
    }

    public LFailureStateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public int getContentId() {
        return R.layout.l_state_failure;
    }

    @Override
    public void setText(String text) {
        d.viewStateText.setText(text);
    }

    @Override
    public void setCode(int code) {

    }

    @Override
    public void setOnRetryClickListener(OnClickListener onRetryClickListener) {
        d.viewStateText.setOnClickListener(onRetryClickListener);
    }

    @Override
    public void setVisible(boolean isShow) {
        d.retry.setVisibility(isShow ? View.VISIBLE : View.GONE);
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public TextView getRetryTextView() {
        return d.retry;
    }

    @Override
    public ImageView getImageView() {
        return d.viewStateImg;
    }
}
