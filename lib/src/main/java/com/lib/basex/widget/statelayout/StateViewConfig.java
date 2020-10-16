package com.lib.basex.widget.statelayout;

import android.content.Context;

import com.lib.basex.widget.statelayout.api.IFailureView;
import com.lib.basex.widget.statelayout.api.ILoadingView;
import com.lib.basex.widget.statelayout.view.LFailureStateView;
import com.lib.basex.widget.statelayout.view.LLoadingStateView;

/**
 * @author Alan
 * 时 间：2020-05-12
 * 简 述：<功能简述>
 */
public class StateViewConfig {

    public static StateViewConfig mStateViewConfig;

    public static void register(StateViewConfig stateViewConfig) {
        mStateViewConfig = stateViewConfig;
    }

    public static StateViewConfig getInstance() {
        if (null == mStateViewConfig) {
            mStateViewConfig = new StateViewConfig();
        }
        return mStateViewConfig;
    }

    public ILoadingView getLoadingView(Context context) {
        return new LLoadingStateView(context);
    }

    public IFailureView getFailureView(Context context) {
        return new LFailureStateView(context);
    }

}
