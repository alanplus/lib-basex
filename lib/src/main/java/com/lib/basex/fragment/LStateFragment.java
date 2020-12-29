package com.lib.basex.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.lib.basex.R;
import com.lib.basex.activity.LLoadingDialogInfo;
import com.lib.basex.activity.LStateViewModel;
import com.lib.basex.databinding.LFragmentStateBinding;
import com.lib.basex.dialog.loading.LoadingDialog;
import com.lib.basex.utils.LClassUtils;

import java.util.Objects;

/**
 * @author Alan
 * 时 间：2020/11/19
 * 简 述：<功能简述>
 */
public abstract class LStateFragment<VM extends LStateViewModel, D extends ViewDataBinding> extends LFragment<VM, D> {

    protected LFragmentStateBinding binding;

    protected LoadingDialog loadingDialog;

    @Override
    protected void initContentView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        binding = DataBindingUtil.inflate(inflater, R.layout.l_fragment_state, container, false);
        d = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getContentId(), null, false);
        binding.stateLayout.addView(d.getRoot(), new RelativeLayout.LayoutParams(-1, -1));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vm = (VM) new ViewModelProvider(requireActivity()).get(LClassUtils.getTClassObject(this));
        initContentView(inflater, container);
        initView(savedInstanceState);
        return binding.getRoot();
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        vm.state.observe(getViewLifecycleOwner(), stateModel -> {
            switch (stateModel.state) {
                case LStateViewModel.STATE_LOADING:
                    binding.stateLayout.showLoadingState(stateModel.text);
                    break;
                case LStateViewModel.STATE_SUCCESS:
                    binding.stateLayout.showSuccessState();
                    break;
                case LStateViewModel.STATE_FAILURE:
                    binding.stateLayout.showFailureState(stateModel.code, stateModel.text, stateModel.isRetry);
                    binding.stateLayout.getRetryView().setOnClickListener(stateModel.onClickListener);
                    break;
            }
        });

        vm.loadingInfo.observe(getViewLifecycleOwner(), lLoadingDialogInfo -> {
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

    protected void showLoadingDialog(LLoadingDialogInfo lLoadingDialogInfo) {
        if (null == loadingDialog) {
            loadingDialog = new LoadingDialog(Objects.requireNonNull(getActivity()));
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
