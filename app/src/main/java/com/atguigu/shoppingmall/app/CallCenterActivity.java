package com.atguigu.shoppingmall.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.utils.Constants;

public class CallCenterActivity extends Activity {
    private WebView webView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_center);
        webView = (WebView) findViewById(R.id.webView);
        initWebView();
    }

    /**
     * 初始化webView
     */
    private void initWebView() {
        String url = Constants.CALL_CENTER;
        webView.loadUrl(url);
        //覆盖使用自带浏览器，只使用内置浏览器
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
        //设置支持js
        settings.setJavaScriptEnabled(true);
        //设置优先使用缓存
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
    }


}
