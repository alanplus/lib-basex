package com.lib.test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lib.basex.activity.LActivity;
import com.lib.basex.activity.LViewModel;
import com.lib.basex.activity.LWebViewActivity;
import com.lib.basex.annotation.Activity;
import com.lib.basex.dialog.LTextListDialog;
import com.lib.basex.dialog.LightLoadingDialog;
import com.lib.basex.utils.LActivityUtils;
import com.lib.basex.utils.LToastManager;
import com.lib.basex.widget.popup.LListPopupWindow;
import com.lib.test.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

@Activity("aaa")
public class MainActivity extends LActivity<AViewModel, ActivityMainBinding> {


    @Override
    public int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        d.content.setBackgroundColor(Color.WHITE);
//        d.content.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                t.arrayMap.put("name", "abac");
//                startActivity(LoadingActivity.class);
//            }
//        }, 3000);
        d.showLightDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupWindow();
//                LTextListDialog textListDialog = new LTextListDialog(getActivity());
//                textListDialog.setData(new String[]{"全部", "项目1", "项目1", "项目1"});
//                textListDialog.setOnItemClickListener(new LTextListDialog.OnItemClickListener() {
//                    @Override
//                    public void onItemClickListener(int position, String s, View view) {
//                        LToastManager.getInstance().showToast(getActivity(), "position:" + position);
//                    }
//                });
//                textListDialog.show();
//                LActivityUtils.startActivity(getActivity(), TestGlideViewActivity.class);
//                LightLoadingDialog lightLoadingDialog = new LightLoadingDialog(getActivity());
//                lightLoadingDialog.show();
            }
        });

        d.showWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), LWebViewActivity.class);
                intent.putExtra("title", "测试");
                intent.putExtra("url", "http://www.baidu.com");
                startActivity(intent);
            }
        });
    }


    public void toLeak(View view) {
        Intent intent = new Intent();
        intent.setClass(this, TestLeakActivity.class);
        startActivity(intent);
    }

    public void toLoad(View view) {
        Intent intent = new Intent();
        intent.setClass(this, LoadingActivity.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void getData(View view) {
        TestLeakActivity testLeakActivity = TestLeakActivity.list.get(0);
        Log.d("test_basex", "is_destroy:" + testLeakActivity.isDestroyed());
        Log.d("test_basex", "name:" + testLeakActivity.getName());
    }

    protected void showPopupWindow() {
        LListPopupWindow lListPopupWindow = new LListPopupWindow(this);
        List<String> list = new ArrayList<>();
        list.add("1，撒打算的");
        list.add("2，撒打算的");
        list.add("3，撒打算的");
        list.add("4，撒打算的");
        lListPopupWindow.setData(list);
        lListPopupWindow.showAsDropDown(d.showLightDialog, 0, 0);

        lListPopupWindow.setOnItemClickListener((view, position, s) -> LToastManager.getInstance().showToast(MainActivity.this, "位置：" + position));
    }
}
