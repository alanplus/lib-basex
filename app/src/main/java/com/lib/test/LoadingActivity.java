package com.lib.test;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.lib.basex.activity.LHomeActivity;
import com.lib.basex.activity.LViewModel;


/**
 * @author Alan
 * 时 间：2020-09-08
 * 简 述：<功能简述>
 */
public class LoadingActivity extends LHomeActivity {

    @Override
    public void initView() {
        super.initView();
        showMsgHitView(1);
    }

    @Override
    protected Fragment[] getFragmentArray() {
        return new Fragment[]{new AFragment()};
    }

    @Override
    protected ViewModel createViewModel() {
        return new ViewModelProvider(this).get(LViewModel.class);
    }
}
