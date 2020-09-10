package com.lib.basex.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.lib.basex.LApplication;

/**
 * @author Alan
 * 时 间：2020-09-10
 * 简 述：<功能简述>
 */
public class LSharedPreferences {

    private SharedPreferences sharedPreferences;
    private static LSharedPreferences lSharedPreferences;

    private LSharedPreferences() {
        if (null == sharedPreferences) {
            sharedPreferences = LApplication.app.getSharedPreferences("l_config", Context.MODE_PRIVATE);
        }
    }

    public synchronized static LSharedPreferences getInstance() {
        if (null == lSharedPreferences) {
            lSharedPreferences = new LSharedPreferences();
        }
        return lSharedPreferences;
    }

    public int getInt(String name, int defValue) {
        return sharedPreferences.getInt(name, defValue);
    }

    public String getString(String name, String defValue) {
        return sharedPreferences.getString(name, defValue);
    }

    public Boolean getBool(String name, boolean defValue) {
        return sharedPreferences.getBoolean(name, defValue);
    }

    public long getLong(String name, long defValue) {
        return sharedPreferences.getLong(name, defValue);
    }

    public void setInt(String name, int value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt(name, value);
        edit.commit();
    }

    public void setString(String name, String value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(name, value);
        edit.commit();
    }

    public void setBool(String name, boolean value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putBoolean(name, value);
        edit.commit();
    }

    public void setLong(String name, long value) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putLong(name, value);
        edit.commit();
    }
}
