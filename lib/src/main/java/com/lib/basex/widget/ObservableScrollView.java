package com.lib.basex.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * @author Alan
 * 时 间：2021/1/13
 * 简 述：
 * scrollview的滑动监听事件setOnScrollChangeListener这个方法是在6.0以上才能用的。
 * 为了考虑低版本的的需求，要重写ScrollView把接口开放出来。
 */
public class ObservableScrollView extends ScrollView {

    private ScrollViewListener scrollViewListener = null;

    public ObservableScrollView(Context context) {
        super(context);
    }

    public ObservableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollViewListener(ScrollViewListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    public interface ScrollViewListener {
        void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (null != scrollViewListener) {
            scrollViewListener.onScrollChanged(this, l, t, oldl, oldt);
        }
    }
}
