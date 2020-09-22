package com.lib.basex.widget.viewpager;

import android.view.View;

import androidx.annotation.NonNull;

import java.util.List;

/**
 * @author Alan
 * 时 间：2020/9/22
 * 简 述：<功能简述>
 */
public class LViewPagerBuilder<T, V extends View> {

    private LViewPager lViewPager;
    private List<T> dataList;
    private IPageViewAdapter<T, V> adapter;

    private int duration;

    public LViewPagerBuilder(@NonNull LViewPager lViewPager, List<T> dataList) {
        this.lViewPager = lViewPager;
        this.dataList = dataList;
        duration = 1000;
    }

    public LViewPagerBuilder setDuration(int duration) {
        this.duration = duration;
        return this;
    }

    public LViewPagerBuilder setAdapter(IPageViewAdapter<T, V> adapter) {
        this.adapter = adapter;
        return this;
    }

    public void build() {
        PageViewAdapter<T, V> pageViewAdapter = new PageViewAdapter<T, V>(dataList, adapter);
        lViewPager.setAdapter(pageViewAdapter);
        lViewPager.setScrollable(duration != 0);
        if (duration != 0) {
            ViewPagerScroller scroller = new ViewPagerScroller(lViewPager.getContext());
            scroller.setScrollDuration(duration);
            scroller.initViewPagerScroll(lViewPager);
        }
    }
}
