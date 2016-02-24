package com.shichai.www.choume.activity.chou;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.globalways.choume.R;
import com.shichai.www.choume.activity.BaseActivity;
import com.shichai.www.choume.tools.Tool;


public class ChouDescMore extends BaseActivity {

    private WebView wvContainer;
    private ProgressBar progressBar;

    private String project_intro;
    private String project_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chou_desc_more);
        initActionBar();
        setTitle("项目介绍");

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
        public void handleMessage(android.os.Message msg) {
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
        project_intro = getIntent().getStringExtra(ChouDetailActivity.PROJECT_INTRO);
        project_desc  = getIntent().getStringExtra(ChouDetailActivity.PROJECT_DESC);
        if (Tool.isEmpty(project_intro)) {
            wvContainer.loadData(project_desc, "text/html", "utf-8");
        }else wvContainer.loadData(project_intro, "text/html", "utf-8");
    }

}
