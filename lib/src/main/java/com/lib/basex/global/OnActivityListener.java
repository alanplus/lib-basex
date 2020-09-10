package com.lib.basex.global;

import android.app.Activity;

/**
 * Created by Mouse on 2018/1/8.
 */

public interface OnActivityListener {

    void onForegroundListener(Activity activity);
    void onBackgroundListener();
}
