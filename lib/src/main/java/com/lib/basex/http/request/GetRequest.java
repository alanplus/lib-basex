package com.lib.basex.http.request;


import android.text.TextUtils;


import com.lib.basex.utils.Logger;

import okhttp3.Request;

/**
 * @author Alan
 * 时 间：2019-12-13
 * 简 述：<功能简述>
 */
public class GetRequest extends LRequest {


    public GetRequest(String path) {
        super(path);
    }

    @Override
    public Request create(String url, Request.Builder builder, String body) {
        if (!TextUtils.isEmpty(body)) {
            url += "?" + body;
        }
        Logger.d(url);
        return builder.url(url).build();
    }
}
