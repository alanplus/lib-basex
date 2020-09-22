package com.lib.basex.http;


import com.lib.basex.http.request.BitmapRequest;
import com.lib.basex.http.request.FileRequest;
import com.lib.basex.http.request.GetRequest;
import com.lib.basex.http.request.LRequest;
import com.lib.basex.http.request.PostFileRequest;
import com.lib.basex.http.request.PostRequest;

/**
 * @author Alan
 * 时 间：2019-12-13
 * 简 述：<功能简述>
 */
public class LHttpExecutor {


    public static LRequest get(String path) {
        return new GetRequest(path);
    }

    public static LRequest post(String path) {
        return new PostRequest(path);
    }

    public static LRequest bitmap(String path) {
        return new BitmapRequest(path);
    }

    public static LRequest postFile(String path) {
        return new PostFileRequest(path);
    }

    public static LRequest downloadFile(String path) {
        return new FileRequest(path);
    }
}
