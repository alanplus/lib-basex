package com.lib.basex.image;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestFutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.lib.basex.utils.Logger;

/**
 * @author Alan
 * 时 间：2020/9/16
 * 简 述：<功能简述>
 */
public class LImageBuilder {

    private String path;
    private int res;

    private boolean isBackground;

    private boolean isAnim;

    private int placeHolderRes;

    private ImageView.ScaleType imageType;

    public LImageBuilder() {
        this.isBackground = false;
        this.isAnim = false;
        this.imageType = ImageView.ScaleType.CENTER_CROP;
    }

    public LImageBuilder setPath(String path) {
        this.path = path;
        return this;
    }

    public LImageBuilder setRes(int res) {
        this.res = res;
        return this;
    }

    public LImageBuilder isBackground(boolean background) {
        isBackground = background;
        return this;
    }

    public LImageBuilder setAnim(boolean anim) {
        isAnim = anim;
        return this;
    }

    public LImageBuilder setPlaceHolderRes(int placeHolderRes) {
        this.placeHolderRes = placeHolderRes;
        return this;
    }

    @SuppressLint("CheckResult")
    public void build(@NonNull View view) {
        if (TextUtils.isEmpty(path) && res == 0) {
            return;
        }

        RequestBuilder<Drawable> builder;
        if (!TextUtils.isEmpty(path)) {
            builder = Glide.with(view).load(path);
        } else {
            builder = Glide.with(view).load(res);
        }

        if (!isAnim) {
            builder.dontAnimate();
        }

        if (placeHolderRes != 0) {
            builder.placeholder(placeHolderRes);
        }

        try {
            if (isBackground || !(view instanceof ImageView)) {
                setBackground(builder, view);
            } else {
                builder.into((ImageView) view);
            }
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    private void setBackground(RequestBuilder<Drawable> builder, View view) {
        builder.into(new CustomViewTarget<View, Drawable>(view) {
            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {

            }

            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    view.setBackground(resource);
                } else {
                    view.setBackgroundDrawable(resource);
                }
            }

            @Override
            protected void onResourceCleared(@Nullable Drawable placeholder) {

            }
        });
    }
}
