package com.lib.basex.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.lib.basex.R;
import com.lib.basex.databinding.LActivityWebviewBinding;
import com.lib.basex.utils.LUtils;


public class LWebViewActivity extends LStateActivity<LStateViewModel, LActivityWebviewBinding> {

    protected String mTitle = "";
    private String mUrl;

    private boolean isShowWebTitle;


    @Override
    public int getContentId() {
        return R.layout.l_activity_webview;
    }

    @Override
    public void initView() {
        initWebView();
        d.contentView.setBackgroundColor(Color.RED);
        mUrl = getIntent().getStringExtra("url");
        mTitle = getIntent().getStringExtra("title");
        isShowWebTitle = getIntent().getBooleanExtra("is_show_web_title", true);
        setTitleBar(mTitle);
        t.showSuccessState();
        loadUrl(mUrl);
    }

    protected void loadUrl(String url) {
        mUrl = url;
        if (!LUtils.isNetworkAvailable(this) && !mUrl.startsWith("file:")) {
            binding.stateLayout.showFailureState("请连接网络后，点击重试", true);
            binding.stateLayout.getRetryView().setOnClickListener(view -> loadUrl(mUrl));
        } else {
            d.webView.loadUrl(mUrl);
        }

    }


    @SuppressLint("AddJavascriptInterface")
    protected void initWebView() {
        d.webView.removeJavascriptInterface("searchBoxJavaBridge");
        d.webView.removeJavascriptInterface("accessibility");
        d.webView.removeJavascriptInterface("accessibilityTraversal");

        d.webView.setWebViewClient(new WebViewClient() {

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                if (isValidUrl()) {
                    return super.shouldInterceptRequest(view, url);
                }
                return new WebResourceResponse(null, null, null);
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                String string = request.getUrl().getHost();
                if (isValidUrl()) {
                    return super.shouldInterceptRequest(view, request);
                }
                return new WebResourceResponse(null, null, null);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            d.webView.addJavascriptInterface(new JavaScriptInterface(), "weicinativejs");
//        }

        d.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (isShowWebTitle) {
                    binding.titleBar.setTitle(title);
                }
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                LWebViewActivity.this.onProgressChanged(newProgress);
            }
        });

        WebSettings webSetting = d.webView.getSettings();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        webSetting.setBlockNetworkImage(false);//解决图片不显示
        webSetting.setAllowFileAccess(false);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(false);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(false);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(false);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSetting.setSavePassword(false);
    }

    protected void onProgressChanged(int progress) {
    }

    @Override
    protected void onDestroy() {
        d.webView.removeAllViews();
        d.webView.destroy();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (d.webView.canGoBack()) {
            d.webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    public boolean isValidUrl() {
        return true;
    }
}
