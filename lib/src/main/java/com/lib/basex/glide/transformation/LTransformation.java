package com.lib.basex.glide.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.lib.basex.utils.Logger;

import java.security.MessageDigest;

/**
 * @author Alan
 * 时 间：2021/1/27
 * 简 述：<功能简述>
 */
public class LTransformation extends BitmapTransformation {

    private Paint mBorderPaint;
    private int borderWidth;
    private int borderColor;

    private boolean isCircle;
    private int left;
    private int top;
    private int right;
    private int bottom;

    public LTransformation(int borderWidth, int borderColor, boolean isCircle) {
        this(borderWidth, borderColor, isCircle, 0, 0, 0, 0);
    }

    public LTransformation(int borderWidth, int borderColor, int radius) {
        this(borderWidth, borderColor, false, radius, radius, radius, radius);
    }

    public LTransformation(int borderWidth, int borderColor, int left, int top, int right, int bottom) {
        this(borderWidth, borderColor, false, left, top, right, bottom);
    }

    public LTransformation(int borderWidth, int borderColor) {
        this(borderWidth, borderColor, false, 0, 0, 0, 0);
    }


    public LTransformation(int borderWidth, int borderColor, boolean isCircle, int left, int top, int right, int bottom) {
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(borderWidth);

        this.isCircle = isCircle;
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Logger.d("transform");
        return isCircle ? circleCrop(pool, toTransform) : cornerCrop(pool, toTransform);
    }

    private Bitmap cornerCrop(BitmapPool mBitmapPool, Bitmap source) {
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap bitmap = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
        //新建一个空白的bitmap
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //设置要绘制的图形
        paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
        //设置边框样式
        Paint borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setColor(borderColor);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setStrokeWidth(borderWidth);

        drawRoundRect(canvas, paint, width, height, borderPaint);
        BitmapResource resource = BitmapResource.obtain(bitmap, mBitmapPool);
        return resource.get();
    }


    private void drawRoundRect(Canvas canvas, Paint paint, float width, float height, Paint borderPaint) {
        float halfBorder = borderWidth >> 1;
        Path path = new Path();
        if (left == 0 && top == 0 && right == 0 && bottom == 0) {
            path.addRect(new RectF(halfBorder, halfBorder, width - halfBorder, height - halfBorder), Path.Direction.CW);
        } else {
            float[] pos = new float[]{left, left, top, top, right, right, bottom, bottom};
            path.addRoundRect(new RectF(halfBorder, halfBorder, width - halfBorder, height - halfBorder), pos, Path.Direction.CW);
        }
        //绘制要加载的图形
        canvas.drawPath(path, paint);
        //绘制边框
        canvas.drawPath(path, borderPaint);

    }


    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null) {
            return null;
        }
        int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (borderWidth / 2));
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        if (mBorderPaint != null) {
            float borderRadius = r - (borderWidth >> 1);
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }
        return result;

    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
