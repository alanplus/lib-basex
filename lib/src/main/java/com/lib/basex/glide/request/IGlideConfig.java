package com.lib.basex.glide.request;


import androidx.annotation.DrawableRes;

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
}
