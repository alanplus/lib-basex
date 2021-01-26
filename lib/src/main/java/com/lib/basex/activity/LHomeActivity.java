package com.lib.basex.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.lib.basex.R;
import com.lib.basex.databinding.LActivityHomeBaseBinding;
import com.lib.basex.utils.LUtils;
import com.lib.basex.widget.viewpager.LPagerFragmentAdapter;
import com.lib.basex.widget.viewpager.LViewPager;

/**
 * @author Alan
 * 时 间：2020/9/15
 * 简 述：<功能简述>
 */
public abstract class LHomeActivity extends LActivity<LViewModel, LActivityHomeBaseBinding> implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    protected View[] mMsgHitView;
    protected LPagerFragmentAdapter<Fragment> mPagerAdapter;

    protected int msgColor = Color.RED;

    @Override
    public int getContentId() {
        return R.layout.l_activity_home_base;
    }

    @Override
    public void initView() {
        d.bvHomeNavigation.setItemIconTintList(null);
        d.bvHomeNavigation.setOnNavigationItemSelectedListener(this);
        d.bvHomeNavigation.setItemHorizontalTranslationEnabled(true);
        mMsgHitView = new View[getTabSize()];
        initViewPager(d.lViewPager);
    }

    @Override
    protected LViewModel createViewModel() {
        return createViewModel(LViewModel.class);
    }

    protected void initViewPager(LViewPager viewPager) {
        FragmentManager fm = getSupportFragmentManager();
        mPagerAdapter = new LPagerFragmentAdapter<>(fm, getFragmentArray());
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setScrollable(false);
        viewPager.setOffscreenPageLimit(getTabSize());
        viewPager.setCurrentItem(0, false);
        viewPager.addOnPageChangeListener(this);
    }

    protected abstract Fragment[] getFragmentArray();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        d.lViewPager.setCurrentItem(getPosition(menuItem), d.lViewPager.isScrollable());
        // 返回false menu点击没响应
        return true;
    }

    protected int getTabSize() {
        return d.bvHomeNavigation.getMenu().size();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        d.bvHomeNavigation.setSelectedItemId(getItemMenuId(position));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private int getItemMenuId(int position) {
        MenuItem item = d.bvHomeNavigation.getMenu().getItem(position);
        return item.getItemId();
    }

    public void showMsgHitView(int position) {
        if (position < mMsgHitView.length) {
            if (mMsgHitView[position] == null) {
                generateMsgHitView(position);
            } else {
                mMsgHitView[position].setVisibility(View.VISIBLE);
            }
        }
    }

    public void hiddenHitMsgView(int position) {
        if (position < mMsgHitView.length && mMsgHitView[position] != null) {
            mMsgHitView[position].setVisibility(View.GONE);
        }
    }

    private void generateMsgHitView(final int position) {
        d.bvHomeNavigation.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    d.bvHomeNavigation.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    d.bvHomeNavigation.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
                View view = new View(getActivity());
                view.setBackgroundDrawable(generateMsgHitViewDrawable(msgColor));
                view.setLayoutParams(generateMsgHitViewLayoutParams(position));
                d.bvHomeNavigation.addView(view);
            }
        });
    }

    protected Drawable generateMsgHitViewDrawable(int color) {
        int i = LUtils.dip2px(6);
        float[] out = new float[]{i, i, i, i, i, i, i, i};
        ShapeDrawable drawable = new ShapeDrawable(new RoundRectShape(out, null, null));
        drawable.getPaint().setColor(color);
        return drawable;
    }

    protected FrameLayout.LayoutParams generateMsgHitViewLayoutParams(int position) {
        int i = LUtils.dip2px(8);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(i, i);
        int[] screenSize = LUtils.getScreenSize(this);
        BottomNavigationMenuView bottomNavigationMenuView = (BottomNavigationMenuView) d.bvHomeNavigation.getChildAt(0);
        BottomNavigationItemView v = (BottomNavigationItemView) bottomNavigationMenuView.getChildAt(position);
        int itemWidth = v.getWidth();
        int left = (screenSize[0] - itemWidth * getTabSize()) / 2 + position * itemWidth;
        layoutParams.leftMargin = left + getTabMsgHitLeft(itemWidth);
        layoutParams.topMargin = getTabMsgHitTop();
        return layoutParams;
    }

    protected int getTabMsgHitLeft(int itemWidth) {
        int itemIconSize = d.bvHomeNavigation.getItemIconSize();
        return (itemIconSize + itemWidth) / 2 - LUtils.dip2px(8);
    }

    protected int getTabMsgHitTop() {
        return LUtils.dip2px(5);
    }

    private int getPosition(MenuItem menuItem) {
        Menu menu = d.bvHomeNavigation.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            if (item == menuItem) {
                return i;
            }
        }
        return 0;
    }
}
