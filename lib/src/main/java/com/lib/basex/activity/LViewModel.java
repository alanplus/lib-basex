package com.lib.basex.activity;

import android.content.Intent;
import android.text.TextUtils;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lib.basex.LApplication;
import com.lib.basex.utils.LToastManager;
import com.lib.basex.utils.LUtils;

/**
 * @author Alan
 * 时 间：2020-09-10
 * 简 述：<功能简述>
 */
public class LViewModel extends ViewModel implements LifecycleObserver {

    public MutableLiveData<Boolean> isFinish = new MutableLiveData<>();
    public MutableLiveData<Intent> start = new MutableLiveData<>();

    public boolean isNetworkAvailable() {
        return LUtils.isNetworkAvailable(LApplication.app);
    }

    public boolean noNet() {
        return !isNetworkAvailable();
    }

    public void showMessage(String message) {
        if (!TextUtils.isEmpty(message)) {
            LToastManager.getInstance().showToast(LApplication.app, message);
        }
    }

    public void finish() {
        isFinish.setValue(true);
    }
}
