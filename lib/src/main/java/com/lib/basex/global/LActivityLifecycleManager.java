package com.lib.basex.global;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.lib.basex.utils.Logger;

/**
 * Created by Mouse on 2018/1/8.
 */

public class LActivityLifecycleManager implements Application.ActivityLifecycleCallbacks {

    private int activityCount;
    private OnActivityListener onActivityListener;


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Logger.d(activity.getClass().getName());
        if (null != onActivityListener) {
            onActivityListener.onForegroundListener(activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (activityCount == 0) {
            //app回到前台
            if (null != onActivityListener) {
                onActivityListener.onForegroundListener(activity);
            }
        }
        activityCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount--;
        if (activityCount == 0) {
            if (null != onActivityListener) {
                onActivityListener.onBackgroundListener();
            }
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    public void setOnActivityListener(OnActivityListener onActivityListener) {
        this.onActivityListener = onActivityListener;
    }
}
