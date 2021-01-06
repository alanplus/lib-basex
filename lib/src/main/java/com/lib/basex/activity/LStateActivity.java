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
                    binding.stateLayout.showLoadingState(stateModel.text);
                    break;
                case LStateViewModel.STATE_SUCCESS:
                    binding.stateLayout.showSuccessState();
                    break;
                case LStateViewModel.STATE_FAILURE:
                    if (stateModel.failureDrawable != 0) {
                        binding.stateLayout.setFailureImage(stateModel.failureDrawable);
                    }
                    binding.stateLayout.showFailureState(stateModel.code, stateModel.text, stateModel.isRetry);
                    binding.stateLayout.getRetryView().setOnClickListener(stateModel.onClickListener);
                    break;
            }
        });

        t.loadingInfo.observe(this, new Observer<LLoadingDialogInfo>() {
            @Override
            public void onChanged(LLoadingDialogInfo lLoadingDialogInfo) {
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
            }
        });
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
            loadingDialog.dismiss(lLoadingDialogInfo.showText, isSuccess, () -> onLoadingDismiss(isSuccess));
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
