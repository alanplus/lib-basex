package com.lib.test;

import com.bumptech.glide.Glide;
import com.lib.basex.LApplication;

/**
 * @author Alan
 * 时 间：2020-09-08
 * 简 述：<功能简述>
 */
public class App extends LApplication {
    @Override
    public void init() {
        Glide.get(this).clearMemory();
        Glide.get(this).clearDiskCache();
    }

    @Override
    public boolean isDebug() {
        return true;
    }
}
