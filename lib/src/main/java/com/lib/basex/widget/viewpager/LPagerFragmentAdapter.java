package com.lib.basex.widget.viewpager;

import android.os.Parcelable;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;


public class LPagerFragmentAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

    private T[] mFragmentList;

    public LPagerFragmentAdapter(@NonNull FragmentManager fm, T[] mFragments) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mFragmentList = mFragments;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @NonNull
    @Override
    public T getItem(int position) {
        return mFragmentList[position % getCount()];
    }

    @Override
    public int getCount() {
        return mFragmentList.length;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return LPagerFragmentAdapter.POSITION_NONE;
    }
}