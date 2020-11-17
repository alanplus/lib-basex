package com.lib.basex.utils;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

import androidx.annotation.IntDef;

/**
 * @author Alan
 * 时 间：2020/11/17
 * 简 述：<功能简述>
 */
public class LAnimation {

    public static final int TYPE_ALPHA = 1;
    public static final int TYPE_TRANSLATE = 2;
    public static final int TYPE_ROTATE = 3;
    public static final int TYPE_SCALE = 4;

    @IntDef({TYPE_ALPHA, TYPE_TRANSLATE, TYPE_ROTATE, TYPE_SCALE})
    public @interface AnimationType {
    }

    private Animation animation;
    private int type;

    // 公共属性
    private Interpolator interpolator;
    private int duration;
    private int repeatCount;
    private int repeatMode;
    private boolean isFilter;

    // 旋转
    private float fromDegree;
    private float toDegree;

    // 平移
    private float x;
    private float toX;
    private float y;
    private float toY;

    // 渐变
    private float startColor;
    private float endColor;

    // 缩放
    private float scaleX;
    private float scaleToX;
    private float scaleY;
    private float scaleToY;
    // 缩放中心点的X坐标类型。取值范围为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
    private int scaleXType;
    // 缩放中心点的X坐标值。当pivotXType==ABSOLUTE时，表示绝对位置；否则表示相对位置，1.0表示100%。
    private float scaleXValue;
    private int scaleYType;
    private float scaleYValue;

    public LAnimation(@AnimationType int type) {
        this.type = type;
        interpolator = new LinearInterpolator();
        repeatCount = Animation.INFINITE;
        repeatMode = Animation.RESTART;
        isFilter = false;
        duration = 500;
        scaleXType = Animation.RELATIVE_TO_SELF;
        scaleXValue = 0.5f;
        scaleXType = Animation.RELATIVE_TO_SELF;
        scaleYValue = 0.5f;
    }

    public LAnimation setFromDegree(float fromDegree) {
        this.fromDegree = fromDegree;
        return this;
    }

    public LAnimation setToDegree(float toDegree) {
        this.toDegree = toDegree;
        return this;
    }

    public LAnimation setX(float x) {
        this.x = x;
        return this;
    }

    public LAnimation setToX(float toX) {
        this.toX = toX;
        return this;
    }

    public LAnimation setY(float y) {
        this.y = y;
        return this;
    }

    public LAnimation setToY(float toY) {
        this.toY = toY;
        return this;
    }

    public LAnimation setStartColor(float startColor) {
        this.startColor = startColor;
        return this;
    }

    public LAnimation setEndColor(float endColor) {
        this.endColor = endColor;
        return this;
    }

    public LAnimation setScaleX(float scaleX) {
        this.scaleX = scaleX;
        return this;
    }

    public LAnimation setScaleToX(float scaleToX) {
        this.scaleToX = scaleToX;
        return this;
    }

    public LAnimation setScaleY(float scaleY) {
        this.scaleY = scaleY;
        return this;
    }

    public LAnimation setScaleToY(float scaleToY) {
        this.scaleToY = scaleToY;
        return this;
    }

    public LAnimation setScaleXType(int scaleXType) {
        this.scaleXType = scaleXType;
        return this;
    }

    public LAnimation setScaleXValue(float scaleXValue) {
        this.scaleXValue = scaleXValue;
        return this;
    }

    public LAnimation setScaleYType(int scaleYType) {
        this.scaleYType = scaleYType;
        return this;
    }

    public LAnimation setScaleYValue(float scaleYValue) {
        this.scaleYValue = scaleYValue;
        return this;
    }

    public Animation build() {
        Animation animation = null;
        switch (type) {
            case TYPE_ROTATE:
                animation = createDefaultRotateAnimation(fromDegree, toDegree);
                break;
            case TYPE_TRANSLATE:
                animation = createDefaultTranslateAnimation(x, toX, y, toY);
                break;
            case TYPE_ALPHA:
                animation = createDefaultAlphaAnimation(startColor, endColor);
                break;
            case TYPE_SCALE:
                animation = createDefaultScaleAnimation(scaleX, scaleToX, scaleY, scaleToY, scaleXType, scaleXValue, scaleYType, scaleYValue);
                break;
        }
        animation.setInterpolator(interpolator);
        animation.setDuration(duration);
        animation.setFillAfter(isFilter);
        animation.setRepeatCount(repeatCount);
        animation.setRepeatMode(repeatMode);
        return animation;
    }

    public static RotateAnimation createDefaultRotateAnimation() {
        return createDefaultRotateAnimation(0, 359);
    }

    public static RotateAnimation createDefaultRotateAnimation(float from, float to) {
        RotateAnimation animation = new RotateAnimation(from, to, 0.5f, 0.5f);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setFillAfter(false);
        return animation;
    }

    public static TranslateAnimation createDefaultTranslateAnimation(float x, float toX, float y, float toY) {
        TranslateAnimation animation = new TranslateAnimation(x, toX, y, toY);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.RESTART);
        animation.setFillAfter(false);
        return animation;
    }

    public static AlphaAnimation createDefaultAlphaAnimation(float from, float to) {
        AlphaAnimation animation = new AlphaAnimation(from, to);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setFillAfter(false);
        return animation;
    }

    public static ScaleAnimation createDefaultScaleAnimation(float x, float toX, float y, float toY) {
        ScaleAnimation animation = new ScaleAnimation(x, toX, y, toY, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setFillAfter(false);
        return animation;
    }

    public static ScaleAnimation createDefaultScaleAnimation(float x, float toX, float y, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
        ScaleAnimation animation = new ScaleAnimation(x, toX, y, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.RESTART);
        animation.setFillAfter(false);
        return animation;
    }


}
