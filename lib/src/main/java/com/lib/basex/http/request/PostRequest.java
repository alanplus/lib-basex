package com.lib.basex.http.request;

import com.lib.basex.utils.Logger;

import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author Alan
 * 时 间：2019-12-13
 * 简 述：<功能简述>
 */
public class PostRequest extends LRequest {

    public PostRequest(String path) {
        super(path);
    }

    @Override
    public Request create(String url, Request.Builder builder, String body) {
        Logger.d(url);
        Logger.d(body);
        return builder.url(url).post(RequestBody.create(mediaType, body)).build();
    }

}
