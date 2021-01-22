package com.lib.basex.glide.request;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lib.basex.LApplication;
import com.lib.basex.glide.transformation.CircleTransformation;

import java.lang.ref.WeakReference;

/**
 * @author Alan
 * 时 间：2021/1/20
 * 简 述：<功能简述>
 */
public class GlideLoader implements IGlideConfig<GlideLoader> {

    private WeakReference<ImageView> imageViewWeakReference;
    private RequestBuilder<Drawable> builder;

    public static GlideLoader create(ImageView imageView) {
        return new GlideLoader(imageView);
    }

    private GlideLoader(@NonNull ImageView imageView) {
        imageViewWeakReference = new WeakReference<>(imageView);
        builder = Glide.with(LApplication.app).asDrawable();
    }

    public GlideLoader load(@NonNull String url, @DrawableRes int placeholder, Transformation<Bitmap> transformation) {
        ImageView imageView = imageViewWeakReference.get();
        if (null == imageView) {
            return this;
        }
        builder = builder.load(url);
        if (placeholder != 0) {
            builder = builder.placeholder(placeholder);
        }

        if (transformation != null) {
            builder = builder.transform(transformation);
        }
        builder.into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                ImageView img = imageViewWeakReference.get();
                if (null != img) {
                    img.setImageDrawable(resource);
                }
            }
        });
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
        builder = builder.transform(new CircleTransformation());
        return this;
    }

    @Override
    public GlideLoader setRadius(int left, int top, int right, int bottom) {
        return this;
    }

    @Override
    public GlideLoader setRadius(int radius) {
        return this;
    }

}
