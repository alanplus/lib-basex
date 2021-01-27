package com.lib.basex.glide.request;


import android.graphics.drawable.Drawable;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;

import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;

/**
 * @author Alan
 * 时 间：2021/1/20
 * 简 述：<功能简述>
 */
public interface IGlideConfig<T> {

    /**
     * 设置url
     *
     * @param url 设置url
     * @return 返回当前对象
     */
    T setUrl(String url);

    /**
     * 设置占位图
     *
     * @param placeHolder 占位图 ID
     * @return 返回当前对象
     */
    T setPlaceHolder(@DrawableRes int placeHolder);

    /**
     * 设置错误页
     *
     * @param errRes 错误页 ID
     * @return 返回当前对象
     */
    T setErrorRes(@DrawableRes int errRes);

    /**
     * 是否是圆形
     *
     * @return 返回当前对象
     */
    T isCircle();

    /**
     * 设置圆角
     *
     * @param left   左
     * @param top    上
     * @param right  右
     * @param bottom 下
     * @return 返回当前对象
     */
    T setRadius(int left, int top, int right, int bottom);

    /**
     * 设置圆角
     *
     * @param radius 角度
     * @return 返回当前对象
     */
    T setRadius(int radius);

    /**
     * 设置 边框 宽度
     *
     * @param width 宽度
     * @return 返回当前对象
     */
    T setStrokeWidth(int width);

    /**
     * 设置 边框 颜色
     *
     * @param color 颜色
     * @return 返回当前对象
     */
    T setStrokeColor(@ColorInt int color);

    /**
     * 设置 资源图片
     *
     * @param res 图片ID
     * @return 返回当前对象
     */
    T setDrawableId(@DrawableRes int res);

    /**
     * 设置是否使用缓存
     *
     * @param useCache 是否使用缓存
     * @return 返回当前对象
     */
    T setUseCache(int useCache);

    /**
     * 禁用内存缓存功能
     *
     * @param useCache true 禁用 false 使用
     * @return 返回当前对象
     */
    T skipMemoryCache(boolean useCache);

    /**
     * 加载
     */
    void load();

    /**
     * 加载 到 target
     *
     * @param target 参数
     */
    void load(Target<Drawable> target);

    /**
     * 设置 Glide 参数
     *
     * @param requestOption Glide 参数
     * @return 返回当前对象
     */
    T setRequestOption(RequestOptions requestOption);


}
