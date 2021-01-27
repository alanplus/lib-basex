package com.lib.basex.glide;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lib.basex.R;
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
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        glideLoader = GlideLoader.create(this);
        if (null == attrs) {
            return;
        }
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.GlideView);
        boolean isCircle = array.getBoolean(R.styleable.GlideView_l_glide_circle, false);
        if (isCircle) {
            isCircle();
        } else {
            int radius = array.getDimensionPixelSize(R.styleable.GlideView_l_glide_radius, 0);
            int left = array.getDimensionPixelSize(R.styleable.GlideView_l_glide_corner_left, radius);
            int top = array.getDimensionPixelSize(R.styleable.GlideView_l_glide_corner_top, radius);
            int right = array.getDimensionPixelSize(R.styleable.GlideView_l_glide_corner_right, radius);
            int bottom = array.getDimensionPixelSize(R.styleable.GlideView_l_glide_corner_bottom, radius);
            if (left != 0 || top != 0 || right != 0 || bottom != 0) {
                setRadius(left, top, right, bottom);
            }
        }

        int width = array.getDimensionPixelSize(R.styleable.GlideView_l_glide_stroke_width, 0);
        setStrokeWidth(width);
        int color = array.getColor(R.styleable.GlideView_l_glide_stroke_color, Color.WHITE);
        setStrokeColor(color);

        int placeHolder = array.getResourceId(R.styleable.GlideView_l_glide_place_holder, 0);
        if (placeHolder != 0) {
            setPlaceHolder(placeHolder);
        }
        int error = array.getResourceId(R.styleable.GlideView_l_glide_error_res, 0);
        if (error != 0) {
            setErrorRes(error);
        }

        int useCache = array.getInt(R.styleable.GlideView_l_glide_use_cache, 4);
        setUseCache(useCache);

        int src = array.getResourceId(R.styleable.GlideView_l_glide_src, 0);
        setDrawableId(src);
        if (src != 0) {
            load();
        }
        array.recycle();

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
        glideLoader.isCircle();
        return this;
    }

    @Override
    public GlideView setRadius(int left, int top, int right, int bottom) {
        glideLoader.setRadius(left, top, right, bottom);
        return this;
    }

    @Override
    public GlideView setRadius(int radius) {
        glideLoader.setRadius(radius);
        return this;
    }

    @Override
    public GlideView setStrokeWidth(int width) {
        glideLoader.setStrokeWidth(width);
        return this;
    }

    @Override
    public GlideView setStrokeColor(int color) {
        glideLoader.setStrokeColor(color);
        return this;
    }

    @Override
    public GlideView setDrawableId(int res) {
        glideLoader.setDrawableId(res);
        return this;
    }

    @Override
    public GlideView setUseCache(int useCache) {
        glideLoader.setUseCache(useCache);
        return null;
    }

    @Override
    public GlideView skipMemoryCache(boolean useCache) {
        glideLoader.skipMemoryCache(useCache);
        return this;
    }

    @Override
    public void load() {
        glideLoader.load();
    }

    @Override
    public void load(Target<Drawable> target) {
        glideLoader.load(target);
    }

    @Override
    public GlideView setRequestOption(RequestOptions requestOption) {
        glideLoader.setRequestOption(requestOption);
        return this;
    }


}
