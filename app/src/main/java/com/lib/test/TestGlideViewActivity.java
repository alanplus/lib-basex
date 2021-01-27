package com.lib.test;

import com.lib.basex.activity.LActivity;
import com.lib.basex.activity.LViewModel;
import com.lib.test.databinding.ActivityTestGlideBinding;

/**
 * @author Alan
 * 时 间：2021/1/27
 * 简 述：<功能简述>
 */
public class TestGlideViewActivity extends LActivity<LViewModel, ActivityTestGlideBinding> {
    @Override
    public int getContentId() {
        return R.layout.activity_test_glide;
    }

    @Override
    public void initView() {

    }
}
