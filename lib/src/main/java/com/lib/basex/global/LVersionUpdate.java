package com.lib.basex.global;

import com.lib.basex.LApplication;
import com.lib.basex.utils.LSharedPreferences;
import com.lib.basex.utils.LUtils;

/**
 * @author Alan
 * 时 间：2020-09-10
 * 简 述：<功能简述>
 */
public abstract class LVersionUpdate {


    public void update() {
        int versionCode = LSharedPreferences.getInstance().getInt(LConstant.SP_VERSION_CODE, 0);
        int currentVersionCode = LUtils.getVersionCode(LApplication.app);
        if (versionCode < currentVersionCode) {
            if (onUpdate()) {
                LSharedPreferences.getInstance().setInt(LConstant.SP_VERSION_CODE, currentVersionCode);
            }
        }
    }

    public abstract boolean onUpdate();

}
