package com.lib.basex.widget.statelayout;

import android.view.View;

import com.lib.basex.activity.LStateViewModel;

/**
 * @author Alan
 * 时 间：2020/12/25
 * 简 述：<功能简述>
 */
public class StateModel {

    @LStateViewModel.State
    public int state;

    public String text;

    /**
     * 失败 信息
     */
    public int code;

    public boolean isRetry;
    public View.OnClickListener onClickListener;

    public StateModel(@LStateViewModel.State int state) {
        this.state = state;
    }
}
