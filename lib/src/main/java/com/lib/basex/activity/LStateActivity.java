package com.lib.basex.activity;

import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import com.lib.basex.R;
import com.lib.basex.databinding.LActivityStateBinding;
import com.lib.basex.widget.statelayout.view.LLoadingStateView;

/**
 * @author Alan
 * 时 间：2020/10/16
 * 简 述：<功能简述>
 */
public abstract class LStateActivity<T extends LStateViewModel, D extends ViewDataBinding> extends LActivity<T, D> {

    protected LActivityStateBinding binding;

    @Override
    protected void initContentView() {
        binding = DataBindingUtil.setContentView(this, R.layout.l_activity_state);
        d = DataBindingUtil.inflate(LayoutInflater.from(this), getContentId(), null, false);
        binding.stateLayout.addView(d.getRoot(), new RelativeLayout.LayoutParams(-1, -1));

    }

    @Override
    protected void onCreate() {
        super.onCreate();
        t.state.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer) {
                    case LStateViewModel.STATE_LOADING:
                        binding.stateLayout.showLoadingState(t.getLoadingText());
                        break;
                    case LStateViewModel.STATE_SUCCESS:
                        binding.stateLayout.showSuccessState();
                        break;
                    case LStateViewModel.STATE_FAILURE:
                        binding.stateLayout.showFailureState(t.getFailureText(), t.isRetry);
                        if (t.isRetry) {
                            binding.stateLayout.getRetryView().setOnClickListener(t.getRetryOnClickListener());
                        } else {
                            binding.stateLayout.getRetryView().setOnClickListener(null);
                        }
                        break;
                }
            }
        });
    }

    public void setTitleBar(String title) {
        binding.titleBar.setTitle(title);
    }

}
