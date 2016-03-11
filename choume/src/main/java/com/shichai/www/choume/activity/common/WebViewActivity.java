package com.shichai.www.choume.activity.common;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.activity.chou.ChouDetailActivity;
import com.shichai.www.choume.tools.CMTool;
import com.shichai.www.choume.tools.Tool;

/**
 * 通用WebView Activity,用于显示文本内容
 */
public class WebViewActivity extends BaseActivity {

    private WebView wvContainer;
    private ProgressBar progressBar;

    private String wvTitle;
    private String wvContentHtml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        initActionBar();
        initViews();
        initData();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && wvContainer.canGoBack()) {
            wvContainer.goBack();
            return true;
        } else return super.onKeyDown(keyCode, event);
    }

    private void initViews() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        wvContainer = (WebView) findViewById(R.id.wvContainer);
        wvContainer.getSettings().setDefaultTextEncodingName("utf-8");
        wvContainer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
                wvContainer.setVisibility(View.VISIBLE);
            }
        });

        wvContainer.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Message msg = new Message();
                msg.what = 200;
                msg.obj = newProgress;
                //使用无进度的progressBar
                //handler.sendMessage(msg);
            }
        });
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 200:
                    int progress = (Integer) msg.obj;
                    progressBar.setProgress(progress);
                    break;

                default:
                    break;
            }
        };
    };


    private void initData() {
        wvTitle = getIntent().getStringExtra(CMTool.WEBVIEW_TITLE);
        wvContentHtml  = getIntent().getStringExtra(CMTool.WEBVIEW_CONTENT_HTML);
        wvContainer.loadData(wvContentHtml, "text/html; charset=UTF-8", null);
        setTitle(wvTitle);
    }

}
