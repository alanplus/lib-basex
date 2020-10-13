package com.lib.basex.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * @author Alan
 * 时 间：2020/10/13
 * 简 述：<功能简述>
 */
public class LBitmapUtils {

    public static boolean save(Bitmap bitmap, String path) {
        if (bitmap == null || path == null)
            return false;
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
            if (ext != null && ext.equals("jpg")) {
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
}
