package com.lib.basex.widget.statelayout.api;

import android.view.View;

/**
 * Created by Mouse on 2018/10/22.
 */
public interface ILoadingView {

    void setText(String text);
    void start();
    View getView();
}
