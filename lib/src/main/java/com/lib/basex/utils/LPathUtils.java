package com.lib.basex.utils;

import com.lib.basex.LApplication;

import java.util.Objects;

/**
 * @author Alan
 * 时 间：2020/10/14
 * 简 述：<功能简述>
 */
public class LPathUtils {

    /**
     * 获取APP 私有目录
     *
     * @return
     */
    public static String getDir() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return LApplication.app.getDataDir().getAbsolutePath();
        } else {
            return "/data/data/" + LApplication.app.getPackageName();
        }
    }

    /**
     * 获取 外部存储目录 不需要权限
     *
     * @return
     */
    public static String getExternalCache() {
        return Objects.requireNonNull(LApplication.app.getExternalCacheDir()).getAbsolutePath();
    }
}
