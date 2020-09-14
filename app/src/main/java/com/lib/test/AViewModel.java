package com.lib.test;


import android.graphics.Color;

import androidx.databinding.ObservableArrayMap;

import com.lib.basex.activity.LViewModel;
import com.lib.basex.utils.LToastManager;

/**
 * @author Alan
 * 时 间：2020/9/14
 * 简 述：<功能简述>
 */
public class AViewModel extends LViewModel {

    public String name = "back";
    public int color = Color.RED;

    public ObservableArrayMap<String,String> arrayMap = new ObservableArrayMap<>();

    public AViewModel(){
        arrayMap.put("name","小哥哥");
        arrayMap.put("nick_name","xiao jiejie");
    }

    public int getColor(){
        return color;
    }

    public void click(){
        LToastManager.getInstance().showToast(App.app,"abc");
    }
}
