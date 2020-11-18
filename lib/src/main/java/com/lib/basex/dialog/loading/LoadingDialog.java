package com.lib.basex.dialog.loading;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.lib.basex.R;
import com.lib.basex.databinding.LDialogLoadingBinding;
import com.lib.basex.dialog.LDialog;


/**
 * 数据操作状态对话框
 * Created by Mouse on 2017/10/28.
 */

public class LoadingDialog extends LDialog<LDialogLoadingBinding> implements LLoadDialogView.OnFinishListener {

    private String text;

    public LoadingDialog(@NonNull Context context) {
        super(context);
    }


    @Override
    public void show() {
        super.show();
        d.loadingImg.showLoading();
    }

    @Override
    public int getContentRes() {
        return R.layout.l_dialog_loading;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        TextView textView = d.text;
        if (!TextUtils.isEmpty(this.text)) {
            textView.setText(this.text);
        }
        d.loadingImg.setOnFinishListener(this);
    }


    public LoadingDialog setText(String text) {
        this.text = text;
        if (null != d) {
            d.text.setText(text);
        }
        return this;
    }

    public void dismiss(String text, boolean success) {
        if (isShowing()) {
            d.text.setText(text);
            if (success) {
                d.loadingImg.showSuccess();
            } else {
                d.loadingImg.showError();
            }
        }
    }

    public void dismiss(String text, boolean success, LLoadDialogView.OnFinishListener listener) {
        d.loadingImg.setOnFinishListener(listener);
        dismiss(text, success);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        d.loadingImg.destroy();
    }

    @Override
    public void viewDismiss() {
        d.loadingImg.postDelayed(() -> {
            d.text.setText("正在加载");
            dismiss();
        }, 800);
    }

}
