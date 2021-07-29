package com.lib.basex.utils;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.lib.basex.LApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mouse on 2018/10/18.
 */
public class LFileUtils {

    /**
     * 获取APP 私有目录
     *
     * @return
     */
    public static String getDir() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            return LApplication.app.getDataDir().getAbsolutePath();
        } else {
            return "/data/data/" + LApplication.app.getPackageName();
        }
    }

    public static String getFilename(String path) {
        if (TextUtils.isEmpty(path)) {
            return "";
        }
        int index = path.lastIndexOf("/");
        return path.substring(index + 1);
    }

    public static String getFileExt(String path) {
        if (path == null) {
            return null;
        }
        int index = path.lastIndexOf(".");
        if (index >= 0) {
            return path.substring(index + 1).toLowerCase();
        }
        return null;
    }

    public static String getFilenameByKeyHashCode(String url) {
        int firstHalfLength = url.length() / 2;
        String localFilename = String.valueOf(url.substring(0, firstHalfLength).hashCode());
        localFilename += String.valueOf(url.substring(firstHalfLength).hashCode());
        return localFilename;
    }

    public static List<String> readStringList(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        List<String> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static boolean exist(String path) {
        if (TextUtils.isEmpty(path)) {
            return false;
        }
        return new File(path).exists();
    }

    public static boolean move(String src, String dest, boolean delOnExist) {
        if (TextUtils.isEmpty(src) || TextUtils.isEmpty(dest) || !exist(src)) {
            return false;
        }
        File fileDest = new File(dest);
        if (!fileDest.exists()) {
            boolean mkdirs = fileDest.mkdirs();
            if (!mkdirs) {
                return false;
            }
        }
        if (fileDest.isFile()) {
            return false;
        }

        String filename = getFilename(src);
        String destFilePath = dest.endsWith("/") ? dest + filename : dest + "/" + filename;
        File destFile = new File(destFilePath);
        if (destFile.exists()) {
            if (delOnExist) {
                destFile.delete();
            } else {
                new File(src).delete();
                return true;
            }
        }
        return new File(src).renameTo(destFile);
    }

    public static String join(String path, String name) {
        if (TextUtils.isEmpty(path)) {
            return name;
        }
        return path.endsWith("/") ? path + name : path + "/" + name;
    }

    public static void writeFile(String path, String name, boolean append, String content) {
        try {
            File dir = new File(path);
            if (!dir.exists()) {
                boolean mkdirs = dir.mkdirs();
                Logger.d("文件夹创建：" + mkdirs);
            }

            File file = new File(path, name);
            if (!file.exists()) {
                boolean newFile = file.createNewFile();
                Logger.d("文件创建：" + newFile);
            }

            FileWriter fileWriter = new FileWriter(file, append);
            fileWriter.append(content);
            fileWriter.close();
        } catch (Exception err) {
            Logger.error(err);
        }

    }
}
