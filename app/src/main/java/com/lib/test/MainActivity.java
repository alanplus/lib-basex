package com.lib.test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.lib.basex.thread.LThreadService;
import com.lib.basex.utils.LUtils;
import com.lib.basex.utils.Logger;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.d("abc:"+LUtils.isMainThread());
        new Thread(() -> LThreadService.runOnMainThread(() -> {
            Logger.d("MainActivity runOnMainThread run");
            Logger.d(LUtils.isMainThread());
        })).start();

        LThreadService.runDelay(new Runnable() {
            @Override
            public void run() {
                Logger.d("MainActivity runOnMainThread runDelay");
                Logger.d(LUtils.isMainThread());
            }
        },1000);
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
