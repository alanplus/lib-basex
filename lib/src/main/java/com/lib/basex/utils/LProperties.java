package com.lib.basex.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Alan
 * 时 间：2020/9/22
 * 简 述：打开资源文件中properties的配置
 */
public class LProperties {

    public static Properties getProperties(Context context, String url) {
        InputStream in = null;

        try {
            AssetManager assets = context.getResources().getAssets();
            Properties properties = new Properties();
            in = assets.open(url);
            properties.load(in);
            return properties;
        } catch (Exception e) {
            Logger.error(e);
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (Exception e) {
                    Logger.error(e);
                }
            }
        }

        return null;
    }

    public static String getValue(Context context, String url, String key) {
        Properties properties = getProperties(context, url);
        return null == properties ? "" : properties.getProperty(key, "");
    }
}
