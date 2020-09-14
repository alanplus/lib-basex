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
import com.lib.test.databinding.ActivityMainBinding;

public class MainActivity extends LActivity<AViewModel, ActivityMainBinding> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActivityMainBinding inflate = ActivityMainBinding.inflate(LayoutInflater.from(this));
//        LinearLayout content = inflate.content;
//        TextView abc = inflate.abc;

        d.content.setBackgroundColor(Color.BLUE);
        d.content.postDelayed(new Runnable() {
            @Override
            public void run() {
                t.arrayMap.put("name","abac");
            }
        },3000);

    }

    @Override
    public int getContentId() {
        return R.layout.activity_main;
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
}
