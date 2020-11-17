package com.lib.basex.activity;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.ViewModel;

import com.lib.basex.LApplication;
import com.lib.basex.utils.LUtils;

/**
 * @author Alan
 * 时 间：2020-09-10
 * 简 述：<功能简述>
 */
public class LViewModel extends ViewModel implements LifecycleObserver {


    public boolean isNetworkAvailable() {
        return LUtils.isNetworkAvailable(LApplication.app);
    }

}
