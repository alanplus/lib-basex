package com.lib.test;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.lib.basex.thread.LThreadService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alan
 * 时 间：2020-09-08
 * 简 述：<功能简述>
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class TestLeakActivity extends Activity {

    private TextView textView;

    public String name = "dddd";

    public static List<TestLeakActivity> list = new ArrayList<>();

    public Runnable runnable = () -> {
        textView.setText("abcaa");
        Activity activity = list.get(0);
        if (null != activity) {
            Log.d("test_basex", "abc:" + activity.isDestroyed());
        }
        Log.d("test_basex", getName());
        boolean destroyed = isDestroyed();
        Log.d("test_basex", "is:" + destroyed);
        if (!destroyed) {
            testLeak(null);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list.add(this);
        setContentView(R.layout.activity_test_leak);
        textView = findViewById(R.id.tv_test1);
    }

    public void testLeak(View view) {
        LThreadService.runDelay(runnable, 3000);
        if (null != view)
            finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test_basex", "onDestroy");
    }

    public String getName() {
        return name;
    }
}
