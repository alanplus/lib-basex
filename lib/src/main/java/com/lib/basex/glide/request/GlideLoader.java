package com.lib.basex.glide.request;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lib.basex.LApplication;
import com.lib.basex.glide.transformation.CornerTransformation;
import com.lib.basex.glide.transformation.LTransformation;

import java.lang.ref.WeakReference;

/**
 * @author Alan
 * 时 间：2021/1/20
 * 简 述：<功能简述>
 */
public class GlideLoader implements IGlideConfig<GlideLoader> {

    private WeakReference<ImageView> imageViewWeakReference;
    private RequestBuilder<Drawable> builder;

    private int strokeWidth;
    private int strokeColor;
    private int left;
    private int top;
    private int right;
    private int bottom;
    private boolean isCircle;

    public static GlideLoader create(ImageView imageView) {
        return new GlideLoader(imageView);
    }

    private GlideLoader(@NonNull ImageView imageView) {
        imageViewWeakReference = new WeakReference<>(imageView);
        builder = Glide.with(LApplication.app).asDrawable();
        this.isCircle = false;
    }

    @Override
    public void load() {
        setParams();
        builder.into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

            }
        });
    }

    private void setParams() {
        if (strokeWidth == 0) {
            if (isCircle) {
                builder = builder.circleCrop();
            }
            if (isCorner()) {
                builder = builder.transform(new CornerTransformation(left, top, right, bottom));
            }
        } else {
            builder = builder.transform(new LTransformation(strokeWidth, strokeColor, isCircle, left, top, right, bottom));
        }
        ImageView img = imageViewWeakReference.get();
        if (null != img) {
            builder.into(img);
        }
    }

    @Override
    public void load(Target<Drawable> target) {
        setParams();
        builder.into(target);
    }

    @Override
    public GlideLoader setRequestOption(RequestOptions requestOption) {
        builder = builder.apply(requestOption);
        return this;
    }

    @Override
    public GlideLoader setUrl(String url) {
        builder = builder.load(url);
        return this;
    }

    @Override
    public GlideLoader setPlaceHolder(int placeHolder) {
        if (placeHolder != 0) {
            builder = builder.placeholder(placeHolder);
        }
        return this;
    }


    @Override
    public GlideLoader setErrorRes(int errRes) {
        if (errRes != 0) {
            builder = builder.error(errRes);
        }
        return this;
    }

    @Override
    public GlideLoader isCircle() {
        this.isCircle = true;
        return this;
    }

    @Override
    public GlideLoader setRadius(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        return this;
    }

    @Override
    public GlideLoader setRadius(int radius) {
        return setRadius(radius, radius, radius, radius);
    }

    @Override
    public GlideLoader setStrokeWidth(int width) {
        this.strokeWidth = width;
        return this;
    }

    @Override
    public GlideLoader setStrokeColor(int color) {
        this.strokeColor = color;
        return this;
    }

    @Override
    public GlideLoader setDrawableId(int res) {
        builder = builder.load(res);
        return this;
    }

    @Override
    public GlideLoader setUseCache(int useCache) {
        switch (useCache) {
            case 0:
                builder = builder.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case 1:
                builder = builder.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case 2:
                builder = builder.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case 3:
                builder = builder.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case 4:
                builder = builder.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;

        }
        return this;
    }

    @Override
    public GlideLoader skipMemoryCache(boolean useCache) {
        builder = builder.skipMemoryCache(useCache);
        return this;
    }

    private boolean isCorner() {
        return left != 0 || top != 0 || right != 0 || bottom != 0;
    }

}
