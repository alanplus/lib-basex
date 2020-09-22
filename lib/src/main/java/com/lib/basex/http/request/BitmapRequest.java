package com.lib.basex.http.request;

import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.lib.basex.http.ApiResult;

import java.io.InputStream;

import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Alan
 * 时 间：2019-12-17
 * 简 述：<功能简述>
 */
public class BitmapRequest extends LRequest {


    public BitmapRequest(String path) {
        super(path);
    }

    @Override
    public Request create(String url, Request.Builder builder, String body) {
        if (!TextUtils.isEmpty(body)) {
            url += "?" + body;
        }
        return builder.url(url).build();
    }


    @Override
    protected ApiResult handlerResponse(Response response, OnHttpCallBack onHttpCallBack) {
        ApiResult apiResult = new ApiResult(-122);
        apiResult.httpCode = response.code();
        if (response.isSuccessful()) {
            if (response.body() != null) {
                InputStream in = response.body().byteStream();
                apiResult.object = BitmapFactory.decodeStream(in);
                return apiResult;
            }
        }
        return apiResult;
    }
}
