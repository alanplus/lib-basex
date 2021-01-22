package com.lib.basex.glide;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.lib.basex.glide.request.GlideLoader;
import com.lib.basex.glide.request.IGlideConfig;

/**
 * @author Alan
 * 时 间：2021/1/20
 * 简 述：<功能简述>
 */
public class GlideView extends androidx.appcompat.widget.AppCompatImageView implements IGlideConfig<GlideView> {

    private GlideLoader glideLoader;


    public GlideView(Context context) {
        this(context, null);
    }

    public GlideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        glideLoader = GlideLoader.create(this);
    }

    @Override
    public GlideView setUrl(String url) {
        glideLoader = glideLoader.setUrl(url);
        return this;
    }

    @Override
    public GlideView setPlaceHolder(@DrawableRes int placeHolder) {
        glideLoader = glideLoader.setPlaceHolder(placeHolder);
        return this;
    }

    @Override
    public GlideView setErrorRes(int errRes) {
        glideLoader = glideLoader.setErrorRes(errRes);
        return this;
    }

    @Override
    public GlideView isCircle() {
        return null;
    }

    @Override
    public GlideView setRadius(int left, int top, int right, int bottom) {
        return null;
    }

    @Override
    public GlideView setRadius(int radius) {
        return null;
    }

}
