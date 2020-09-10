package com.lib.basex.widget.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class LXViewPager extends ViewPager {
    private boolean isScrollable = true;

    public LXViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LXViewPager(Context context) {
        super(context);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onTouchEvent(ev);
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isScrollable) {
            return false;
        } else {
            return super.onInterceptTouchEvent(ev);
        }
    }

    public void setScrollable(boolean scrollable) {
        isScrollable = scrollable;
    }
}
