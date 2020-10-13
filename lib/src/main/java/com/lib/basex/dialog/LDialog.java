package com.lib.basex.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.lib.basex.R;

import java.util.Objects;

/**
 * @author Alan
 * 时 间：2020/10/12
 * 简 述：<功能简述>
 */
public abstract class LDialog<D extends ViewDataBinding> extends Dialog {

    protected D d;

    public LDialog(@NonNull Context context) {
        this(context, R.style.LAppTheme);
    }

    public LDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        d = (D) DataBindingUtil.inflate(LayoutInflater.from(getContext()), getContentRes(), null, false);
        setContentView(d.getRoot());
        setSize(getDialogWidth());
    }


    public abstract int getContentRes();

    public float getDialogWidth() {
        return 0.75f;
    }

    protected void setSize(double widthScale) {
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        int windowWidth = outMetrics.widthPixels;
        WindowManager.LayoutParams params = Objects.requireNonNull(getWindow()).getAttributes();
        params.width = (int) (windowWidth * widthScale); // 宽度设置为屏幕的一定比例大小
        //params.height = (int) (outMetrics.heightPixels * widthScale);
        getWindow().setAttributes(params);
    }
}
