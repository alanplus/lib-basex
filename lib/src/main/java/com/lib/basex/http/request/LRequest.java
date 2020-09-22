package com.lib.basex.http.request;

import android.os.Handler;
import android.text.TextUtils;

import com.lib.basex.http.ApiResult;
import com.lib.basex.http.HttpConfig;
import com.lib.basex.http.IParseStrategy;
import com.lib.basex.thread.LThreadService;
import com.lib.basex.utils.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Alan
 * 时 间：2019-12-13
 * 简 述：<功能简述>
 */
public abstract class LRequest {

    protected OkHttpClient okHttpClient;
    protected static final String CHARSET_NAME = "UTF-8";

    protected boolean isEncoding;
    protected MediaType mediaType;

    protected String url;
    protected String tag;

    protected HashMap<String, String> mParams;
    protected HashMap<String, String> mHeaders;

    protected IParseStrategy iParseStrategy;

    protected OnHttpCallBack onHttpCallBack;

    protected String downloadDir, downloadName;


    public LRequest(String path) {
        this.okHttpClient = HttpConfig.getOkHttpClient();
        this.url = path.startsWith("http://") || path.startsWith("https://") ? path : HttpConfig.getHost() + path;
        this.tag = url;
        this.isEncoding = HttpConfig.isEncoding();
        this.mediaType = HttpConfig.getMediaType();
        this.mParams = HttpConfig.getCommonParams();
        this.mHeaders = HttpConfig.getHeadParams();
        this.iParseStrategy = HttpConfig.getParseStrategy();
    }


    public abstract Request create(String url, Request.Builder builder, String body);


    protected String getContentFromMap(Map<String, String> params) throws UnsupportedEncodingException {
        if (null == params) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Set<String> keys = params.keySet();
        for (String key : keys) {
            String value = params.get(key);
            if (value != null) {
                sb.append(key).append('=').append(isEncoding ? URLEncoder.encode(value, CHARSET_NAME) : value).append('&');
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    protected Request create() throws IOException {
        Request.Builder builder = new Request.Builder().tag(tag);
        addHeaders(builder);
        return create(url, builder, getContentFromMap(mParams));
    }


    protected void addHeaders(Request.Builder builder) {
        if (!mHeaders.isEmpty()) {
            for (String key : mHeaders.keySet()) {
                String value = mHeaders.get(key);
                if (!TextUtils.isEmpty(value)) {
                    builder.addHeader(key, value);
                }
            }
        }
    }


    public LRequest setEncoding(boolean encoding) {
        isEncoding = encoding;
        return this;
    }

    public LRequest setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
        return this;
    }

    public LRequest setUrl(String url) {
        this.url = url;
        return this;
    }

    public LRequest setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public LRequest setParams(HashMap<String, String> mParams) {
        this.mParams = mParams;
        return this;
    }

    public LRequest setHeaders(HashMap<String, String> mHeaders) {
        this.mHeaders = mHeaders;
        return this;
    }

    public String getUrl() {
        return url;
    }


    public LRequest addParams(HashMap<String, String> mParams) {
        this.mParams.putAll(mParams);
        return this;
    }

    public LRequest addParam(String key, String value) {
        this.mParams.put(key, value);
        return this;
    }

    public LRequest addParam(String key, int value) {
        this.mParams.put(key, String.valueOf(value));
        return this;
    }


    public LRequest addParam(String key, boolean value) {
        this.mParams.put(key, String.valueOf(value));
        return this;
    }

    public LRequest addParam(String key, long value) {
        this.mParams.put(key, String.valueOf(value));
        return this;
    }

    public LRequest addParam(String key, Object value) {
        this.mParams.put(key, value.toString());
        return this;
    }

    public LRequest addHeads(HashMap<String, String> mHeaders) {
        this.mHeaders.putAll(mHeaders);
        return this;
    }

    public LRequest addHead(String key, String value) {
        this.mHeaders.put(key, value);
        return this;
    }

    public LRequest setParseStrategy(IParseStrategy iParseStrategy) {
        this.iParseStrategy = iParseStrategy;
        return this;
    }

    public LRequest setOnHttpCallBack(OnHttpCallBack onHttpCallBack) {
        this.onHttpCallBack = onHttpCallBack;
        return this;
    }

    public LRequest setDownloadDir(String dir, String downloadName) {
        this.downloadDir = dir;
        this.downloadName = downloadName;
        return this;
    }

    /**
     * 执行请求
     */
    public ApiResult execute() {
        try {
            Request request = create();
            Response response = okHttpClient.newCall(request).execute();
            return handlerResponse(response, null);
        } catch (Exception e) {
            Logger.error(e);
        }
        return new ApiResult(-122);
    }


    public Response response() throws Exception {
        Request request = create();
        return okHttpClient.newCall(request).execute();
    }


    public void execute(final OnHttpCallBack callBack) {
        this.onHttpCallBack = callBack;
        Request request = null;
        try {
            request = create();
        } catch (Exception e) {
            Logger.error(e);
            onError(onHttpCallBack, null, e);
            return;
        }
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                onError(onHttpCallBack, call, e);
            }

            @Override
            public void onResponse(final Call call, final Response response) {
                if (null != onHttpCallBack)
                    try {
                        final ApiResult apiResult = handlerResponse(response, callBack);
                        LThreadService.runOnMainThread(() -> onHttpCallBack.onSuccess(apiResult.originText, apiResult));
                    } catch (Exception e) {
                        Logger.error(e);
                        onError(onHttpCallBack, null, e);
                    }

            }
        });
    }

    protected void onError(final OnHttpCallBack onHttpCallBack, final Call call, final Exception e) {
        LThreadService.runOnMainThread(() -> onHttpCallBack.onFailure(call, e));
    }


    protected ApiResult handlerResponse(Response response, OnHttpCallBack onHttpCallBack) throws IOException {
        ApiResult apiResult = new ApiResult(-122);
        apiResult.httpCode = response.code();
        if (response.isSuccessful()) {
            String s = response.body() == null ? "" : response.body().string();
            apiResult.code = -121;
            apiResult.originText = s;
            return null == iParseStrategy ? apiResult : iParseStrategy.parse(s);
        }
        return apiResult;
    }

    public interface OnHttpCallBack {

        void onSuccess(String content, ApiResult t);

        void onFailure(Call call, Exception e);

    }

    public interface OnDownloadHttpCallBack extends OnHttpCallBack {

        void onProgressCallback(long progress, long total);
    }
}
