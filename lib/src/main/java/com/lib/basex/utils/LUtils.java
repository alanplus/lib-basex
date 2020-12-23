package com.lib.basex.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import java.io.File;
import java.util.List;

/**
 * Created by Mouse on 2018/10/15.
 */
public class LUtils {

    /**
     * dp转px
     *
     * @param dpValue
     * @return
     */
    public static int dip2px(float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率 px(像素) 转 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕分辨
     *
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        DisplayMetrics metrics = getDisplayMetrics(context);
        return new int[]{metrics.widthPixels, metrics.heightPixels};
    }

    /**
     * 返回原尺寸的DisplayMetrics�?4.0默认会减掉�?�知栏部分，故要作处�?
     *
     * @param context
     * @return
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics metric = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metric);
        return metric;
    }

    public static String getMetaData(Context context, String key) {
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(key);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.error(e);
        }
        return null;
    }

    public static int getMetaDataInt(Context context, String key) {
        ApplicationInfo appInfo;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            return appInfo.metaData.getInt(key);
        } catch (PackageManager.NameNotFoundException e) {
            Logger.error(e);
        }
        return -1;
    }

    public static int getCurrentSdk() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * 判断当前网络是否是wifi
     */
    @SuppressLint("MissingPermission")
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    public static int getVersionCode(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getVersionName(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "0";
    }

    @SuppressLint("MissingPermission")
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static long getSystemLeftSpace() {
        File root = Environment.getDataDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        return sf.getAvailableBlocks() * blockSize;
    }

    public static boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }

    /**
     * 获取当前应用名称
     *
     * @param context
     * @return
     */
    public static String getApplicationName(Context context) {
        return getSomeApplicationName(context.getPackageName(), context);
    }

    /**
     * 通过包名获取应用名称
     *
     * @param pkg
     * @param context
     * @return
     */
    public static String getSomeApplicationName(String pkg, Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    pkg, 0);
            int labelRes = packageInfo.applicationInfo.labelRes;
            return context.getResources().getString(labelRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean listIsNull(List list) {
        return null == list || list.size() == 0;
    }

    public static String getDir(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return context.getDataDir().getAbsolutePath();
        } else {
            return "/data/data/" + context.getPackageName();
        }
    }

    public static <T extends ViewDataBinding> T getViewDataBinding(Context context, @LayoutRes int res) {
        return DataBindingUtil.inflate(LayoutInflater.from(context), res, null, false);
    }

    public static <T extends ViewDataBinding> T getViewDataBinding(Context context, @LayoutRes int res, ViewGroup viewGroup) {
        return DataBindingUtil.inflate(LayoutInflater.from(context), res, viewGroup, true);
    }

    public static int getTextWidth(Paint paint, String text) {
        return (int) paint.measureText(text);
    }

    public static int getTextHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil((double) (fm.descent - fm.top)) + 2;
    }

    public static float getTextYCenter(Paint paint, int height) {
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (float) height / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;
    }

    public static float getTextXCenter(Paint paint, int width, String text) {
        int textWidth = getTextWidth(paint, text);
        return (float) (width - textWidth) / 2;
    }

    public static void setSpannableTextColor(SpannableString string, int start, int len, int color) {
        string.setSpan(new ForegroundColorSpan(color), start, start + len, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    public static void setSpannableTextSize(SpannableString string, int start, int len, int size) {
        string.setSpan(new AbsoluteSizeSpan(size, true), start, start + len, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }


    public static void setSpannableTextBold(SpannableString string, int start, int len) {
        string.setSpan(new StyleSpan(Typeface.BOLD), start, start + len, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    public static void setSpannableTextItalic(SpannableString string, int start, int len) {
        string.setSpan(new StyleSpan(Typeface.ITALIC), start, start + len, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    public static void setSpannableTextItalicAndBold(SpannableString string, int start, int len) {
        string.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), start, start + len, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    public static boolean isEnglishChar(String s) {
        if (TextUtils.isEmpty(s)) {
            return false;
        }

        if (s.length() != 1) {
            return false;
        }
        char c = s.charAt(0);
        return isEnglishChar(c);
    }

    /**
     * ASCII码
     * A-Z 65-90 91 [ 92 \ 93 ] 94^ 95_96` a-z 97-122
     * 33-47 标点符号 0-9 48-64
     *
     * @param s
     * @return
     */
    public static boolean isEnglishChar(char s) {
        if ((s >= 65 && s <= 90) || (s >= 97 && s <= 122)) {
            return true;
        }
        return false;
    }

    public static <T> T newTClass(Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (Exception e) {
            Logger.error(e);
        }
        return t;
    }

}
