package com.lib.basex.widget.statelayout.api;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Mouse on 2018/10/22.
 */
public interface IFailureView {

    void setText(String text);
    void setCode(int  code);
    void setOnRetryClickListener(View.OnClickListener onRetryClickListener);
    void setVisible(boolean isShow);
    View getView();
    TextView getRetryTextView();

    ImageView getImageView();
}
