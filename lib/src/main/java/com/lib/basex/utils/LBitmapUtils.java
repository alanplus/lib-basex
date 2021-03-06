package com.lib.basex.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;

import androidx.annotation.NonNull;

import com.lib.basex.LApplication;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author Alan
 * 时 间：2020/10/13
 * 简 述：<功能简述>
 */
public class LBitmapUtils {

    public static boolean save(@NonNull Bitmap bitmap, @NonNull String path) {
        File file = new File(path);
        File parentFile = file.getParentFile();
        if (parentFile != null && !parentFile.exists()) {
            boolean mkdirs = parentFile.mkdirs();
            Logger.d("创建文件夹：" + mkdirs + "---" + parentFile.getAbsolutePath());
        }

        if (file.exists()) {
            boolean delete = file.delete();
            Logger.d("删除文件：" + delete + "---" + file.getAbsolutePath());
        }


        OutputStream out = null;
        try {
            boolean newFile = file.createNewFile();
            Logger.d("创建文件：" + newFile + "---" + file.getAbsolutePath());
            out = new FileOutputStream(path);
            Bitmap.CompressFormat format = Bitmap.CompressFormat.PNG;
            String ext = LFileUtils.getFileExt(path);
            if (("jpg").equals(ext)) {
                format = Bitmap.CompressFormat.JPEG;
            }
            return bitmap.compress(format, 100, out);
        } catch (Exception t) {
            Logger.error(t);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    Logger.error(e);
                }
            }
        }
        return false;
    }

    /***
     * 图片的缩放方法
     *
     * @param bitmap
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     */
    public static Bitmap scale(@NonNull Bitmap bitmap, double newWidth, double newHeight) {
        // 获取这个图片的宽和高
        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, (int) width, (int) height, matrix, true);
    }

    /**
     * @param maxSize //图片允许最大空间   单位：KB
     */
    public static Bitmap zip(@NonNull Bitmap bitMap, double maxSize) {
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitMap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        //将字节换成KB
        double mid = b.length >> 10;
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (mid > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = mid / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            bitMap = scale(bitMap, bitMap.getWidth() / Math.sqrt(i), bitMap.getHeight() / Math.sqrt(i));
        }
        return bitMap;
    }

    //按比例缩放
    public static Bitmap zoom(@NonNull Bitmap bitmap, float scale) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(scale, scale);
        Bitmap newBM = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        if (newBM.equals(bitmap)) {
            return newBM;
        }
        bitmap.recycle();
        return newBM;
    }


    /**
     * 获取View的截图
     *
     * @param v
     * @return
     */
    public static Bitmap getViewScreenBitmap(@NonNull View v) {
        //使控件可以进行缓存
        v.setDrawingCacheEnabled(true);
        //获取缓存的 Bitmap
        Bitmap drawingCache = v.getDrawingCache();
        //复制获取的 Bitmap
        drawingCache = Bitmap.createBitmap(drawingCache);
        //关闭视图的缓存
        v.setDrawingCacheEnabled(false);
        return drawingCache;
    }

    public static void recycle(Bitmap... bitmap) {
        if (null == bitmap || bitmap.length == 0) {
            return;
        }
        for (Bitmap b : bitmap) {
            if (null != b && !b.isRecycled()) {
                b.recycle();
                b = null;
            }
        }
    }

    /**
     * 从路径中 加载Bitmap
     *
     * @param pathName 路径
     * @return 返回bitmap
     */
    public static Bitmap getBitmapFromPath(String pathName) {
        try {
            int[] screenSize = LUtils.getScreenSize(LApplication.app);
            int screenWidth = screenSize[0];
            int screenHeight = screenSize[1];
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            Bitmap bitmap;
            if (options.outWidth > screenWidth
                    && options.outHeight > screenHeight) {
                options.inSampleSize = computeSampleSize(options, -1,
                        screenWidth * screenHeight);
            }
            options.inJustDecodeBounds = false;
            bitmap = BitmapFactory.decodeFile(pathName, options);
            return bitmap;
        } catch (Throwable t) {
            t.printStackTrace();
            Logger.error(t);
            return null;
        }
    }

    public static int computeSampleSize(BitmapFactory.Options options,
                                        int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }

        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    // 计算图片的缩放�??
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        boolean isOptionWidthLarger = options.outWidth > options.outHeight;
        boolean isReqWidthLarger = reqWidth > reqHeight;
        final int height = isOptionWidthLarger ? options.outHeight
                : options.outWidth;
        final int width = isOptionWidthLarger ? options.outWidth
                : options.outHeight;
        int reqW = isReqWidthLarger ? reqWidth : reqHeight;
        int reqH = isReqWidthLarger ? reqHeight : reqWidth;
        int inSampleSize = 1;

        if (height > reqH || width > reqW) {
            final int heightRatio = Math.round((float) height / (float) reqH);
            final int widthRatio = Math.round((float) width / (float) reqW);
            inSampleSize = Math.min(heightRatio, widthRatio);
        }
        return inSampleSize;
    }

}
