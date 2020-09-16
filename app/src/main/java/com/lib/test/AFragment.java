package com.lib.test;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lib.basex.activity.LViewModel;
import com.lib.basex.fragment.LFragment;
import com.lib.basex.widget.LBannerLayout;
import com.lib.test.databinding.FragmentABinding;

/**
 * @author Alan
 * 时 间：2020/9/16
 * 简 述：<功能简述>
 */
public class AFragment extends LFragment<LViewModel, FragmentABinding> {
    @Override
    public void initView(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int getContentId() {
        return R.layout.fragment_a;
    }
}
