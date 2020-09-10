package com.lib.basex.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lib.basex.R;

/**
 * @author Alan
 * 时 间：2020-08-13
 * 简 述：<功能简述>
 */
public class LToastManager {

    private Toast mToast;
    private static LToastManager manager;

    private LToastManager() {
    }

    public static LToastManager getInstance() {
        if (null == manager) {
            manager = new LToastManager();
        }

        return manager;
    }

    public Toast getToast(Context context) {
        if (null != this.mToast) {
            this.mToast.cancel();
        }

        this.mToast = Toast.makeText(context.getApplicationContext(), "", Toast.LENGTH_LONG);
        return this.mToast;
    }

    public void showToast(Context context, String str) {
        try {
            this.mToast = this.getToast(context.getApplicationContext());
            this.mToast.setText(str);
            this.mToast.show();
        } catch (Exception var4) {
        } catch (Error var5) {
        }

    }

    public void cancelToast() {
        if (null != this.mToast) {
            this.mToast.cancel();
        }

        this.mToast = null;
    }

    public void showIosToast(Context context, String string) {
        Toast toast = new Toast(context);
        View layout = View.inflate(context, R.layout.lx_view_toast, null);
        toast.setView(layout);
        TextView textView = layout.findViewById(R.id.toast_text);
        textView.setText(string);
        toast.setGravity(7, 0, 0);
        toast.show();
    }
}
