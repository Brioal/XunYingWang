package com.brioal.xunyingwang.commune;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.brioal.xunyingwang.R;
import com.brioal.xunyingwang.base.BaseActivity;
import com.socks.library.KLog;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DialogueActivity extends BaseActivity {
    private WebView mWebView;
    private String mId = "";
    private String URL_DIALOGUE = "http://www.xunyingwang.com%s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_dialogue);
        initData();
        initID();
        initView();
    }

    private void initView() {
        //WebView设置
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        String REAL_URL = String.format(URL_DIALOGUE, mId);
        //将内容显示到网页上面
        mWebView.loadUrl(REAL_URL);
        //mWebView.loadDataWithBaseURL(null, content, "text/html", "utf8", null);

    }

    private void initID() {
        mWebView = (WebView) findViewById(R.id.dialogue_webview);
    }

    private void initData() {
        mId = getIntent().getStringExtra("id");
    }
}
