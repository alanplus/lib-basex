package com.lib.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.lib.basex.thread.IThreadService;
import com.lib.basex.thread.LThreadService;
import com.lvleo.dataloadinglayout.DataLoadingLayout;

/**
 * @author Alan
 * 时 间：2020-09-08
 * 简 述：<功能简述>
 */
public class LoadingActivity extends Activity implements DataLoadingLayout.OnViewTouchListener {

    private DataLoadingLayout mLoadingLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_loading);
        mLoadingLayout = findViewById(R.id.loading_layout);
        mLoadingLayout.setDataView(findViewById(R.id.tv_test4));
        findViewById(R.id.tv_test1).setOnClickListener(v -> tv1(v));
        findViewById(R.id.tv_test2).setOnClickListener(v -> tv2(v));
        findViewById(R.id.tv_test3).setOnClickListener(v -> tv3(v));
    }

    @Override
    public void onTouchUp() {
        // if data load Error or data is empty, can get data again by touch the view
        getData(1);
    }

    private void getData(int is) {
        mLoadingLayout.loading();

        LThreadService.run(new IThreadService<Integer>() {
            @Override
            public Integer runOnThread() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return is;
            }

            @Override
            public void runOnMainThread(Integer aBoolean) {
                if (aBoolean == 1) {
                    mLoadingLayout.loadSuccess("加载成功");
                } else if (aBoolean == 2) {
                    mLoadingLayout.loadError("空数据");
                } else {
                    mLoadingLayout.loadError("加载失败");
                }
            }
        });
    }

    public void tv1(View v) {
        getData(1);
    }

    public void tv2(View v) {
        getData(3);
    }

    public void tv3(View v) {
        getData(2);
    }
}
