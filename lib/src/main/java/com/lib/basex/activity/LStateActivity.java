package com.lib.basex.activity;

import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.lib.basex.R;
import com.lib.basex.databinding.LActivityStateBinding;
import com.lib.basex.dialog.loading.LoadingDialog;
import com.lib.basex.widget.statelayout.StateModel;
import com.lib.basex.widget.statelayout.api.IStateView;

/**
 * @author Alan
 * 时 间：2020/10/16
 * 简 述：<功能简述>
 */
public abstract class LStateActivity<T extends LStateViewModel, D extends ViewDataBinding> extends LActivity<T, D> {

    protected LActivityStateBinding binding;

    protected LoadingDialog loadingDialog;

    @Override
    protected void initContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.l_activity_state);
        d = DataBindingUtil.inflate(LayoutInflater.from(this), getContentId(), null, false);
        binding.stateLayout.addView(d.getRoot(), new RelativeLayout.LayoutParams(-1, -1));
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        t.state.observe(this, stateModel -> {
            switch (stateModel.state) {
                case LStateViewModel.STATE_LOADING:
                    getStateView().showLoadingState(stateModel.text);
                    break;
                case LStateViewModel.STATE_SUCCESS:
                    getStateView().showSuccessState();
                    break;
                case LStateViewModel.STATE_FAILURE:
                    if (stateModel.failureDrawable != 0) {
                        getStateView().setFailureImage(stateModel.failureDrawable);
                    }
                    getStateView().showFailureState(stateModel.code, stateModel.text, stateModel.isRetry);
                    getStateView().getRetryView().setOnClickListener(stateModel.onClickListener);
                    break;
            }
        });

        t.loadingInfo.observe(this, lLoadingDialogInfo -> {
            switch (lLoadingDialogInfo.state) {
                case 1:
                    showLoadingDialog(lLoadingDialogInfo);
                    break;
                case 2:
                case 3:
                    dismissSuccessLoadingDialog(lLoadingDialogInfo);
                    break;
                case 4:
                    dismissImmediately();
                    break;
            }
        });
    }

    public IStateView getStateView() {
        return binding.stateLayout;
    }

    public void setTitleBar(String title) {
        binding.titleBar.setTitle(title);
    }

    protected void showLoadingDialog(LLoadingDialogInfo lLoadingDialogInfo) {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(this);
        }
        loadingDialog.setText(lLoadingDialogInfo.showText);
        loadingDialog.show();
    }

    protected void dismissSuccessLoadingDialog(LLoadingDialogInfo lLoadingDialogInfo) {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            boolean isSuccess = lLoadingDialogInfo.state == 2;
            if (lLoadingDialogInfo.onFinishListener != null) {
                loadingDialog.dismiss(lLoadingDialogInfo.showText, isSuccess, lLoadingDialogInfo.onFinishListener);
            } else {
                loadingDialog.dismiss(lLoadingDialogInfo.showText, isSuccess, () -> onLoadingDismiss(isSuccess));
            }

        }
    }


    protected void onLoadingDismiss(boolean isSuccess) {
        dismissImmediately();
    }

    protected void dismissImmediately() {
        if (null != loadingDialog && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


}
